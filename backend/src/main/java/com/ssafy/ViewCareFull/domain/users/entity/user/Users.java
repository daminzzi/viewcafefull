package com.ssafy.ViewCareFull.domain.users.entity.user;

import com.ssafy.ViewCareFull.domain.users.dto.TokenInfo;
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

  public String getUserType(){
    return this.getClass().getAnnotation(DiscriminatorValue.class).value().toString();
  }

  public void issueRefreshToken(TokenInfo tokenInfo) {
    refreshToken = tokenInfo.getRefreshToken();
  }
}


