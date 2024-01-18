package com.ssafy.ViewCareFull.domain.users.entity.user;

import com.ssafy.ViewCareFull.domain.users.dto.JoinForm;
import com.ssafy.ViewCareFull.domain.users.entity.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Entity
@Getter
@SuperBuilder
@DiscriminatorValue(UserType.Values.Guardian)
public class Guardian extends Users {

  @Column(name = "kakao_id")
  private String kakaoId;

  @Column(name = "naver_id")
  private String naverId;

  public static Guardian createUser(JoinForm joinForm, PasswordEncoder passwordEncoder) {
    return Guardian.builder()
        .domainId(joinForm.getId())
        .password(passwordEncoder.encode(joinForm.getPassword()))
        .userName(joinForm.getName())
        .phoneNumber(joinForm.getPhoneNumber())
        .brith(joinForm.getBirth())
        .build();
  }
}
