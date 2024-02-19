package com.ssafy.ViewCareFull.domain.medicine.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MedicineAdvice {

  @ExceptionHandler(NoCaregiverException.class)
  public ResponseEntity<String> noCaregiverException(NoCaregiverException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

}
