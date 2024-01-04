package ru.kuzds.camunda.web2mq.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.kuzds.camunda.web2mq.dto.User;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotifyWorker {

    @JobWorker(type = "web2mq.notifyResult", fetchVariables = {"user"})
    public void notifyResult(@Variable @NonNull User user) {
        log.info("Received user: " + user);
    }

    @JobWorker(type = "web2mq.notifyError", fetchAllVariables = true)
    public void notifyError(ActivatedJob job, JobClient client) {
        //todo в каком виде возвращать Siebel ошибку?
        log.info("siebel.notifyError: " + job.getVariables());
    }
}
