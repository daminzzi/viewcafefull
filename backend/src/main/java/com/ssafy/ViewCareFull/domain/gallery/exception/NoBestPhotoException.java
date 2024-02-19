package com.ssafy.ViewCareFull.domain.gallery.exception;

public class NoBestPhotoException extends RuntimeException {

  public NoBestPhotoException() {
    super("BestPhoto is not exsit");
  }
}
