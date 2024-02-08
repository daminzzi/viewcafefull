import api from '../api';

async function getCondition(start: string, end: string) {
  try {
    const response = await api.get('/condition', { params: { start, end } });
    if (response.status !== 200) {
      throw new Error(`오류: ${response.status}`);
    }
    return response.data;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export default getCondition;
