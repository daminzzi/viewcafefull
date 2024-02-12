package com.ssafy.ViewCareFull.domain.users.entity.user;

import com.ssafy.ViewCareFull.domain.users.dto.JoinForm;
import com.ssafy.ViewCareFull.domain.users.dto.TokenInfo;
import com.ssafy.ViewCareFull.domain.users.error.exception.UserTypeException;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@AllArgsConstructor
@NoArgsConstructor
public abstract class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "domain_id")
  private String domainId;

  @NotNull
  @Column
  private String password;

  @NotNull
  @Column(name = "user_name")
  private String userName;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column
  private String email;

  @Column
  private String address;

  @Column
  private String brith;

  @Column
  private String refreshToken;

  @Column(name = "kakao_id")
  private String kakaoId;

  @Column(name = "naver_id")
  private String naverId;


  public static Users createUser(JoinForm joinForm, PasswordEncoder passwordEncoder) {
    switch (joinForm.getUserType()) {
      case Caregiver:
        return Caregiver.builder()
            .domainId(joinForm.getId())
            .password(passwordEncoder.encode(joinForm.getPassword()))
            .userName(joinForm.getName())
            .phoneNumber(joinForm.getPhoneNumber())
            .brith(joinForm.getBirth())
            .build();
      case Guardian:
        return Guardian.builder()
            .domainId(joinForm.getId())
            .password(passwordEncoder.encode(joinForm.getPassword()))
            .userName(joinForm.getName())
            .phoneNumber(joinForm.getPhoneNumber())
            .brith(joinForm.getBirth())
            .kakaoId(joinForm.getEmail())
            .build();
      case Hospital:
        return Hospital.builder()
            .domainId(joinForm.getId())
            .password(passwordEncoder.encode(joinForm.getPassword()))
            .userName(joinForm.getName())
            .phoneNumber(joinForm.getPhoneNumber())
            .brith(joinForm.getBirth())
            .build();
    }
    throw new UserTypeException();
  }

  public String getUserType() {
    return this.getClass().getAnnotation(DiscriminatorValue.class).value();
  }

  public void issueRefreshToken(TokenInfo tokenInfo) {
    refreshToken = tokenInfo.getRefreshToken();
  }

  public void deleteRefreshToken() {
    refreshToken = null;
  }
}


