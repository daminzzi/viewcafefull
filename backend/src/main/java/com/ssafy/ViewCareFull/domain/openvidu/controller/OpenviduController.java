package com.ssafy.ViewCareFull.domain.openvidu.controller;

import com.ssafy.ViewCareFull.domain.openvidu.service.OpenviduService;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/openvidu"})
@Slf4j
public class OpenviduController {

  private final OpenviduService openviduService;

  /**
   * @param params session 관련 정보
   * @return sessionId 생성 요청한 sessionId
   */
  @PostMapping("/sessions")
  public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
      throws OpenViduJavaClientException, OpenViduHttpException {
    log.info("initializeSession: " + params.get("customSessionId").toString());
    String sessionId = openviduService.initSession(params);
    return new ResponseEntity<>(sessionId, HttpStatus.OK);
  }

  @DeleteMapping("/sessions/{sessionId}")
  public ResponseEntity<String> closeSession(@PathVariable("sessionId") String sessionId)
      throws OpenViduJavaClientException, OpenViduHttpException {
    log.info("closeSession: " + sessionId);
    String sessionName = openviduService.closeSession(sessionId);
    return new ResponseEntity<>("close " + sessionName + " success", HttpStatus.OK);
  }

  /**
   * @param sessionId 연결 생성하는 세션의 ID
   * @param params    연결 관련 정보 - default: {}
   * @return token 연결에 접근 가능하도록 하는 토큰 정보
   */
  @PostMapping("/sessions/{sessionId}/connections")
  public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
      @RequestBody(required = false) Map<String, Object> params)
      throws OpenViduJavaClientException, OpenViduHttpException {
    String token = openviduService.creToken(sessionId, params);
    return new ResponseEntity<>(token, HttpStatus.OK);
  }
}
