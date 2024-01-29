import api from '../api';

async function getReportInfo(yearMonth: string) {
  try {
    const response = await api.get(`/report?month=${yearMonth}`);
    if (response.status !== 200) {
      throw new Error(`오류: ${response.status}`);
    }
    return response.data;
  } catch (e) {
    console.error(e);
  }
}

export default getReportInfo;
