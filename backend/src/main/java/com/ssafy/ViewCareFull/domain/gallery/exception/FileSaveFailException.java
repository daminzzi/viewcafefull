package com.ssafy.ViewCareFull.domain.gallery.exception;

public class FileSaveFailException extends RuntimeException {

  public FileSaveFailException() {
    super("파일 저장에 실패하였습니다.");
  }

}
