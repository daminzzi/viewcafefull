package com.ssafy.ViewCareFull.domain.health.repository;

import com.ssafy.ViewCareFull.domain.health.entity.Health;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthRepository extends JpaRepository<Health, Long> {

  @Query("select h from Health h where h.user.domainId = :userId and h.healthDate = :date")
  List<Health> findByDomainIdAndDate(@Param("userId") String userId, @Param("date") LocalDate date);

  @Query("select h from Health h where h.user.id = :userId and h.healthDate between :start and :end")
  List<Health> findAllByUserIdAndHealthDateBetween(@Param("userId") Long userId, @Param("start") LocalDate start,
      @Param("end") LocalDate end);
}
