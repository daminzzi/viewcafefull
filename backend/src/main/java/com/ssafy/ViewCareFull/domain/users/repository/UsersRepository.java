package com.ssafy.ViewCareFull.domain.users.repository;

import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

  @Query("SELECT u FROM Users u WHERE u.domainId = :domainId")
  Optional<Users> findByDomainId(@Param("domainId") String domainId);

  Optional<Users> findBykakaoId(String kakaoId);

  boolean existsByDomainId(String domainId);
}
