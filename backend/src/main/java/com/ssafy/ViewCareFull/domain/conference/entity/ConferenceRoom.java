package com.ssafy.ViewCareFull.domain.conference.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "conference_id")
  private Conference conference;

  @Column(name = "room_name")
  private String roomName;

  @Column(name = "conference_link")
  private String conferenceLink;

  @Column(name = "start_datetime")
  private LocalDateTime startDateTime;

  @Column(name = "end_datetime")
  private LocalDateTime endDateTime;

  public ConferenceRoom(Conference conference) {
    this.conference = conference;
  }

  public void updateConferenceRoomInfo(String roomName, String conferenceLink) {
    this.roomName = roomName;
    this.conferenceLink = conferenceLink;
  }

  public void startConference(LocalDateTime startDateTime) {
    this.startDateTime = startDateTime;
  }

  public void endConference(LocalDateTime endDateTime) {
    this.endDateTime = endDateTime;
  }
}
