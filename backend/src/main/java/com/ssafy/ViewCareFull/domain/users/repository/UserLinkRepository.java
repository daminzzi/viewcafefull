package com.ssafy.ViewCareFull.domain.users.repository;

import com.ssafy.ViewCareFull.domain.users.entity.UserLink;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserLinkRepository extends JpaRepository<UserLink, Long> {

  @Query("select ul from UserLink ul where ul.caregiver.id = :userId")
  Optional<UserLink> findLinkByCaregiverId(@Param("userId") Long userId);

  @Query("select ul from UserLink ul where ul.hospital.id = :userId")
  Optional<UserLink> findLinkByHospitalId(@Param("userId") Long userId);

  @Query("select ul from UserLink ul where ul.caregiver.id = (select ul.caregiver.id from UserLink ul where ul.guardian.id = :guardianId)")
  Optional<List<UserLink>> findAllGuardianByGuardian(@Param("guardianId") Long guardianId);

  @Query("select ul from UserLink ul where ul.caregiver.id = :caregiverId")
  Optional<List<UserLink>> findAllGuardianByCaregiver(@Param("caregiverId") Long caregiverId);

  @Query("select ul from UserLink ul where ul.guardian.id = :guardianId")
  Optional<List<UserLink>> findCaregiverByGuardian(@Param("guardianId") Long guardianId);
}
