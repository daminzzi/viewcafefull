package com.ssafy.ViewCareFull.domain.conference.repository;

import com.ssafy.ViewCareFull.domain.conference.entity.Conference;
import com.ssafy.ViewCareFull.domain.users.entity.PermissionType;
import java.time.LocalDate;
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

  @Query(value = "SELECT c FROM Conference c WHERE c.hospital.id = :permissionId",
      countQuery = "SELECT count(c) FROM Conference c WHERE c.hospital.id = :permissionId")
  Page<Conference> findAllByHospitalId(@Param("permissionId") Long permissionId, Pageable pageable);

  @Query(value = "SELECT c FROM Conference c WHERE c.hospital.id = :permissionId and c.conferenceDate between :startDate and :endDate",
      countQuery = "SELECT count(c) FROM Conference c WHERE c.hospital.id = :permissionId and c.conferenceDate between :startDate and :endDate")
  Page<Conference> findAllByHospitalIdBetweenDate(@Param("permissionId") Long permissionId,
      @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

  @Query(value = "SELECT c FROM Conference c JOIN ConferenceReservation r ON c.id = r.conference.id WHERE r.guardian = :applicationId",
      countQuery = "SELECT count(c) FROM Conference c JOIN ConferenceReservation r ON c.id = r.conference.id WHERE r.guardian = :applicationId")
  Page<Conference> findAllByGuardianId(@Param("applicationId") Long applicationId, Pageable pageable);

  @Query(value = "SELECT c FROM Conference c JOIN ConferenceReservation r ON c.id = r.conference.id where r.guardian = :applicationId and c.conferenceDate between :startDate and :endDate",
      countQuery = "SELECT count(c) FROM Conference c JOIN ConferenceReservation r ON c.id = r.conference.id where r.guardian = :applicationId and c.conferenceDate between :startDate and :endDate")
  Page<Conference> findAllByGuardianIdBetweenDate(@Param("applicationId") Long applicationId,
      @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

  @Query("SELECT c FROM Conference c JOIN ConferenceReservation r ON c.id = r.conference.id WHERE r.guardian = :applicationId AND c.conferenceDate=:today And c.conferenceState=:conferenceState ORDER BY c.conferenceTime")
  List<Conference> findAllByGuardianIdAndPermissionState(@Param("applicationId") Long applicationId,
      @Param("conferenceState") PermissionType conferenceState, @Param("today") LocalDate date);

  @Query("SELECT count(c) FROM Conference c WHERE c.hospital.id=:permissionId and c.conferenceState=:conferenceState")
  int countByHospitalIdAndPermissionState(@Param("permissionId") Long permissionId, @Param("conferenceState")
  PermissionType conferenceState);

  @Query("SELECT c FROM Conference c WHERE c.id = :conferenceId")
  Optional<Conference> getConferenceById(@Param("conferenceId") Long conferenceId);
}
