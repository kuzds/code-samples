package ru.kuzds.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;

@Slf4j
@ActiveProfiles("develop")
@SpringBootTest(properties = {"spring.main.lazy-initialization=true"})
class SchedulingServiceTest {

    private static final Long DELAY = 100L;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("ru.kuzds.task-delay", () -> DELAY);
    }

    @Autowired
    private SchedulingService schedulingService;

    @Test
    void scheduleAtFixedRate() throws InterruptedException {
        // given
        String uuid = UUID.randomUUID().toString();
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.set(0);
        Runnable task = Mockito.spy(new Runnable() {
            @Override
            public void run() {
                atomicInteger.incrementAndGet();
            }
        });

        // when
        schedulingService.scheduleAtFixedRate(uuid, task, Instant.now(), Duration.ofMillis(DELAY));

        //then
        Thread.sleep(DELAY * 5);
        verify(task, atLeast(4)).run();

        int fixed = atomicInteger.get();
        schedulingService.cancel(uuid);
        Thread.sleep(DELAY * 5);
        Assertions.assertThat(atomicInteger.get()).isEqualTo(fixed);
    }

    @Test
    void schedule() throws InterruptedException {
        // given
        Runnable task = Mockito.mock(Runnable.class);

        // when
        schedulingService.schedule(task);

        //then
        Thread.sleep(DELAY * 5);
        verify(task, atMost(1)).run();
    }
}