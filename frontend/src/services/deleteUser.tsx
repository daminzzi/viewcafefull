import api from '../stores/api';


// 회원탈퇴
export const deleteUserAPI = async () => {
    try {
      await api.delete(`/users`);
    } catch (error) {
      console.error(error);
      throw error;
    }
  };