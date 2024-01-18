package com.ssafy.ViewCareFull.domain.users.entity.user;

import com.ssafy.ViewCareFull.domain.users.entity.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@DiscriminatorValue(UserType.Values.Caregiver)
public class Caregiver extends Users {

  @Column
  private String token;
}
