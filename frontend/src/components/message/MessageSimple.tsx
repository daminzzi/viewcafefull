import React from 'react';
import { ReactComponent as EnvelopeSimpleClosed } from '../../assets/icons/EnvelopeSimpleClosed.svg';
import { ReactComponent as EnvelopeSimpleOpen } from '../../assets/icons/EnvelopeSimpleOpen.svg';
import { Message } from '../../services/message/getMessage';

interface MessageProps {
  openModal: (message: Message) => void;
  message: Message;
}

const MessageSimple = ({ openModal, message }: MessageProps) => {
  return (
    <div onClick={() => openModal(message)}>
      <div key={message.id}>
        <div>
          {message.title.length > 20
            ? `${message.title.substring(0, 20)}...`
            : message.title}
        </div>
        <div>{message.from}</div>
        <div>{message.time}</div>

        {message.isRead ? (
          <EnvelopeSimpleOpen className="EnvelopeSimple-open" />
        ) : (
          <EnvelopeSimpleClosed className="EnvelopeSimple-closed" />
        )}
      </div>
    </div>
  );
};

export default MessageSimple;
