package com.ssafy.ViewCareFull.domain.condition.entity;

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
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "conditions")
public class Conditions {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private Users user;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "user_condition")
  @Enumerated(EnumType.STRING)
  private ConditionType condition;

  public void updateCondition(ConditionType condition) {
    this.condition = condition;
  }
}
