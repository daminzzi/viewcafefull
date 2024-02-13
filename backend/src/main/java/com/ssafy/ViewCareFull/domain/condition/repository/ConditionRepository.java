package com.ssafy.ViewCareFull.domain.condition.repository;

import com.ssafy.ViewCareFull.domain.condition.entity.Conditions;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionRepository extends JpaRepository<Conditions, Long> {

  @Query("select c from Conditions c where c.user = :user and c.date = :date")
  Optional<Conditions> findByUserAndDate(Users user, LocalDate date);

  @Query("select c from Conditions c where c.user = :user and c.date between :start and :end")
  List<Conditions> findByUserAndDateBetween(Users user, LocalDate start, LocalDate end);

  @Query("select c from Conditions c where c.user.id = :id and c.date between :start and :end")
  List<Conditions> findAllByUserDateBetween(@Param("id") long id, @Param("start") LocalDate start,
      @Param("end") LocalDate end);
}
