import React, { useEffect, useState } from 'react';
import getMessage, {
  Message,
  MessagesResponse,
} from '../../services/message/getMessage';
import postReadMessage from '../../services/message/postReadMessage';
import { ReactComponent as EnvelopeSimpleClosed } from '../../assets/icons/EnvelopeSimpleClosed.svg';
import { ReactComponent as EnvelopeSimpleOpen } from '../../assets/icons/EnvelopeSimpleOpen.svg';
import MessageModal from '../../components/message/MessageModal';
import Pagination from 'react-js-pagination';
// import getReadMessage from '../../services/message/getReadMessage';

// 보호자 - 받은 메세지 전체 보기

function FamilyMessage() {
  const [messagesData, setMessagesData] = useState<MessagesResponse | null>(
    null,
  );
  const [selectedMessage, setSelectedMessage] = useState<Message | null>(null);
  const [checkedMessages, setCheckedMessages] = useState<Message[]>([]);
  const [currentPage, setCurrentPage] = useState(1);

  function handlePageChange(currentPage: React.SetStateAction<number>) {
    setCurrentPage(currentPage);
  };

  useEffect(() => {
    async function fetchMessages() {
      const res = await getMessage();
      setMessagesData(res);
    }
    fetchMessages();
  }, [currentPage, messagesData]);

  if (!messagesData) {
    return <div>Now Loading...</div>;
  }

  // 체크박스의 상태가 변경될 때 호출
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

  // 모달 테스트용 코드
  function openModal(message: Message) {
    setSelectedMessage(message);
  }

  // // 모달 클릭시 읽음확인 처리되는 코드
  // async function openModal(message: Message) {
  //   const updatedMessage = await getReadMessage(message.id);
  //   if (updatedMessage) {
  //     setSelectedMessage(updatedMessage);
  //   }
  // }

  function closeModal() {
    setSelectedMessage(null);
  }

  return (
    <div>
      <div>메세지</div>
      <button onClick={handleReadClick}>모두 읽음</button>
      <div>
        {messagesData.unreadMsgs}/{messagesData.sum}
      </div>
      {messagesData.messages.map((message) => (
        <>
          <input
            type="checkbox"
            checked={checkedMessages.some((m) => m.id === message.id)}
            onChange={(e) => handleCheckboxChange(e, message)}
          />
          <div onClick={() => openModal(message)}>
            <div key={message.id}>
              <div>{message.title}</div>
              <div>{message.from}</div>
              <div>{message.time}</div>

              {message.isRead ? (
                <EnvelopeSimpleOpen className="EnvelopeSimple-open" />
              ) : (
                <EnvelopeSimpleClosed className="EnvelopeSimple-closed" />
              )}
            </div>
          </div>
          <hr />
        </>
      ))}

      {selectedMessage && (
        <MessageModal message={selectedMessage} onClose={closeModal} />
      )}

      <Pagination
        activePage={currentPage}
        itemsCountPerPage={10}
        totalItemsCount={500}
        pageRangeDisplayed={5}
        prevPageText={'‹'}
        nextPageText={'›'}
        onChange={handlePageChange}
      />
    </div>
  );
}

export default FamilyMessage;
