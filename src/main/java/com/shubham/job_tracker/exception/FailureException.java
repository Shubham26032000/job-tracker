package com.shubham.job_tracker.exception;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FailureException extends RuntimeException {
  private String message;
  private long code;
  public FailureException(Long code,String message) {
    super(message);
    this.code = code;
    this.message = message;
  }


}
