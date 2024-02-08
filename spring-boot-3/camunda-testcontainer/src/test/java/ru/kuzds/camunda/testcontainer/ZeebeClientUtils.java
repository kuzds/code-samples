package ru.kuzds.camunda.testcontainer;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.api.worker.JobHandler;
import io.camunda.zeebe.client.api.worker.JobWorker;

import java.util.Map;

public class ZeebeClientUtils {

    @SuppressWarnings("UnusedReturnValue")
    public static DeploymentEvent createDeployment(ZeebeClient client, String bpmn) {
        return client.newDeployResourceCommand()
                .addResourceFromClasspath(bpmn)
                .send()
                .join();
    }

    public static ProcessInstanceEvent createProcess(ZeebeClient client, String processId, Map<String, Object> vars) {
        return client.newCreateInstanceCommand()
                .bpmnProcessId(processId)
                .latestVersion()
                .variables(vars)
//                .startBeforeElement(startAtElementId)
                .send()
                .join();
    }

    public static JobWorker openWorker(ZeebeClient client, String jobType, JobHandler handler) {
        return client.newWorker()
                .jobType(jobType)
                .handler(handler)
                .open();
    }
}
