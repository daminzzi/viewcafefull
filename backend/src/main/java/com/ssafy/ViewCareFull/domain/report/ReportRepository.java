package com.ssafy.ViewCareFull.domain.report;

import com.ssafy.ViewCareFull.domain.report.entity.Reports;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository<Reports, Long> {

  @Query("SELECT r FROM Reports r WHERE r.caregiverId = :id AND r.year= :year and  r.month = :month")
  Optional<Reports> findByIdAndDate(long id, int year, int month);

}
