package com.ssafy.ViewCareFull.domain.report.util;

import com.ssafy.ViewCareFull.domain.report.dto.MonthlyHealthInfo;
import com.ssafy.ViewCareFull.domain.report.dto.MonthlyReport;

public interface OpenAIApi {

  MonthlyReport getMonthlyReportResponse(MonthlyHealthInfo healthData);
}
