import React, { useEffect, useState, Fragment } from 'react';
import getMessage, {
  Message,
  MessagesResponse,
} from '../../services/message/getMessage';
import postReadMessage from '../../services/message/postReadMessage';
import MessageDetailModal from '../../components/message/MessageDetailModal';
import Pagination from '../../components/common/Pagination';
// import getReadMessage from '../../services/message/getReadMessage';
import MessageSimple from '../../components/message/MessageSimple';
import useUserStore from '../../stores/userStore';

// 보호자 - 받은 메세지 페이지별 조회

function FamilyMessage() {
  const [currentPage, setCurrentPage] = useState<number>(1);
  const [keyword, setKeyword] = useState<string>();
  const [messagesData, setMessagesData] = useState<MessagesResponse | null>(
    null,
  );
  const [selectedMessage, setSelectedMessage] = useState<Message | null>(null);
  const [checkedMessages, setCheckedMessages] = useState<Message[]>([]);
  const { user } = useUserStore();
  const pageGroupSize = 5;

  useEffect(() => {
    const fetchData = async () => {
      const res = await getMessage(currentPage, keyword);
      setMessagesData(res);
    };
    fetchData();
  }, [currentPage, keyword, messagesData]);

  // 메세지 데이터 로딩 중
  if (!messagesData) {
    return <div>Now Loading...</div>;
  }

  // 메세지들 불러오기
  function renderMessages() {
    if (!messagesData) {
      return null;
    }
    const messageList = [];
    for (let i = 0; i < messagesData.messages.length; i++) {
      const message = messagesData.messages[i];
      messageList.push(
        <Fragment key={message.id}>
          <MessageSimple openModal={openModal} message={message} />
          <input
            type="checkbox"
            checked={checkedMessages.some((m) => m.id === message.id)}
            onChange={(e) => handleCheckboxChange(e, message)}
          />
          <hr />
        </Fragment>,
      );
    }
    return messageList;
  }

  // 메세지 검색
  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    const form = e.target as HTMLFormElement;
    const input = form.elements.namedItem('keyword') as HTMLInputElement;
    setKeyword(input.value);
  };

  // 체크박스의 상태가 변경될 때
  function handleCheckboxChange(
    e: React.ChangeEvent<HTMLInputElement>,
    message: Message,
  ) {
    if (e.target.checked) {
      // 새로 체크된다면 목록에 추가
      setCheckedMessages([...checkedMessages, message]);
    } else {
      // 체크가 해제된다면 목록에서 제거
      // 체크 해제된 message를 제외한 새 배열을 생성
      setCheckedMessages(checkedMessages.filter((m) => m.id !== message.id));
    }
  }

  // 모두 읽음 버튼 눌렀을 때
  async function handleReadClick() {
    const promises = checkedMessages.map((message) =>
      postReadMessage(message.id),
    );
    try {
      // 모든 메세지의 읽음 처리가 완료될 때까지 대기
      await Promise.all(promises);
      setMessagesData((prev) => {
        if (!prev) return null;
        // 새로운 메시지 목록(updatedMessages)생성
        const updatedMessages = prev.messages.map((message) =>
          checkedMessages.some(
            // 현재 가리키고 있는 메세지가 체크된 메세지인 경우
            (checkedMessage) => checkedMessage.id === message.id,
          ) // 1. 읽음 처리
            ? { ...message, isRead: true }
            : // 2. 그대로 유지
              message,
        );
        // 이전 메세지 목록 기존 정보 유지하고, 메세지 목록 새롭게 업데이트해서 반환
        return { ...prev, messages: updatedMessages };
      });
    } catch (error) {
      console.error('오류:', error);
    }
    // 체크함 초기화
    setCheckedMessages([]);
  }

  // 상세보기 할 메세지 선택(더미테스트코드)
  function openModal(message: Message) {
    setSelectedMessage(message);
  }

  // // 모달 클릭시 읽음확인 처리
  // async function openModal(message: Message) {
  //   const updatedMessage = await getReadMessage(message.id);
  //   if (updatedMessage) {
  //     setSelectedMessage(updatedMessage);
  //   }
  // }

  // 모달 닫히면 메세지 선택 해제
  function closeModal() {
    setSelectedMessage(null);
  }

  return (
    <div>
      <div>메세지</div>
      <button onClick={handleReadClick}>선택 읽음</button>
      <div>
        {messagesData.unreadMsgs}/{messagesData.sum}
      </div>

      <form onSubmit={handleSearch}>
        <input type="text" name="keyword" placeholder="검색어를 입력하세요." />
        <button type="submit">검색</button>
      </form>

      {renderMessages()}

      {selectedMessage && (
        <MessageDetailModal
          message={selectedMessage}
          onClose={closeModal}
          userId={user ? user.id : null}
        />
      )}

      <Pagination
        pageGroupSize={pageGroupSize}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalPage={messagesData.pageNum}
      />
    </div>
  );
}

export default FamilyMessage;
