package com.ssafy.ViewCareFull.domain.gallery.repository;

import com.ssafy.ViewCareFull.domain.gallery.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {

  @Query(value = "SELECT i FROM Image i WHERE i.userId = :caregiverId ORDER BY i.imageDateTime DESC",
      countQuery = "SELECT COUNT(i) FROM Image i WHERE i.userId = :caregiverId")
  Page<Image> findAllByCaregiverId(Long caregiverId, Pageable pageable);
}
