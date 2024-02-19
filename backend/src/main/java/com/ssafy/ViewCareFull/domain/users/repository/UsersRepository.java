package com.ssafy.ViewCareFull.domain.users.repository;

import com.ssafy.ViewCareFull.domain.users.entity.user.Caregiver;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<Users, Long> {

  @Query("SELECT u FROM Users u WHERE u.domainId = :domainId")
  Optional<Users> findByDomainId(@Param("domainId") String domainId);

  Optional<Users> findBykakaoId(String kakaoId);

  boolean existsByDomainId(String domainId);

  @Query("SELECT u FROM Caregiver u WHERE u.token = :targetCode")
  Optional<Caregiver> findByToken(String targetCode);

  @Query("SELECT u FROM Caregiver u WHERE u.id = :id")
  Optional<Caregiver> findCaregiverById(@Param("id") Long id);

}
