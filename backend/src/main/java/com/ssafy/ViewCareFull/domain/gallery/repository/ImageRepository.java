package com.ssafy.ViewCareFull.domain.gallery.repository;

import com.ssafy.ViewCareFull.domain.gallery.entity.Image;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Long> {

  @Query(value = "SELECT i FROM Image i WHERE i.userId = :caregiverId ORDER BY i.imageDateTime DESC",
      countQuery = "SELECT COUNT(i) FROM Image i WHERE i.userId = :caregiverId")
  Page<Image> findAllByCaregiverId(Long caregiverId, Pageable pageable);

  @Query(value = "SELECT i FROM Image i where i not in (select m.image from Meal m) and month(i.imageDateTime) = :month and i.userId = :userId and year(i.imageDateTime) = :year")
  List<Image> findAllNotInMealWithMonthAndUser(Integer year, Integer month, Long userId);

  @Query(value = "SELECT i FROM Image i INNER JOIN BestPhoto b ON i.id = b.image.id  where i.userId = :caregiverId and i.imageDateTime between :startDate and :endDate")
  List<Image> findBestPhotoImageByCaregiverIdBetweenDate(@Param("caregiverId") Long caregiverId,
      @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

  @Query(value = "SELECT i FROM Image i LEFT JOIN Meal m ON i.id = m.image.id LEFT JOIN BestPhoto b ON i.id = b.image.id where i.userId = :caregiverId and i.imageDateTime between :startDate and :endDate AND m.image.id IS NULL AND b.image.id IS NULL")
  List<Image> findNoneMealImageByCaregiverIdBetweenDate(@Param("caregiverId") Long caregiverId,
      @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
