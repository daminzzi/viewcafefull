// 건강 정보 조회
import api from '../api';

async function getHealth(
  domainId: string,
  date: string,
): Promise<HealthResponse> {
  try {
    const response = await api.get(`/main/${domainId}`, { params: { date } });
    if (response.status !== 200) {
      throw new Error(`오류: ${response.status}`);
    }
    return response.data as HealthResponse;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export default getHealth;
