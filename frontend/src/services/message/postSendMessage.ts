import api from '../api';

// 메세지 작성
async function postSendMessage(message: {
  to: string;
  title: string;
  content: string;
}) {
  try {
    const response = await api.post('/msg', { ...message });
    if (response.status === 201) {
      return response.data;
    } else {
      throw new Error('메세지 전송에 실패하였습니다.');
    }
  } catch (error) {
    console.error(error);
    throw error;
  }
}

export default postSendMessage;
