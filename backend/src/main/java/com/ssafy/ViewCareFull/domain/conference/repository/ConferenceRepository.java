package com.ssafy.ViewCareFull.domain.conference.repository;

import com.ssafy.ViewCareFull.domain.conference.entity.Conference;
import com.ssafy.ViewCareFull.domain.users.entity.PermissionType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {

  @Query(value = "SELECT c FROM Conference c WHERE c.hospital.id = :permissionId order by c.id desc")
  Optional<List<Conference>> findAllByHospitalId(@Param("permissionId") Long permissionId);

  @Query(value = "SELECT c FROM Conference c join fetch ConferenceRoom cr on cr.conference.id = c.id WHERE c.hospital.id = :permissionId and c.createdDateTime between :startDate and :endDate order by c.id desc")
  Optional<List<Conference>> findAllByHospitalIdBetweenDate(@Param("permissionId") Long permissionId,
      @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

  @Query(value = "SELECT c FROM Conference c WHERE c.id IN (SELECT r.conference.id FROM ConferenceReservation r WHERE r.guardian = :applicationId) ORDER BY c.id DESC",
      countQuery = "SELECT count(c) FROM Conference c WHERE c.id IN (SELECT r.conference.id FROM ConferenceReservation r WHERE r.guardian = :applicationId)")
  Page<Conference> findAllByGuardianId(@Param("applicationId") Long applicationId, Pageable pageable);

  @Query(value = "SELECT c FROM Conference c WHERE c.id IN (SELECT r.conference.id FROM ConferenceReservation r WHERE r.guardian = :applicationId) and c.createdDateTime between :startDate and :endDate ORDER BY c.id DESC",
      countQuery = "SELECT count(c) FROM Conference c WHERE c.id IN (SELECT r.conference.id FROM ConferenceReservation r WHERE r.guardian = :applicationId) and c.createdDateTime between :startDate and :endDate")
  Page<Conference> findAllByGuardianIdBetweenDate(@Param("applicationId") Long applicationId,
      @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

  @Query("SELECT c FROM Conference c WHERE c.id IN (SELECT r.conference.id FROM ConferenceReservation r WHERE r.guardian = :applicationId) and c.conferenceDate=:today and c.conferenceState=:conferenceState ORDER BY c.conferenceDate")
  Optional<List<Conference>> findAllByGuardianIdAndPermissionState(@Param("applicationId") Long applicationId,
      @Param("conferenceState") PermissionType conferenceState, @Param("today") LocalDate date);

  @Query("SELECT count(c) FROM Conference c WHERE c.hospital.id=:permissionId and c.conferenceState=:conferenceState ORDER BY c.id DESC")
  int countByHospitalIdAndPermissionState(@Param("permissionId") Long permissionId, @Param("conferenceState")
  PermissionType conferenceState);

  @Query("SELECT c FROM Conference c WHERE c.id = :conferenceId")
  Optional<Conference> getConferenceById(@Param("conferenceId") Long conferenceId);
}
