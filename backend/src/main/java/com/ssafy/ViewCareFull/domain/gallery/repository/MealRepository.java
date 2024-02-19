package com.ssafy.ViewCareFull.domain.gallery.repository;

import com.ssafy.ViewCareFull.domain.gallery.entity.Meal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MealRepository extends JpaRepository<Meal, Long> {

  @Query("select m from Meal m where m.user.id = :caregiverId and m.mealDate = :day")
  List<Meal> findByCaregiverAndDay(@Param("caregiverId") Long caregiverId, @Param("day") LocalDate day);

  @Query("select m from Meal m where m.user.domainId = :domainId and m.mealDate = :date")
  List<Meal> findByDomainIdAndDate(String domainId, LocalDate date);
}
