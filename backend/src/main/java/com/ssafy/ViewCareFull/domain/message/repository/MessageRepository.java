package com.ssafy.ViewCareFull.domain.message.repository;

import com.ssafy.ViewCareFull.domain.message.entity.Message;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Long> {

  @Query(value = "SELECT m FROM Message m WHERE m.toId = :domainId AND m.content LIKE %:keyword%",
      countQuery = "SELECT COUNT(m) FROM Message m WHERE m.toId = :domainId AND m.content LIKE %:keyword%")
  Page<Message> findByMemberIdAndMessageContentContaining(@Param("domainId") String domainId,
      @Param("keyword") String keyword, Pageable pageable);

  @Query(value = "SELECT m FROM Message m WHERE m.id = :messageId AND m.toId = :memberId")
  Optional<Message> findByIdAndMemberId(@Param("messageId") long messageId, @Param("memberId") String domainId);

  Integer countByToIdAndIsReadFalse(String domainId);

  @Query(value = "SELECT m FROM Message m WHERE m.fromId = :caregiverDomainId AND m.sendDateTime BETWEEN :start AND :end")
  List<Message> findByDomainIdAndDateBetween(String caregiverDomainId, LocalDateTime start, LocalDateTime end);
}
