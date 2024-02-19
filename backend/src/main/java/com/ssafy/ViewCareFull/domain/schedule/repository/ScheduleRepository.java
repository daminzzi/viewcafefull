package com.ssafy.ViewCareFull.domain.schedule.repository;

import com.ssafy.ViewCareFull.domain.schedule.entity.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

  @Query("SELECT s FROM Schedule s WHERE s.hospital.id = :hospitalId")
  List<Schedule> findAllById(Long hospitalId);
}
