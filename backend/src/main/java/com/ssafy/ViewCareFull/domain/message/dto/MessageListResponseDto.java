package com.ssafy.ViewCareFull.domain.message.dto;

import com.ssafy.ViewCareFull.domain.message.entity.Message;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageListResponseDto {

  private Long sum;
  private Long unreadMsgs;
  private Long pageNum;
  private List<MessageDto> messages;

  public MessageListResponseDto(Page<Message> page, int unreadMsgs) {
    this.sum = page.getTotalElements();
    this.unreadMsgs = (long) unreadMsgs;
    this.pageNum = (long) page.getTotalPages();
    this.messages = MessageDto.of(page.getContent());
  }
}
