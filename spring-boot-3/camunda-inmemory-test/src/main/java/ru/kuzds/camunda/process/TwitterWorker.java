package ru.kuzds.camunda.process;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.VariablesAsType;
import io.camunda.zeebe.spring.client.exception.ZeebeBpmnError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kuzds.camunda.business.DuplicateTweetException;
import ru.kuzds.camunda.business.TwitterService;

@Component
@RequiredArgsConstructor
public class TwitterWorker {

  private final TwitterService twitterService;

  @JobWorker(type = "publish-tweet")
  public void handleTweet(@VariablesAsType TwitterProcessVariables variables) throws Exception {
    try {
      twitterService.tweet(variables.getTweet());
    } catch (DuplicateTweetException ex) {
      throw new ZeebeBpmnError("duplicateMessage", "Could not post tweet, it is a duplicate.");
    }
  }

  @JobWorker(type = "send-rejection")
  public void sendRejection(@VariablesAsType TwitterProcessVariables variables) throws Exception {
    // same thing as above, do data transformation and delegate to real business code / service
  }
}
