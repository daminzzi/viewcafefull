import api from '../api';

// 메세지 상태변경(모달 띄울 시 안읽음->읽음)
async function getReadMessage(id: number) {
  try {
    const response = await api.get(`/msg/${id}`);
    if (response.status === 200) {
      return response.data;
    } else {
      throw new Error('메세지를 읽지 못했습니다.');
    }
  } catch (error) {
    console.error(error);
    return null;
  }
}

export default getReadMessage;
