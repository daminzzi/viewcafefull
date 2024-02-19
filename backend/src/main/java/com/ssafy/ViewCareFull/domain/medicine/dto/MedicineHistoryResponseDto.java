package com.ssafy.ViewCareFull.domain.medicine.dto;

import com.ssafy.ViewCareFull.domain.medicine.entity.MedicineHistory;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class MedicineHistoryResponseDto {

  private final Long id;
  private final Long medicineId;
  private final String medicineName;
  private final String information;
  private final String medicineType;
  private final String medicineDate;

  public MedicineHistoryResponseDto(MedicineHistory medicineHistory) {
    this.id = medicineHistory.getId();
    this.medicineId = medicineHistory.getMedicine().getId();
    this.medicineName = medicineHistory.getMedicine().getMedicineName();
    this.information = medicineHistory.getMedicine().getInformation();
    this.medicineType = medicineHistory.getMedicineType().toString();
    this.medicineDate = medicineHistory.getMedicineDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
  }
}
