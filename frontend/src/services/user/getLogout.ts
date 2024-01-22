import api from "../api";

// 로그아웃
async function getLogout(id: string) {
  try {
    const response = await api.get(`/users/signout`);
    if (response.status !== 200) {
      throw new Error(`오류: ${response.status}`);
    }
  } catch (error) {
    console.error(error);
    throw error;
  }
}

export default getLogout;
