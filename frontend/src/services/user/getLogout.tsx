import api from '../api';

// 로그아웃
export const logoutAPI = async (id: string) => {
    try {
      await api.get(`/users/signout`);
    } catch (error) {
      console.error(error);
      throw error;
    }
  };