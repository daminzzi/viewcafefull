import api from '../api';

// 회원탈퇴
async function deleteUser() {
  try {
    const response = await api.delete('/users');
    if (response.status !== 204) {
      throw new Error(`오류: ${response.status}`);
    }
  } catch (error) {
    console.error(error);
    throw error;
  }
}

export default deleteUser;
