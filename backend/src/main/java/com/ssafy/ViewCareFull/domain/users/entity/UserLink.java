package com.ssafy.ViewCareFull.domain.users.entity;

import com.ssafy.ViewCareFull.domain.users.entity.user.Caregiver;
import com.ssafy.ViewCareFull.domain.users.entity.user.Guardian;
import com.ssafy.ViewCareFull.domain.users.entity.user.Hospital;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "user_link",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "user_link_unique",
            columnNames = {"permission_id", "target_id", "application_id"}
        )
    }
)
public class UserLink {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "permission_id")
  private Hospital hospital;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "target_id")
  private Caregiver caregiver;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "application_id")
  private Guardian guardian;

  @NotNull
  @Column
  @Enumerated(EnumType.STRING)
  private PermissionType agreement;

  @NotNull
  private String relationship;

  public static UserLink of(Guardian guardian, Caregiver caregiver, Hospital hospital, String relationship) {
    return UserLink.builder()
        .hospital(hospital)
        .caregiver(caregiver)
        .guardian(guardian)
        .relationship(relationship)
        .agreement(PermissionType.A)
        .build();
  }

  public void updateAgreement(String agreement) {
    this.agreement = PermissionType.valueOf(agreement);
  }
}
