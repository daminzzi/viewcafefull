package com.ssafy.ViewCareFull.domain.message.entity;

import com.ssafy.ViewCareFull.domain.users.entity.user.Guardian;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "from_id")
  private Users fromId;

  @ManyToOne
  @JoinColumn(name = "to_id")
  private Guardian toId;

  @Column(name = "message_content")
  private String content;

  @Column
  @Enumerated(EnumType.STRING)
  private ReadType isReaded;

  @Column(name = "send_datetime")
  private LocalDateTime sendDateTime;
}
