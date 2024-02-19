package com.ssafy.ViewCareFull.domain.gallery.entity;

import com.ssafy.ViewCareFull.domain.common.entity.TodayType;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
// unique index to user mealtype mealdate
@Table(name = "meal",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "meal_type", "meal_date"})})
public class Meal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "user_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Users user;

  @Column
  @Enumerated(EnumType.STRING)
  private TodayType mealType;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "image_id")
  private Image image;

  @Column
  private LocalDate mealDate;

  public Meal(Users user, LocalDate mealDate, TodayType mealType, Image image) {
    this.user = user;
    this.mealDate = mealDate;
    this.mealType = mealType;
    this.image = image;
  }
}
