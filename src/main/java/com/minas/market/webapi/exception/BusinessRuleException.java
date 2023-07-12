package com.minas.market.webapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BusinessRuleException extends RuntimeException{

  public BusinessRuleException() {
    super();
  }

  public BusinessRuleException(String message, Throwable cause) {
    super(message, cause);
  }

  public BusinessRuleException(String message) {
    super(message);
  }

  public BusinessRuleException(Throwable cause) {
    super(cause);
  }

}
