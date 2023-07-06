package com.example.crud123.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DuplicateException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private HttpStatus status;
  private String userMessage;
  private String devMessage;

  public DuplicateException(String userMessage) {
    super(userMessage);
    this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    this.userMessage = userMessage;
    this.devMessage = devMessage;
  }

  public DuplicateException(HttpStatus status, String userMessage, String devMessage) {
    super(userMessage);
    this.status = status;
    this.userMessage = userMessage;
    this.devMessage = devMessage;
  }
}
