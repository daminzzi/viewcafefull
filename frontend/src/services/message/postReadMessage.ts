import api from '../api';

// 메세지 상태변경(체크박스로 선택해서 모두읽음)
async function postReadMessage(id: number) {
  try {
    const response = await api.post(`/msg?id=${id}`);
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

export default postReadMessage;
