import api from '../api';

// 메세지 페이지 형태로 조회
async function getMessage(
  page: number,
  keyword?: string,
): Promise<MessagesResponse | null> {
  try {
    const response = await api.get<MessagesResponse>(
      `/msg?page=${page}${keyword ? `&keyword=${keyword}` : ''}`,
    );
    if (response.status !== 200) {
      throw new Error(`오류: ${response.status}`);
    }
    return response.data;
  } catch (error) {
    console.error(error);
    return null;
  }
}

export default getMessage;
