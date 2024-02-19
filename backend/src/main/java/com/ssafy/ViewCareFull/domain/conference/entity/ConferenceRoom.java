package com.ssafy.ViewCareFull.domain.conference.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ConferenceRoom {

  @Column(name = "room_name")
  private String roomName;

  @Column(name = "start_datetime")
  private LocalDateTime startDateTime;

  @Column(name = "end_datetime")
  private LocalDateTime endDateTime;

  public ConferenceRoom(String roomName) {
    this.roomName = roomName;
  }

  public void startConference(LocalDateTime startDateTime) {
    this.startDateTime = startDateTime;
  }

  public void endConference(LocalDateTime endDateTime) {
    this.endDateTime = endDateTime;
  }
}