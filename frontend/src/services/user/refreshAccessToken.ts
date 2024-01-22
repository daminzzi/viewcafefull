import api from "../api";

// access토큰 만료시 재발급
async function refreshAccessToken() {
  try {
    const response = await api.post("/users/token");
    const newToken = response.data.accessToken;
    return newToken;
  } catch (error) {
    console.error(error);
    throw error;
  }
}

export default refreshAccessToken;
