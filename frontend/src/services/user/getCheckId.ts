import api from '../api';

// 아이디 중복 체크
async function getCheckId(id: string) {
  try {
    const response = await api.get(`/users/validation/${id}`);
    if (response.status === 200) {
      return;
    }
  } catch (error) {
    console.error(error);
    throw error;
  }
}

export default getCheckId;
