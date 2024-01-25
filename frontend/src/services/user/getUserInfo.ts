// import api from '../api';
import userData from './userData.json';

// 로그인 된 회원의 정보 조회
async function getUserInfo(): Promise<UserInfo | null> {
  // try {
  //   const response = await api.get<UserInfo>(`/users`);
  //   if (response.status !== 200) {
  //     throw new Error(`오류: ${response.status}`);
  //   }
  //   return response.data;
  // } catch (error) {
  //   console.error(error);
  //   return null;
  // }
  return userData;
}

export default getUserInfo;
