package ru.kuzds.camunda.testcontainer;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import io.camunda.zeebe.spring.client.bean.MethodInfo;
import io.camunda.zeebe.spring.client.jobhandling.JobWorkerManager;
import io.zeebe.containers.ZeebeContainer;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.kuzds.camunda.testcontainer.ZeebeClientUtils.createDeployment;
import static ru.kuzds.camunda.testcontainer.ZeebeClientUtils.createProcess;

@Slf4j
@Testcontainers
@SpringBootTest(classes = {CamundaTestcontainerApplication.class, CamundaTestcontainerApplicationTest.Configuration.class})
class CamundaTestcontainerApplicationTest {
    @Container
    private static final ZeebeContainer CONTAINER = new ZeebeContainer();
    @Autowired
    private ZeebeClient client;
    @Autowired
    private JobWorkerManager workerManager;
    @Autowired
    private LoggingWorkerInterceptor loggingWorkerInterceptor;
    @Autowired
    private StrangerWorker strangerWorker;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("zeebe.client.broker.gateway-address", CONTAINER::getExternalGatewayAddress);
    }

    @Test
    @DisplayName("Проверка одновременного выполнения двух воркеров")
    void test() {

        Map<String, Object> vars = Map.of("invariable", UUID.randomUUID().toString());

        // Зарегистрировать процесс
        createDeployment(client, "camunda-testcontainer.bpmn");

        // Создать экземпляр процесса, начиная с шага "ждать Lit2"
        ProcessInstanceEvent event = createProcess(client, "camunda-testcontainer", vars);

        long instanceKey = event.getProcessInstanceKey();

        Awaitility.await()
                .atMost(3, TimeUnit.SECONDS)
                .pollDelay(100, TimeUnit.MILLISECONDS)
                .pollInterval(300, TimeUnit.MILLISECONDS)
                .until(() -> (strangerWorker.done(instanceKey, "StrangerWorker") && loggingWorkerInterceptor.done()));


        @SuppressWarnings("unchecked")
        LinkedHashMap<String, Object> variables = (LinkedHashMap<String, Object>) strangerWorker.getVariables(instanceKey, "StrangerWorker");
        assertThat(variables.get("invariable")).isNotNull();

        String outvariable = (String) loggingWorkerInterceptor.getResult()
                .map(m -> m.get("outvariable"))
                .orElse(null);
        assertThat(outvariable).isNotNull();

        // если процесс не завершился и хочется завершить процесс
//        client.newCancelInstanceCommand(instanceKey).send().join();
    }

    @PreDestroy
    void destroy() {
        workerManager.closeAllOpenWorkers();
    }


    /**
     * <p>
     * Аспект используется для проверки результата работы {@link LoggingWorker},
     * который инициализируется декларативно {@link io.camunda.zeebe.spring.client.annotation.JobWorker}
     * </p>
     * <p>
     * К сожалению, нет возможности использовать {@link SpyBean}, т.к.{@link MethodInfo}
     * полагается на спринговый {@link LocalVariableTableParameterNameDiscoverer}, который некорректно работает в случае
     * со @Spy proxy
     * </p>
     * <p>
     * Второй вариант, - переопределить {@link JobWorkerManager}, - является намного менее изящным решением, чем AOP
     * в силу особенности своей реализации
     * </p>
     */
    @Aspect
    public static class LoggingWorkerInterceptor {

        private final AtomicReference<Map<String, Object>> result = new AtomicReference<>();

        public Optional<Map<String, Object>> getResult() {
            return Optional.ofNullable(result.get());
        }

        public boolean done() {
            return Optional.ofNullable(result.get()).isPresent();
        }

        @AfterReturning(value = "execution(* ru.kuzds.camunda.testcontainer.LoggingWorker.logging(..))",
                returning = "result")
        public void afterReturningCreateCheckResponse(Object result) {
            log.info("--- set result for LoggingWorkerInterceptor ---");
            @SuppressWarnings("unchecked") Map<String, Object> responseMap = (Map<String, Object>) result;
            this.result.set(responseMap);
        }
    }

    /**
     * Mock JobWorker с типом stranger.worker
     */
    public static class StrangerWorker {

        ConcurrentHashMap<String, Object> savedObjects = new ConcurrentHashMap<>();

        @JobWorker(type = "stranger.worker")
        public void strangerWorker(final ActivatedJob activatedJob, @Variable String invariable) {
            // ключ для доступа будет processInstanceKey:ElementId
            String key = String.join(":", Long.toString(activatedJob.getProcessInstanceKey()), activatedJob.getElementId());
            Map<String, Object> variables = activatedJob.getVariablesAsMap();
            log.info("stranger.worker key: {}, invariable: {}", key, invariable);
            savedObjects.put(key, variables);
        }

        boolean done(long processInstanceKey, String elementId) {
            return nonNull(getVariables(processInstanceKey, elementId));
        }

        Object getVariables(long processInstanceKey, String elementId) {
            String key = String.join(":", Long.toString(processInstanceKey), elementId);
            return savedObjects.getOrDefault(key, null);
        }

    }

    /**
     * Добавляем в тестовый контекст перехватчики LoggingWorker и mock StrangerWorker, который реализован в другом сервисе.
     */
    public static class Configuration {
        @Bean
        public LoggingWorkerInterceptor loggingWorkerInterceptor() {
            return new LoggingWorkerInterceptor();
        }

        @Bean
        public StrangerWorker strangerWorker() {
            return new StrangerWorker();
        }
    }

}
