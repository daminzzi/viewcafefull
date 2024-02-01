package com.ssafy.ViewCareFull.domain.main.dto;

import com.ssafy.ViewCareFull.domain.gallery.entity.Meal;
import com.ssafy.ViewCareFull.domain.health.entity.Health;
import com.ssafy.ViewCareFull.domain.health.entity.HealthType;
import com.ssafy.ViewCareFull.domain.medicine.entity.MedicineHistory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class MainResponseDto {

  private String id;
  private List<MainHealthDto> health;
  private MainMedicineDto medicine;
  private MainMealDto meal;

  public MainResponseDto(String domainId, List<Health> healthInfo, List<MedicineHistory> medicineInfo,
      List<Meal> mealInfo, String serverUrl) {
    this.id = domainId;
    this.health = makeHealthDto(healthInfo);
    this.medicine = new MainMedicineDto(medicineInfo);
    this.meal = new MainMealDto(mealInfo, serverUrl);
  }

  private List<MainHealthDto> makeHealthDto(List<Health> healthInfo) {
    Map<HealthType, MainHealthDto> healthMap = new HashMap<>();
    for (Health health : healthInfo) {
      HealthType healthType = health.getHealthType();
      if (healthMap.containsKey(healthType)) {
        healthMap.get(healthType).addData(health);
        continue;
      }
      healthMap.put(healthType, new MainHealthDto(health));
    }
    return List.copyOf(healthMap.values());
  }
}
