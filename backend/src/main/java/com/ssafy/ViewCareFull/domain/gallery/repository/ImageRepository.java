package com.ssafy.ViewCareFull.domain.gallery.repository;

import com.ssafy.ViewCareFull.domain.gallery.entity.Image;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {

  @Query(value = "SELECT i FROM Image i WHERE i.userId = :caregiverId ORDER BY i.imageDateTime DESC",
      countQuery = "SELECT COUNT(i) FROM Image i WHERE i.userId = :caregiverId")
  Page<Image> findAllByCaregiverId(Long caregiverId, Pageable pageable);

  @Query(value = "SELECT i FROM Image i where i.id not in (select m.id from Meal m) and month(i.imageDateTime) = :month and i.userId = :userId")
  List<Image> findAllNotInMealWithMonthAndUser(Integer month, Long userId);
}
