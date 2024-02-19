package com.ssafy.ViewCareFull.domain.users.dto;

import com.ssafy.ViewCareFull.domain.users.entity.UserLink;
import lombok.Getter;

@Getter
public class UserLinkResponseDto {

  private final Long applicationId;
  private final String appDomainId;
  private final String appName;
  private final Long targetId;
  private final String tarDomainId;
  private final String tarName;
  private final Long permissionId;
  private final String perDomainId;
  private final String perName;
  private final String agreement;
  private final String relationship;

  public UserLinkResponseDto(UserLink userLink) {
    this.applicationId = userLink.getGuardian().getId();
    this.appDomainId = userLink.getGuardian().getDomainId();
    this.appName = userLink.getGuardian().getUserName();
    this.targetId = userLink.getCaregiver().getId();
    this.tarDomainId = userLink.getCaregiver().getDomainId();
    this.tarName = userLink.getCaregiver().getUserName();
    this.permissionId = userLink.getHospital().getId();
    this.perDomainId = userLink.getHospital().getDomainId();
    this.perName = userLink.getHospital().getUserName();
    this.agreement = userLink.getAgreement().toString();
    this.relationship = userLink.getRelationship();
  }
}
