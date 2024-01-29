import React from 'react';
import { Message } from '../../services/message/getMessage';
import deleteMessage from '../../services/message/deleteMessage';

type MessageDetailModalProps = {
  message: Message;
  userId: string | null;
  onClose: () => void;
};

function MessageDetailModal({
  message,
  userId,
  onClose,
}: MessageDetailModalProps) {
  async function handleDelete() {
    try {
      await deleteMessage(message.id);
      onClose();
    } catch (error) {
      console.error('메세지를 삭제하지 못했습니다');
    }
  }

  return (
    <div
      style={{
        position: 'fixed',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        backgroundColor: 'white',
        padding: '50px',
        zIndex: 1000,
      }}
    >
      <button onClick={onClose}>X</button>
      <h2>제목:{message.title}</h2>
      <p>내용: {message.content}</p>
      {message.from === userId ? (
        <button onClick={handleDelete}>삭제</button>
      ) : null}
    </div>
  );
}

export default MessageDetailModal;
