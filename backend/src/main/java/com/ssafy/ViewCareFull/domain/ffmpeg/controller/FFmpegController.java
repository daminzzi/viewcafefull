package com.ssafy.ViewCareFull.domain.ffmpeg.controller;

import com.ssafy.ViewCareFull.domain.ffmpeg.service.FFmpegService;
import com.ssafy.ViewCareFull.domain.ffmpeg.service.FFmpegService2;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ffmpeg")
@RequiredArgsConstructor
public class FFmpegController {

  private final FFmpegService ffmpegService;
  private final FFmpegService2 ffmpegService2;

  @PostMapping("/convert")
  public ResponseEntity<String> convertVideo(@RequestBody List<String> imageUrls)
      throws IOException, InterruptedException {
    ffmpegService.buildCommand(imageUrls);
    return new ResponseEntity<>("convert video create success", HttpStatus.CREATED);
  }

  @PostMapping("/convert2")
  public ResponseEntity<String> convert2Video(@RequestBody List<String> imageUrls)
      throws IOException, InterruptedException {
    ffmpegService2.buildCommand(imageUrls);
    return new ResponseEntity<>("convert video create success", HttpStatus.CREATED);
  }
}
