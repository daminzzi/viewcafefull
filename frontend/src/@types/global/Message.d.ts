type Message = {
  id: number;
  from: string;
  title: string;
  content: string | ReportMessage;
  time: string;
  isRead: boolean;
};

type ReportMessage = {
  year: number;
  month: number;
  targetId: number;
  targetName: string;
};

type MessagesResponse = {
  sum: number;
  unreadMsgs: number;
  pageNum: number;
  messages: Message[];
};
