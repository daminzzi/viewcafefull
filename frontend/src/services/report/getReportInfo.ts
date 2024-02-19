import api from '../api';

async function getReportInfo(
  targetId: string,
  yearMonth: string,
): Promise<ReportInfo> {
  let reportInfo: ReportInfo;
  try {
    const response = await api.get(`/report/${targetId}?month=${yearMonth}`);
    if (response.status !== 200) {
      throw new Error(`오류: ${response.status}`);
    }
    reportInfo = response.data;
    return reportInfo;
  } catch (e) {
    console.error(e);
    throw e;
  }
}

export default getReportInfo;
