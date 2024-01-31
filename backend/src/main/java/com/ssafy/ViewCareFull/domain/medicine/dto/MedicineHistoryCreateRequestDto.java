package com.ssafy.ViewCareFull.domain.medicine.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.ViewCareFull.domain.common.entity.TodayType;
import com.ssafy.ViewCareFull.domain.medicine.entity.Medicine;
import com.ssafy.ViewCareFull.domain.medicine.entity.MedicineHistory;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class MedicineHistoryCreateRequestDto {

  private Long medicineId;
  private String medicineType;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
  private LocalDate medicineDate;

  public MedicineHistory toEntity(Users user, Medicine medicine) {
    return MedicineHistory.builder()
        .user(user)
        .medicineType(TodayType.valueOf(medicineType))
        .medicineDate(medicineDate)
        .medicine(medicine)
        .build();
  }
}
