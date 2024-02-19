package com.ssafy.ViewCareFull.domain.users.entity.user;

import com.ssafy.ViewCareFull.domain.users.entity.UserType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@SuperBuilder
@DiscriminatorValue(UserType.Values.Guardian)
@NoArgsConstructor
public class Guardian extends Users {
  
}
