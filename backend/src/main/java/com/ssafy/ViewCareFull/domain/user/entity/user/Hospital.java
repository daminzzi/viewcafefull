package com.ssafy.ViewCareFull.domain.user.entity.user;

import com.ssafy.ViewCareFull.domain.user.entity.UserType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@DiscriminatorValue(UserType.Values.Hospital)
public class Hospital extends Users {

}
