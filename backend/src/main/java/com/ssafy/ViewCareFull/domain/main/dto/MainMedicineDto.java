package com.ssafy.ViewCareFull.domain.main.dto;

import com.ssafy.ViewCareFull.domain.common.entity.TodayType;
import com.ssafy.ViewCareFull.domain.medicine.entity.MedicineHistory;
import java.util.List;
import lombok.Getter;

@Getter
public class MainMedicineDto {

  private boolean morning = false;
  private boolean noon = false;
  private boolean dinner = false;

  public MainMedicineDto(List<MedicineHistory> medicineInfo) {
    for (MedicineHistory medicine : medicineInfo) {
      TodayType todayType = medicine.getMedicineType();
      inputValueFromTodayType(todayType);
    }
  }

  private void inputValueFromTodayType(TodayType todayType) {
    if (todayType.equals(TodayType.B)) {
      this.morning = true;
    }
    if (todayType.equals(TodayType.L)) {
      this.noon = true;
    }
    if (todayType.equals(TodayType.D)) {
      this.dinner = true;
    }
  }
}
