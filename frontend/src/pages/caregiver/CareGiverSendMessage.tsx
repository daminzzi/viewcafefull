import React, { useState } from 'react';
import postSendMessage from '../../services/message/postSendMessage';
import useUserStore from '../../stores/userStore';
import { useNavigate } from 'react-router-dom';

function CareGiverSendMessage() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const navigate = useNavigate();
  const { user } = useUserStore();

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();

    if (!user) {
      alert('유저 정보를 불러오지 못했습니다.');
      return;
    }
    try {
      const message = {
        to: user.id,
        title,
        content,
      };
      await postSendMessage(message);
      alert('메시지 전송 완료.');
      navigate('/caregiver/message');
    } catch (error) {
      console.error(error);
      alert('메시지 전송에 실패하였습니다.');
    }
  }

  return (
    <div>
      <h2>메시지 작성</h2>
      <form onSubmit={handleSubmit}>
        <p>
          <label>
            제목:
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
            />
          </label>
        </p>
        <p>
          <label>
            내용:
            <textarea
              value={content}
              onChange={(e) => setContent(e.target.value)}
              required
            />
          </label>
        </p>
        <button type="submit">전송</button>
      </form>
    </div>
  );
}

export default CareGiverSendMessage;
