package com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Message;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContentDto {

  String type;
  Text text;

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Text {

    String value;
    List<Object> annotations;
  }
}
