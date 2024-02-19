package com.ssafy.ViewCareFull.domain.message.service;

import com.ssafy.ViewCareFull.domain.message.dto.MessageDto;
import com.ssafy.ViewCareFull.domain.message.dto.MessageListResponseDto;
import com.ssafy.ViewCareFull.domain.message.dto.MessageRequestDto;
import com.ssafy.ViewCareFull.domain.message.entity.Message;
import com.ssafy.ViewCareFull.domain.message.exception.NoMessageException;
import com.ssafy.ViewCareFull.domain.message.repository.MessageRepository;
import com.ssafy.ViewCareFull.domain.report.dto.ReportMessageDto;
import com.ssafy.ViewCareFull.domain.users.entity.user.Caregiver;
import com.ssafy.ViewCareFull.domain.users.entity.user.Guardian;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import com.ssafy.ViewCareFull.domain.users.service.UserLinkService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MessageService {

  private final MessageRepository messageRepository;
  private final UserLinkService userLinkService;

  public MessageListResponseDto getMessages(SecurityUsers securityUsers, Pageable pageable, String keyword) {
    String domainId = securityUsers.getUsername();
    Page<Message> pageObject = messageRepository.findByMemberIdAndMessageContentContaining(
        domainId, keyword, pageable);
    Integer unreadMsgs = messageRepository.countByToIdAndIsReadFalse(domainId);
    return new MessageListResponseDto(pageObject, unreadMsgs);
  }

  @Transactional
  public void sendMessage(SecurityUsers securityUsers, MessageRequestDto message) {
    String fromId = securityUsers.getUsername();
    Message newMessage = new Message(fromId, message);
    messageRepository.save(newMessage);
  }


  @Transactional
  public MessageDto readMessage(SecurityUsers securityUsers, String id) {
    Message message = messageRepository.findById(Long.parseLong(id))
        .orElseThrow(NoMessageException::new);
    message.readThisMessage();
    return MessageDto.of(message);
  }

  @Transactional
  public void deleteMessage(SecurityUsers securityUsers, String id) {
    String domainId = securityUsers.getUsername();
    Message message = messageRepository.findByIdAndMemberId(Long.parseLong(id), domainId)
        .orElseThrow(NoMessageException::new);
    messageRepository.delete(message);
  }

  public List<MessageDto> getMonthlyMessages(String caregiverDomainId, LocalDate start, LocalDate end) {
    List<Message> messages = messageRepository.findByDomainIdAndDateBetween(caregiverDomainId, start.atStartOfDay(),
        end.atTime(LocalTime.MAX));
    return MessageDto.of(messages);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void sendReportMessages(Caregiver caregiver, ReportMessageDto reportMessageDto) {
    List<Guardian> guardians = userLinkService.getGuardiansByCaregiverId(caregiver.getId());
    for (Guardian guardian : guardians) {
      String title = reportMessageDto.getYear() + "년 " + reportMessageDto.getMonth() + "월 건강레포트";
      MessageRequestDto message = MessageRequestDto.builder()
          .to(guardian.getDomainId())
          .title(title)
          .content(reportMessageDto.toJson())
          .build();
      sendMessage(caregiver.getHospital(), message);
      log.info("월간 레포트 메시지 전송 완료");
      log.info("보호자 : " + guardian.getDomainId());
      log.info("메세지 내용 : " + message.getContent());
    }
  }

  private void sendMessage(Users users, MessageRequestDto message) {
    Message newMessage = new Message(users.getDomainId(), message);
    messageRepository.save(newMessage);
  }
}
