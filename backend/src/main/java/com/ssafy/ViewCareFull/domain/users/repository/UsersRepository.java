package com.ssafy.ViewCareFull.domain.users.repository;

import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

  Optional<Users> findByDomainId(String domainId);

  boolean existsByDomainId(String domainId);
}
