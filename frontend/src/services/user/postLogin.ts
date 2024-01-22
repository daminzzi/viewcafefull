import api from "../api";

// 로그인
async function postLogin(form: { id: string; password: string }) {
  try {
    const response = await api.post("/users/signin", form);
    if (response.status !== 200) {
      throw new Error(`오류: ${response.status}`);
    }
    return response.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
}

export default postLogin;
