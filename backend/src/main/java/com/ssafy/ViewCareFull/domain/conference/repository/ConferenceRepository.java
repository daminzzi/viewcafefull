package com.ssafy.ViewCareFull.domain.conference.repository;

import com.ssafy.ViewCareFull.domain.conference.entity.Conference;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {

  @Query(value = "SELECT c FROM Conference c WHERE c.hospital.id = :permissionId order by c.id desc")
  Optional<List<Conference>> findAllByHospitalId(@Param("permissionId") Long permissionId);

  @Query(value = "SELECT c FROM Conference c WHERE c.hospital.id = :permissionId and c.createdDateTime between :startDate and :endDate order by c.id desc")
  Optional<List<Conference>> findAllByHospitalIdBetweenDate(@Param("permissionId") Long permissionId,
      @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

  @Query("SELECT c FROM Conference c WHERE c.id IN (SELECT r.conference.id FROM ConferenceReservation r WHERE r.guardian = :applicationId) ORDER BY c.id DESC")
  Optional<List<Conference>> findAllByGuardianId(@Param("applicationId") Long applicationId);

  @Query("SELECT c FROM Conference c WHERE c.id IN (SELECT r.conference.id FROM ConferenceReservation r WHERE r.guardian = :applicationId) and c.createdDateTime between :startDate and :endDate ORDER BY c.id DESC")
  Optional<List<Conference>> findAllByGuardianIdBetweenDate(@Param("applicationId") Long applicationId,
      @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

  @Query("SELECT c FROM Conference c WHERE c.id = :conferenceId")
  Optional<Conference> getConferenceById(@Param("conferenceId") Long conferenceId);
}
