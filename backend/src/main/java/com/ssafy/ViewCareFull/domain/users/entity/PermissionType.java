package com.ssafy.ViewCareFull.domain.users.entity;


import com.ssafy.ViewCareFull.domain.conference.error.ConferenceErrorCode;
import com.ssafy.ViewCareFull.domain.conference.error.exception.ConferenceException;

/**
 * S : 대기 A : 승인 D : 거부
 */
public enum PermissionType {

  S, A, D;

  public static PermissionType of(String permissionType) {
    switch (permissionType) {
      case "S":
        return S;
      case "A":
        return A;
      case "D":
        return D;
      default:
        throw new ConferenceException(ConferenceErrorCode.INVALID_TYPE);
    }
  }
}
