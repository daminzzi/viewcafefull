package com.ssafy.ViewCareFull.domain.ffmpeg.exception;

public class VideoCreateFailException extends RuntimeException {

  public VideoCreateFailException() {
    super("create video failed");
  }
}
