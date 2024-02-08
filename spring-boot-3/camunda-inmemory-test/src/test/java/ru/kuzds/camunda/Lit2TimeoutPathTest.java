package ru.kuzds.camunda;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.api.worker.JobHandler;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.process.test.api.ZeebeTestEngine;
import io.camunda.zeebe.spring.test.ZeebeSpringTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static io.camunda.zeebe.process.test.assertions.BpmnAssert.assertThat;
import static io.camunda.zeebe.protocol.Protocol.USER_TASK_JOB_TYPE;
import static io.camunda.zeebe.spring.test.ZeebeTestThreadSupport.waitForProcessInstanceCompleted;
import static io.camunda.zeebe.spring.test.ZeebeTestThreadSupport.waitForProcessInstanceHasPassedElement;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringBootTest
//@ZeebeSpringTest
class Lit2TimeoutPathTest {
//
//    @Autowired
//    private ZeebeClient zeebe;
//
//    @Autowired
//    private ZeebeTestEngine zeebeTestEngine;
//
//    @Test
//    void test() throws Exception {
//
//        // start a process instance
//        ProcessInstanceEvent processInstance = zeebe.newCreateInstanceCommand()
//                .bpmnProcessId("tpsbp-in")
//                .latestVersion()
//                .variables(Map.of("lit2ResponseCode", "I00000", "lit2State", "3", "extBank", Map.of("callbackUrl", "https://64579984-ad2e-40f8-969d-080492356c3e.mock.pstmn.io")))
//                .startBeforeElement("CreateCheckCallback")
//                .send()
//                .join();
//
//        try (JobWorker w1 = openWorker(zeebe,"j-tpsbp-repo.addParticipantTransac", (client, job) -> {client.newCompleteCommand(job.getKey()).send();});
//                JobWorker w2 = openWorker(zeebe,"j-tpsbp-repo.updateOrder", (client, job) -> {client.newCompleteCommand(job.getKey()).send();})) {
//
//        waitForProcessInstanceHasPassedElement(processInstance, "AddParticipantTransacCheckCallback");
//        waitForHttpJsonTaskAndComplete("SendCheckCallback", null);
//
//        // Now the process should run to the end
//            waitForProcessInstanceCompleted(processInstance);
//
//            // Let's assert that it passed certain BPMN elements (more to show off features here)
//            assertThat(processInstance)
//                    .hasNotPassedElement("EndEventCheckSubprocess")
//                    .hasPassedElement("EndEventLit2Error")
//                    .isCompleted();
//        }
//
//
//
//    }
//
//    public static JobWorker openWorker(ZeebeClient client, String jobType, JobHandler handler) {
//        return client.newWorker()
//                .jobType(jobType)
//                .handler(handler)
//                .open();
//    }
//
////    public void waitForUserTaskAndComplete(String userTaskId, Map<String, Object> variables)
////            throws InterruptedException, TimeoutException {
////        // Let the workflow engine do whatever it needs to do
////        zeebeTestEngine.waitForIdleState(Duration.ofMinutes(5));
////
////        // Now get all user tasks
////        List<ActivatedJob> jobs = zeebe.newActivateJobsCommand()
////                .jobType(USER_TASK_JOB_TYPE)
////                .maxJobsToActivate(1)
////                .workerName("waitForUserTaskAndComplete")
////                .send()
////                .join()
////                .getJobs();
////
////        // Should be only one
////        assertTrue(jobs.size() > 0, "Job for user task '" + userTaskId + "' does not exist");
////        ActivatedJob userTaskJob = jobs.get(0);
////        // Make sure it is the right one
////        if (userTaskId != null) {
////            assertEquals(userTaskId, userTaskJob.getElementId());
////        }
////
////        // And complete it passing the variables
////        if (variables != null && variables.size() > 0) {
////            zeebe.newCompleteCommand(userTaskJob.getKey()).variables(variables).send().join();
////        } else {
////            zeebe.newCompleteCommand(userTaskJob.getKey()).send().join();
////        }
////    }
//
//    public void waitForHttpJsonTaskAndComplete(String taskId, Map<String, Object> variables)
//            throws InterruptedException, TimeoutException {
//        // Let the workflow engine do whatever it needs to do
//        zeebeTestEngine.waitForIdleState(Duration.ofMinutes(5));
//
//        // Now get all user tasks
//        List<ActivatedJob> jobs = zeebe.newActivateJobsCommand()
//                .jobType("io.camunda:http-json:1")
//                .maxJobsToActivate(1)
////                .workerName("SendCheckCallback")
//                .send()
//                .join()
//                .getJobs();
//
//        // Should be only one
//        assertTrue(jobs.size() > 0, "Job for user task '" + taskId + "' does not exist");
//        ActivatedJob userTaskJob = jobs.get(0);
//        // Make sure it is the right one
//        if (taskId != null) {
//            assertEquals(taskId, userTaskJob.getElementId());
//        }
//
//        // And complete it passing the variables
//        if (variables != null && variables.size() > 0) {
//            zeebe.newCompleteCommand(userTaskJob.getKey()).variables(variables).send().join();
//        } else {
//            zeebe.newCompleteCommand(userTaskJob.getKey()).send().join();
//        }
//    }
}
