package com.ssafy.ViewCareFull.domain.message.entity;

import com.ssafy.ViewCareFull.domain.message.dto.MessageRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Message {

  public Message(String fromId, MessageRequestDto messageRequestDto) {
    this.fromId = fromId;
    this.toId = messageRequestDto.getTo();
    this.title = messageRequestDto.getTitle();
    this.content = messageRequestDto.getContent();
    this.isRead = false;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "from_id")
  private String fromId;

  @Column(name = "to_id")
  private String toId;

  @Column(name = "message_title")
  private String title;

  @Column(name = "message_content")
  private String content;

  @Column(columnDefinition = "boolean default false")
  private Boolean isRead;

  @Column(name = "send_datetime", updatable = false)
  @CreatedDate
  private LocalDateTime sendDateTime;

  public void readThisMessage() {
    this.isRead = true;
  }
}
