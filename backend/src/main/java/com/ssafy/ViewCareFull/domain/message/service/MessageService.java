package com.ssafy.ViewCareFull.domain.message.service;

import com.ssafy.ViewCareFull.domain.message.dto.MessageDto;
import com.ssafy.ViewCareFull.domain.message.dto.MessageListResponseDto;
import com.ssafy.ViewCareFull.domain.message.dto.MessageRequestDto;
import com.ssafy.ViewCareFull.domain.message.entity.Message;
import com.ssafy.ViewCareFull.domain.message.exception.NoMessageException;
import com.ssafy.ViewCareFull.domain.message.repository.MessageRepository;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

  private final MessageRepository messageRepository;

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
}
