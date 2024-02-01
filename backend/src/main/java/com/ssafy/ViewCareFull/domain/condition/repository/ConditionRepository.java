package com.ssafy.ViewCareFull.domain.condition.repository;

import com.ssafy.ViewCareFull.domain.common.entity.TimeType;
import com.ssafy.ViewCareFull.domain.condition.entity.Condition;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConditionRepository extends JpaRepository<Condition, Long> {

  Optional<Condition> findByUserAndDateAndTime(Users user, LocalDate date, TimeType timeType);

  @Query("select c from Condition c where c.user = :user and c.date between :start and :end")
  List<Condition> findByUserAndDateBetween(Users user, LocalDate start, LocalDate end);
}
