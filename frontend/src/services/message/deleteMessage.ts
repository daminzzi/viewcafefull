import api from "../api";

// 메세지 삭제
async function deleteMessage(id: number) {
  try {
    const response = await api.delete(`/msg?id=${id}`);
    if (response.status !== 204) {
      throw new Error(`오류: ${response.status}`);
    }
  } catch (error) {
    console.error(error);
    throw error;
  }
}

export default deleteMessage;