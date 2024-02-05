package com.ssafy.ViewCareFull.domain.openvidu.service;

import com.ssafy.ViewCareFull.domain.openvidu.error.OpenviduErrorCode;
import com.ssafy.ViewCareFull.domain.openvidu.error.exception.OpenviduException;
import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenviduService {

  @Value("${OPENVIDU_URL}")
  private String OPENVIDU_URL;

  @Value("${OPENVIDU_SECRET}")
  private String OPENVIDU_SECRET;

  private OpenVidu openvidu;

  @PostConstruct // 객체 초기화 시점에 호출
  public void init() {
    this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
  }

  public String initSession(Map<String, Object> params) throws OpenViduJavaClientException, OpenViduHttpException {
    SessionProperties properties = SessionProperties.fromJson(params).build();
    Session session = openvidu.createSession(properties);
    return session.getSessionId();
  }

  public String creToken(String sessionId, Map<String, Object> params)
      throws OpenViduJavaClientException, OpenViduHttpException {
    Session session = openvidu.getActiveSession(sessionId);
    if (session == null) {
      throw new OpenviduException(OpenviduErrorCode.SESSION_IS_NULL, "Session is not active");
    }
    ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
    Connection connection = session.createConnection(properties);
    return connection.getToken();
  }
}
