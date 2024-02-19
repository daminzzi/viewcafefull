package com.ssafy.ViewCareFull.domain.users.entity.user;

import com.ssafy.ViewCareFull.domain.users.entity.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue(UserType.Values.Caregiver)
public class Caregiver extends Users {

  @OneToOne
  @JoinColumn(name = "hospital_id")
  private Hospital hospital;

  @Column
  private String token;

}
