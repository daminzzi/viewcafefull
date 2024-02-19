package com.ssafy.ViewCareFull.domain.medicine.service;

import com.ssafy.ViewCareFull.domain.medicine.entity.Medicine;
import com.ssafy.ViewCareFull.domain.medicine.exception.MedicineNotExistException;
import com.ssafy.ViewCareFull.domain.medicine.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicineService {

  private final MedicineRepository medicineRepository;

  public Medicine getMedicine(Long medicineId) {
    return medicineRepository.findById(medicineId).orElseThrow(MedicineNotExistException::new);
  }
}
