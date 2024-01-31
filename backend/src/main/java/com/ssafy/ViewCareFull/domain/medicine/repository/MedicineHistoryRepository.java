package com.ssafy.ViewCareFull.domain.medicine.repository;

import com.ssafy.ViewCareFull.domain.medicine.dto.MedicineHistoryResponseDto;
import com.ssafy.ViewCareFull.domain.medicine.entity.MedicineHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicineHistoryRepository extends JpaRepository<MedicineHistory, Long> {

  @Query("SELECT m FROM MedicineHistory m WHERE m.user.domainId = :domainId")
  List<MedicineHistoryResponseDto> findAllByUserId(String domainId);

}
