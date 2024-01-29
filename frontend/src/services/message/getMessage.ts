import api from '../api';
// import MessageData from './MessageData.json';

export interface Message {
  id: number;
  from: string;
  title: string;
  content: string;
  time: string;
  isRead: boolean;
}

export interface MessagesResponse {
  sum: number;
  unreadMsgs: number;
  messages: Message[];
}

// 보호자가 받은 메세지 페이지 형태로 조회
// async function getMessage(): Promise<MessagesResponse | null> {
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
  // return MessageData;
}

export default getMessage;
