package com.ssafy.ViewCareFull.domain.users.entity;

public enum UserType {
  Hospital(Values.Hospital), Guardian(Values.Guardian), Caregiver(Values.Caregiver);

  private UserType(String val) {
      if (!this.name().equals(val)) {
          throw new IllegalArgumentException("Incorrect use of UserType");
      }
  }

  public static class Values {

    public static final String Hospital = "Hospital";
    public static final String Guardian = "Guardian";
    public static final String Caregiver = "Caregiver";
  }
}
