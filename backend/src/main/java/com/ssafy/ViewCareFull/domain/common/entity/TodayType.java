package com.ssafy.ViewCareFull.domain.common.entity;

/**
 * B : 아침 L : 점심 D : 저녁
 */
public enum TodayType {
  B, L, D;

  public static TodayType findByKorean(String korean) {
    return switch (korean) {
      case "아침" -> B;
      case "점심" -> L;
      case "저녁" -> D;
      default -> throw new IllegalArgumentException();
    };
  }

  public String getKorean() {
    return switch (this) {
      case B -> "아침";
      case L -> "점심";
      case D -> "저녁";
    };
  }
}
