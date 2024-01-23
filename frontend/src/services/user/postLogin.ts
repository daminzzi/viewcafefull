import api from '../api';

// 로그인
async function postLogin(form: { id: string; password: string }) {
  try {
    const response = await api.post('/users/signin', form);
    if (response.status !== 200) {
      throw new Error(`오류: ${response.status}`);
    }

    // 존재하지 않는 사용자로 로그인 시도 시
    if (!response.data.user) {
      throw new Error('사용자를 찾을 수 없습니다.');
    }

    return response;
  } catch (error) {
    throw error;
  }
}

export default postLogin;
