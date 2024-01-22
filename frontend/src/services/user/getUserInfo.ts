import api from '../api';

export interface UserInfo {
  parentId: number[] | null;
  parentName: string[] | null;
  residentId: number | null;
  residentName: string | null;
  hospitalId: number;
  hospitalName: string;
  phoneNumber: string;
  birth: string;
  userRole: string;
}

// 로그인 된 회원(보호자)의 정보 조회
async function getUserInfo(): Promise<UserInfo | null> {
  try {
    const response = await api.get<UserInfo>(`/users`);
    if (response.status !== 200) {
      throw new Error(`오류: ${response.status}`);
    }
    return response.data;
  } catch (error) {
    console.error(error);
    return null;
  }
};

export default getUserInfo;
