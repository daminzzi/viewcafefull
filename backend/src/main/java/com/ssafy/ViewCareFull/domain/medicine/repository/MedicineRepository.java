package com.ssafy.ViewCareFull.domain.medicine.repository;

import com.ssafy.ViewCareFull.domain.medicine.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

}
