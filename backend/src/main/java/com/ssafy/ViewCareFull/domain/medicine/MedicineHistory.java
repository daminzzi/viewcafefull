package com.ssafy.ViewCareFull.domain.medicine;

import com.ssafy.ViewCareFull.domain.common.entity.TodayType;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;

@Entity
@Getter
public class MedicineHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private Users userId;

  @NotNull
  @Enumerated(EnumType.STRING)
  private TodayType medicineType;

  @NotNull
  private LocalDate medicineDate;
}
