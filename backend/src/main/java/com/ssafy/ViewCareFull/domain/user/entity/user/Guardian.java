package com.ssafy.ViewCareFull.domain.user.entity.user;

import com.ssafy.ViewCareFull.domain.user.entity.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue(UserType.Values.Guardian)
public class Guardian extends Users {

  @Column(name = "kakao_id")
  private String kakaoId;

  @Column(name = "naver_id")
  private String naverId;
}
