package com.ssafy.ViewCareFull.domain.medicine.dto;

import com.ssafy.ViewCareFull.domain.common.entity.TodayType;
import com.ssafy.ViewCareFull.domain.medicine.entity.Medicine;
import com.ssafy.ViewCareFull.domain.medicine.entity.MedicineHistory;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MedicineHistoryCreateRequestDto {

  private Long medicineId;
  private String medicineType;
  private String medicineDate;

  public MedicineHistory toEntity(Users user, Medicine medicine) {
    return MedicineHistory.builder()
        .user(user)
        .medicineType(TodayType.valueOf(medicineType))
        .medicineDate(LocalDate.parse(medicineDate, DateTimeFormatter.ofPattern("yyyyMMdd")))
        .medicine(medicine)
        .build();
  }
}
