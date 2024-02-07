package ru.kuzds.camunda.business;

public class DuplicateTweetException extends Exception {
  public DuplicateTweetException(String message) {
    super(message);
  }

  public DuplicateTweetException(String message, Throwable cause) {
    super(message, cause);
  }
}
