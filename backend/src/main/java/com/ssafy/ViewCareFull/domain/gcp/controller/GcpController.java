package com.ssafy.ViewCareFull.domain.gcp.controller;

import com.ssafy.ViewCareFull.domain.gcp.service.GcpService;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/gcp"})
public class GcpController {

  private final GcpService gcpService;

  /**
   * @param params "imgPath" : String
   */
  @PostMapping("/img")
  public ResponseEntity<Integer> analysisImage(@RequestBody(required = false) Map<String, Object> params)
      throws IOException {
    int result = gcpService.detectFace(params.get("imgPath").toString());
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
