import React, { useEffect, useState } from 'react';
import getMessage, {
  Message,
  MessagesResponse,
} from '../../services/message/getMessage';
import { ReactComponent as EnvelopeSimpleClosed } from '../../assets/icons/EnvelopeSimpleClosed.svg';
import { ReactComponent as EnvelopeSimpleOpen } from '../../assets/icons/EnvelopeSimpleOpen.svg';
import MessageDetailModal from '../../components/message/MessageDetailModal';
import SendMessageModal from '../../components/message/SendMessageModal';
import Pagination from 'react-js-pagination';

// 간병인 - 받은 메세지 전체 보기

function CareGiverMessage() {
  const [messagesData, setMessagesData] = useState<MessagesResponse | null>(
    null,
  );
  const [selectedMessage, setSelectedMessage] = useState<Message | null>(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [isSendMessageModalOpen, setSendMessageModalOpen] = useState(false);

  useEffect(() => {
    async function fetchMessages() {
      const res = await getMessage();
      setMessagesData(res);
    }
    fetchMessages();
  }, [currentPage, messagesData]);

  // 현재 페이지 갱신
  function handlePageChange(currentPage: React.SetStateAction<number>) {
    setCurrentPage(currentPage);
  }

  // 메세지 데이터 로딩 중
  if (!messagesData) {
    return <div>Now Loading...</div>;
  }

  // 상세보기 할 메세지 선택
  function openDetailModal(message: Message) {
    setSelectedMessage(message);
  }

  // 모달 닫히면 메세지 선택 해제
  function closeDetailModal() {
    setSelectedMessage(null);
  }

  // 성공적으로 메세지를 전송했을 경우 전송 모달 닫기
  async function handleSend() {
    setSendMessageModalOpen(false);
  }

  return (
    <div>
      <div>메세지</div>
      <div>
        {messagesData.unreadMsgs}/{messagesData.sum}
      </div>
      <button onClick={() => setSendMessageModalOpen(true)}>메세지 작성</button>
      {messagesData.messages.map((message) => (
        <>
          <div onClick={() => openDetailModal(message)}>
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
        <MessageDetailModal message={selectedMessage} onClose={closeDetailModal} />
      )}

      {isSendMessageModalOpen && <SendMessageModal onClose={handleSend} />}

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

export default CareGiverMessage;
