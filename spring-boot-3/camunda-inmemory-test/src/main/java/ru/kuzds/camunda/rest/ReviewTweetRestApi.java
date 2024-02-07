package ru.kuzds.camunda.rest;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import ru.kuzds.camunda.process.TwitterProcessVariables;

@RestController
@RequiredArgsConstructor
public class ReviewTweetRestApi {

    private final ZeebeClient zeebeClient;

    @PutMapping("/tweet")
    public ResponseEntity<String> startTweetReviewProcess(ServerWebExchange exchange) {
        // TODO: add data to the process instance from REST request
        String reference = startTweetReviewProcess("bernd", "Hello World", "Zeebot");

        // And just return something for the sake of the example
        return ResponseEntity.status(HttpStatus.OK).body("Started process instance " + reference);
    }

    public String startTweetReviewProcess(String author, String tweet, String boss) {
        TwitterProcessVariables processVariables = TwitterProcessVariables.builder()
                .author(author)
                .tweet(tweet)
                .boss(boss)
                .build();

        ProcessInstanceEvent processInstance = zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId("TwitterDemoProcess")
                .latestVersion()
                .variables(processVariables)
                .send()
                .join(); // blocking call!

        return String.valueOf(processInstance.getProcessInstanceKey());
    }
}
