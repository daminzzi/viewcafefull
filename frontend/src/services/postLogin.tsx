import api from '../stores/api';

// 로그인
export const loginAPI = async (
  form: { id: string; password: string }
) => {
  try {
    const response = await api.post('/users/signin', form);
    return response.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};