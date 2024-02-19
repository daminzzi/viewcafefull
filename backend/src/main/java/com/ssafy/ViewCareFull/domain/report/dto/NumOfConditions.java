package com.ssafy.ViewCareFull.domain.report.dto;

import com.ssafy.ViewCareFull.domain.condition.entity.Conditions;
import java.util.List;
import lombok.Getter;

@Getter
public class NumOfConditions {

  private int cntGood;
  private int cntNormal;
  private int cntBad;

  public NumOfConditions(List<Conditions> conditionsList) {
    for (Conditions conditions : conditionsList) {
      switch (conditions.getCondition()) {
        case GOOD -> cntGood++;
        case NORMAL -> cntNormal++;
        case BAD -> cntBad++;
      }
    }

  }
}
