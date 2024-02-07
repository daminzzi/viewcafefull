import api from '../api';

// 메세지 상태변경(체크박스 안읽음->읽음)
async function postReadMessage(id: number) {
  try {
    const response = await api.post(`/msg/${id}`);
    if (response.status === 200) {
      return;
    } else {
      throw new Error('메세지를 읽지 못했습니다.');
    }
  } catch (error) {
    console.error(error);
    return null;
  }
}

export default postReadMessage;
