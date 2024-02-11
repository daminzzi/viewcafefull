import api from '../api';

async function getVisitList(params: { type: PathType; domainId: string }) {
  try {
    const response = await api.get(`/conference/${params.type}/list`);
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
