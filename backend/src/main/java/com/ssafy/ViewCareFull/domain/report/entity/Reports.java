package com.ssafy.ViewCareFull.domain.report.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reports {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "report_year")
  private int year;

  @Column(name = "report_month")
  private int month;

  @Column
  private Long caregiverId;

  @Lob
  @Column(columnDefinition = "LONGTEXT")
  private String reportInfo;

  public Reports copy(int month, String reportInfo, Long caregiverId) {
    return Reports.builder()
        .year(this.year)
        .month(month)
        .caregiverId(caregiverId)
        .reportInfo(reportInfo)
        .build();
  }
}
