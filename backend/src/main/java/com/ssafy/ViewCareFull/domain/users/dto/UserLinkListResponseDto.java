package com.ssafy.ViewCareFull.domain.users.dto;

import com.ssafy.ViewCareFull.domain.users.entity.UserLink;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class UserLinkListResponseDto {

  private List<UserLinkResponseDto> userLinkList = new ArrayList<>();

  public UserLinkListResponseDto(List<UserLink> userLinkList) {
    for (UserLink userLink : userLinkList) {
      this.userLinkList.add(new UserLinkResponseDto(userLink));
    }
  }

}
