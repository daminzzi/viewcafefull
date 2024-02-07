type Message = {
  id: number;
  from: string;
  title: string;
  content: string;
  time: string;
  isRead: boolean;
};
type MessagesResponse = {
  sum: number;
  unreadMsgs: number;
  pageNum: number;
  messages: Message[];
};