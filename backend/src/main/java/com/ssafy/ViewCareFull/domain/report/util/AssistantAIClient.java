package com.ssafy.ViewCareFull.domain.report.util;

import static java.lang.Thread.sleep;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ViewCareFull.domain.report.dto.MonthlyHealthInfo;
import com.ssafy.ViewCareFull.domain.report.dto.MonthlyReport;
import com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Assistant.AssistantRequest;
import com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Assistant.AssistantResponse;
import com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Message.OpenAICompleteMessageListResponse;
import com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Message.OpenAICompletedMessage;
import com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Message.OpenAIMessageRequest;
import com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Message.OpenAIMessageResponse;
import com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Message.OpenAIRunningMessageListResponse;
import com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Run.RunRequest;
import com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Run.RunResponse;
import com.ssafy.ViewCareFull.domain.report.dto.OpenAI.ThreadInfo;
import com.ssafy.ViewCareFull.domain.report.dto.OpenAI.ThreadResponse;
import com.ssafy.ViewCareFull.domain.report.error.ReportErrorCode;
import com.ssafy.ViewCareFull.domain.report.error.exception.ReportException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AssistantAIClient implements OpenAIApi {

  @Value("${openai.api.key}")
  private String apiKey;

  @Value("${openai.api.model}")
  private String model;

  @Value("${openai.api.assistantId}")
  private String assistantId;

  private ObjectMapper objectMapper;


  @Override
  public MonthlyReport getMonthlyReportResponse(MonthlyHealthInfo content) {
    objectMapper = new ObjectMapper();
//    AssistantResponse assistantResponse = createAssistant();
    ThreadResponse threadResponse = createThread();

    try {
      OpenAIMessageResponse messageResponse = sendMessage(threadResponse.getId(), "user", content);
    } catch (JsonProcessingException e) {
      throw new ReportException(ReportErrorCode.NOT_MATCHED_JSON_FORMAT);
    }
    ThreadInfo threadInfo = new ThreadInfo(threadResponse.getId(), apiKey);
    RunResponse runResponse = runMessage(threadResponse.getId(), assistantId);

    waitMessageForSeconds(threadResponse.getId(), runResponse.getId(), 30)
        .orElseThrow(() -> new ReportException(ReportErrorCode.CREATE_REPORT_NOT_YET, threadInfo));

    OpenAICompleteMessageListResponse completedMessageListResponse = getMessages(threadResponse.getId());
    OpenAICompletedMessage openAICompletedMessage = completedMessageListResponse.getReportHealthInfo()
        .orElseThrow(() -> new ReportException(ReportErrorCode.NOT_FOUND_CREATED_REPORT, threadInfo));

    MonthlyReport monthlyReport = matchMonthlyReport(openAICompletedMessage).orElseThrow(
        () -> new ReportException(ReportErrorCode.NOT_MATCHED_JSON_FORMAT, threadInfo));

    threadInfo.deleteThread();
    log.info("[AssistantAIClient] Close Thread. Success to create report!!");
    return monthlyReport;
  }

  private Optional<MonthlyReport> matchMonthlyReport(OpenAICompletedMessage openAICompletedMessage) {
    try {
      MonthlyReport monthlyReport = objectMapper.readValue(openAICompletedMessage.getContent()[0].getText().getValue(),
          MonthlyReport.class);
      return Optional.of(monthlyReport);
    } catch (JsonProcessingException e) {
      throw new ReportException(ReportErrorCode.NOT_MATCHED_JSON_FORMAT);
    }
  }

  public Optional<String> waitMessageForSeconds(String threadId, String runId, int seconds) {
    for (int i = 0; i < seconds; i++) {
      RunResponse retriveRunResponse = RestApiUtil.get(apiKey, "/threads/" + threadId + "/runs/" + runId,
          RunResponse.class);
      OpenAIRunningMessageListResponse runningMessageListResponse = RestApiUtil.get(apiKey,
          "/threads/" + threadId + "/runs/" + runId + "/steps",
          OpenAIRunningMessageListResponse.class);
      Optional<String> runCompleted = OpenAIChkUtil.findRunCompleted(runningMessageListResponse);
      if (runCompleted.isPresent()) {
        log.info("[AssistantAIClient] Find Completed Message OpenAI created!!");
        return runCompleted;
      }
      log.info("Every running state is in progress... wait 1 second");
      waitSeconds(1000);
    }
    return Optional.empty();
  }

  public static void waitSeconds(int seconds) {
    try {
      sleep(seconds);
    } catch (InterruptedException e) {
      throw new ReportException(ReportErrorCode.CREATE_REPORT_NOT_YET);
    }
  }

  private AssistantResponse createAssistant() {
    AssistantRequest assistantRequest = new AssistantRequest(model);
    return RestApiUtil.post(apiKey, "/assistants", assistantRequest, AssistantResponse.class);
  }

  private ThreadResponse createThread() {
    return RestApiUtil.post(apiKey, "/threads", "", ThreadResponse.class);
  }

  private OpenAIMessageResponse sendMessage(String threadId, String role, MonthlyHealthInfo content)
      throws JsonProcessingException {
    OpenAIMessageRequest messageRequest = new OpenAIMessageRequest(role,
        objectMapper.writeValueAsString(content).replace("\"", "\\\\\""));
    return RestApiUtil.post(apiKey, "/threads/" + threadId + "/messages", messageRequest, OpenAIMessageResponse.class);
  }

  private RunResponse runMessage(String threadId, String assistantId) {
    RunRequest runRequest = new RunRequest(assistantId);
    return RestApiUtil.post(apiKey, "/threads/" + threadId + "/runs", runRequest, RunResponse.class);
  }

  private OpenAICompleteMessageListResponse getMessages(String threadId) {
    return RestApiUtil.get(apiKey, "/threads/" + threadId + "/messages", OpenAICompleteMessageListResponse.class);
  }

}
