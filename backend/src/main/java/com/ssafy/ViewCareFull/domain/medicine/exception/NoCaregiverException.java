package com.ssafy.ViewCareFull.domain.medicine.exception;

public class NoCaregiverException extends RuntimeException {

  public NoCaregiverException() {
    super("입소자 코드를 확인해주세요");
  }

}
