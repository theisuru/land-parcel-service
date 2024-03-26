package com.example.landparcelservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class LandParcelNotFoundException extends RuntimeException {
  public LandParcelNotFoundException(String message) {
    super(message);
  }
}
