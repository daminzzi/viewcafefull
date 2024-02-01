package com.ssafy.ViewCareFull.domain.medicine.service;

import com.ssafy.ViewCareFull.domain.medicine.dto.MedicineHistoryCreateRequestDto;
import com.ssafy.ViewCareFull.domain.medicine.dto.MedicineHistoryResponseDto;
import com.ssafy.ViewCareFull.domain.medicine.entity.Medicine;
import com.ssafy.ViewCareFull.domain.medicine.entity.MedicineHistory;
import com.ssafy.ViewCareFull.domain.medicine.exception.NoCaregiverException;
import com.ssafy.ViewCareFull.domain.medicine.repository.MedicineHistoryRepository;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.service.UsersService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedicineHistoryService {

  private final MedicineHistoryRepository medicineHistoryRepository;
  private final MedicineService medicineService;
  private final UsersService usersService;

  @Transactional
  public void createMedicineHistory(Users user, MedicineHistoryCreateRequestDto medicineHistoryCreateRequestDto) {
    Medicine medicine = medicineService.getMedicine(medicineHistoryCreateRequestDto.getMedicineId());
    medicineHistoryRepository.save(medicineHistoryCreateRequestDto.toEntity(user, medicine));
  }

  public List<MedicineHistoryResponseDto> getMedicineHistory(String domainId) {
    Users user = usersService.getUser(domainId);
    if (!user.getUserType().equals("Caregiver")) {
      throw new NoCaregiverException();
    }
    return medicineHistoryRepository.findAllByUserId(domainId);
  }

  public List<MedicineHistory> getMedicineHistoryListWithIdDate(String domainId, LocalDate date) {
    return medicineHistoryRepository.findByDomainIdAndDate(domainId, date);
  }
}
