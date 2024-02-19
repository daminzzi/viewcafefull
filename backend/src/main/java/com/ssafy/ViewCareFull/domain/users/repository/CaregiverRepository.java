package com.ssafy.ViewCareFull.domain.users.repository;

import com.ssafy.ViewCareFull.domain.users.entity.user.Caregiver;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {

  Optional<Caregiver> findByDomainId(String domainId);

  Optional<Caregiver> findBykakaoId(String kakaoId);

  boolean existsByDomainId(String domainId);

  @Query("SELECT u FROM Caregiver u WHERE u.token = :targetCode")
  Optional<Caregiver> findByToken(String targetCode);

}
