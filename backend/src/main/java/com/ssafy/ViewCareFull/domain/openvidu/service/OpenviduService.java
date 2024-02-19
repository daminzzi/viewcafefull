package com.ssafy.ViewCareFull.domain.openvidu.service;

import com.ssafy.ViewCareFull.domain.conference.entity.Conference;
import com.ssafy.ViewCareFull.domain.conference.service.ConferenceService;
import com.ssafy.ViewCareFull.domain.gallery.service.BestPhotoService;
import com.ssafy.ViewCareFull.domain.openvidu.error.OpenviduErrorCode;
import com.ssafy.ViewCareFull.domain.openvidu.error.exception.OpenviduException;
import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;
import java.time.LocalDateTime;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenviduService {

  @Value("${OPENVIDU_URL}")
  private String OPENVIDU_URL;

  @Value("${OPENVIDU_SECRET}")
  private String OPENVIDU_SECRET;

  private OpenVidu openvidu;
  private final ConferenceService conferenceService;
  private final BestPhotoService bestPhotoService;


  @PostConstruct // 객체 초기화 시점에 호출
  public void init() {
    this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
  }

  @Transactional
  public String initSession(Map<String, Object> params) throws OpenViduJavaClientException, OpenViduHttpException {
    SessionProperties properties = SessionProperties.fromJson(params).build();
    Session session = openvidu.createSession(properties);
    Conference conference = conferenceService.getConferenceByRoomName(params.get("customSessionId").toString());
    log.info("Start Time : " + String.valueOf(conference.getConferenceRoom().getStartDateTime()));
    if (conference.getConferenceRoom().getStartDateTime() == null) {
      log.info("updateStartTime");
      conferenceService.updateConferenceStartTime(conference, LocalDateTime.now());
    }
    return session.getSessionId();
  }

  @Transactional
  public String closeSession(String sessionId) {
    Conference conference = conferenceService.getConferenceByRoomName(sessionId);
    bestPhotoService.deleteNonBestPhoto(conference.getId());
    log.info("End Time : " + String.valueOf(conference.getConferenceRoom().getEndDateTime()));
    if (conference.getConferenceRoom().getEndDateTime() == null) {
      log.info("updateEndTime");
      conferenceService.updateConferenceEndTime(conference, LocalDateTime.now());
    }
    return sessionId;
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
