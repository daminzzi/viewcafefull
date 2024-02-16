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

  Optional<UserLink> findFirstByCaregiver_Id(Long caregiverId);

  @Query("select ul from UserLink ul where ul.guardian.id = :userId")
  Optional<UserLink> findLinkByGuardianId(@Param("userId") Long userId);

  @Query("select ul from UserLink ul where ul.caregiver.id = (select ul.caregiver.id from UserLink ul where ul.guardian.id = :guardianId)")
  List<UserLink> findAllGuardianByGuardian(@Param("guardianId") Long guardianId);

  @Query("select ul from UserLink ul where ul.caregiver.id = :caregiverId")
  List<UserLink> findAllGuardianByCaregiver(@Param("caregiverId") Long caregiverId);

  @Query("select ul from UserLink ul where ul.guardian.id = :guardianId")
  List<UserLink> findCaregiverByGuardian(@Param("guardianId") Long guardianId);

  @Query("select ul from UserLink ul where ul.caregiver.id = (select ul.caregiver.id from UserLink ul where ul.guardian.domainId = :guardianId)")
  List<UserLink> findAllGuardianByGuardian(@Param("guardianId") String guardianDomainId);

  @Query("select ul from UserLink ul where ul.caregiver.domainId = :caregiverId")
  List<UserLink> findAllGuardianByCaregiver(@Param("caregiverId") String caregiverDomainId);

  @Query("select ul from UserLink ul where ul.guardian.domainId = :guardianId")
  List<UserLink> findCaregiverByGuardian(@Param("guardianId") String guardianDomainId);

  @Query("select ul from UserLink ul where ul.caregiver.id = :caregiverId")
  List<UserLink> findUserLinkByCaregiverId(@Param("caregiverId") Long caregiverId);
}
