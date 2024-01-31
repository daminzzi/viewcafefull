package com.ssafy.ViewCareFull.domain.medicine.exception;

public class MedicineNotExistException extends RuntimeException {

  public MedicineNotExistException() {
    super("약이 존재하지 않습니다.");
  }

}
