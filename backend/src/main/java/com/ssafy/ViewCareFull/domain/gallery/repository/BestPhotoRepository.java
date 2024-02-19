package com.ssafy.ViewCareFull.domain.gallery.repository;

import com.ssafy.ViewCareFull.domain.gallery.entity.BestPhoto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BestPhotoRepository extends JpaRepository<BestPhoto, Long> {

  @Query(value = "SELECT b FROM BestPhoto b WHERE b.conference.id = :conferenceId ORDER BY b.score DESC")
  List<BestPhoto> findByConferenceId(Long conferenceId);
}
