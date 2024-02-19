package com.ssafy.ViewCareFull.domain.medicine.repository;

import com.ssafy.ViewCareFull.domain.medicine.dto.MedicineHistoryResponseDto;
import com.ssafy.ViewCareFull.domain.medicine.entity.MedicineHistory;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicineHistoryRepository extends JpaRepository<MedicineHistory, Long> {

  @Query("SELECT m FROM MedicineHistory m WHERE m.user.domainId = :domainId")
  List<MedicineHistoryResponseDto> findAllByUserId(String domainId);

  @Query("SELECT m FROM MedicineHistory m WHERE m.user.domainId = :domainId AND m.medicineDate = :date")
  List<MedicineHistory> findByDomainIdAndDate(String domainId, LocalDate date);
}
