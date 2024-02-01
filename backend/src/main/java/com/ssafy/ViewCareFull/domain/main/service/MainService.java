package com.ssafy.ViewCareFull.domain.main.service;

import com.ssafy.ViewCareFull.domain.gallery.entity.Meal;
import com.ssafy.ViewCareFull.domain.gallery.service.MealService;
import com.ssafy.ViewCareFull.domain.health.entity.Health;
import com.ssafy.ViewCareFull.domain.health.service.HealthService;
import com.ssafy.ViewCareFull.domain.main.dto.MainResponseDto;
import com.ssafy.ViewCareFull.domain.medicine.entity.MedicineHistory;
import com.ssafy.ViewCareFull.domain.medicine.service.MedicineHistoryService;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.error.exception.UserTypeException;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import com.ssafy.ViewCareFull.domain.users.service.UsersService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MainService {

  private final UsersService usersService;
  private final HealthService healthService;
  private final MedicineHistoryService medicineHistoryService;
  private final MealService mealService;
  @Value("${file.server.url}")
  private String serverUrl;

  public MainResponseDto getMain(SecurityUsers securityUsers, String domainId, LocalDate date) {
    Users user = usersService.getUser(domainId);
    validateCaregiver(user);
    List<Health> healthInfo = healthService.getHealthListWithIdDate(domainId, date);
    List<MedicineHistory> medicineInfo = medicineHistoryService.getMedicineHistoryListWithIdDate(domainId, date);
    List<Meal> mealInfo = mealService.getMealListWithIdDate(domainId, date);
    return new MainResponseDto(domainId, healthInfo, medicineInfo, mealInfo, serverUrl);
  }

  private static void validateCaregiver(Users user) {
    if (!user.getUserType().equals("Caregiver")) {
      throw new UserTypeException();
    }
  }
}
