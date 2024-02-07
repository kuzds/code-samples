package ru.kuzds.camunda.business;

/** Publish content on Twitter. */
public interface TwitterService {

  void tweet(String content) throws DuplicateTweetException;
}
