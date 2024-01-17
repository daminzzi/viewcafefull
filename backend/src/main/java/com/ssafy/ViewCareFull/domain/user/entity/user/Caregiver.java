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
@DiscriminatorValue(UserType.Values.Caregiver)
public class Caregiver extends Users {

  @Column
  private String token;
}
