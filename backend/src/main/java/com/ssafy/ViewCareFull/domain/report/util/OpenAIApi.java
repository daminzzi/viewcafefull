package com.ssafy.ViewCareFull.domain.report.util;

import com.ssafy.ViewCareFull.domain.report.dto.HealthIndicatorData;
import com.ssafy.ViewCareFull.domain.report.dto.MonthlyReport;

public interface OpenAIApi {

  MonthlyReport getMontlyReportResponse(HealthIndicatorData healthData);
}
