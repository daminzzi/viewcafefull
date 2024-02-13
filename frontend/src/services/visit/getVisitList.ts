import api from '../api';

async function getVisitList(type: string) {
  try {
    const response = await api.get(`/conference/${type}/list`);
    if (response.status !== 200) {
      throw new Error(`오류: ${response.status}`);
    }
    return response.data;
  } catch (error) {
    console.log(error);
    throw error;
  }
}

export default getVisitList;
