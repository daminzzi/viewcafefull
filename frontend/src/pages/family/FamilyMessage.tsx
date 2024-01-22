import React, { useEffect, useState } from 'react';
import getMessage, {
  Message,
  MessagesResponse,
} from '../../services/message/getMessage';
import { ReactComponent as EnvelopeSimpleClosed } from '../../assets/icons/EnvelopeSimpleClosed.svg';
import { ReactComponent as EnvelopeSimpleOpen } from '../../assets/icons/EnvelopeSimpleOpen.svg';
import MessageModal from '../../components/message/MessageModal';
// import getReadMessage from '../../services/message/getReadMessage';

// 보호자 - 받은 메세지 전체 보기

function FamilyMessage() {
  const [messagesData, setMessagesData] = useState<MessagesResponse | null>(
    null,
  );
  const [selectedMessage, setSelectedMessage] = useState<Message | null>(null);

  useEffect(() => {
    async function fetchMessages() {
      const res = await getMessage();
      setMessagesData(res);
    }
    fetchMessages();
  }, []);

  if (!messagesData) {
    return <div>Now Loading...</div>;
  }

  // 모달 테스트용 코드
  function openModal(message: Message) {
    setSelectedMessage(message);
  }

  // 모달 클릭시 읽음확인 처리되는 코드
  // const openModal = async (message: Message) => {
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
      <div>
        {messagesData.unreadMsgs}/{messagesData.sum}
      </div>
      {messagesData.messages.map((message) => (
        <>
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
    </div>
  );
}

export default FamilyMessage;
