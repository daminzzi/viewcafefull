import React, { useState } from 'react';
import postSendMessage from '../../../services/message/postSendMessage';
import useUserStore from '../../../stores/UserStore';
import { useNavigate } from 'react-router-dom';
import Input from '../../../components/common/Input';
import * as S from './CareGiverSendMessage.styles';
import TextArea from '../../../components/common/TextArea';
import Title from '../../../components/common/Title';
import getConnectInfo from '../../../services/connect/getConnectInfo';

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

    const userLinkList = await getConnectInfo('tar', user.id);

    try {
      const messagePromises = [];
      if (userLinkList && userLinkList.length > 0) {
        for (let i = 0; i < userLinkList.length; i++) {
          const userLink = userLinkList[i];
          const message = {
            to: userLink.appDomainId,
            title,
            content,
          };
          messagePromises.push(postSendMessage(message));
        }
      }

      await Promise.all(messagePromises);
      alert('메시지가 전송되었습니다.');
      setTitle('');
      setContent('');
      navigate('/caregiver/message/send');
    } catch (error) {
      console.error(error);
      alert('메시지 전송에 실패하였습니다.');
    }
  }

  return (
    <S.ParentContainer>
      <S.MainContainer>
        <Title icon="message">메세지 작성</Title>
        <S.SubContainer>
          <S.SendButton type="submit" onClick={handleSubmit}>
            전송
          </S.SendButton>
          <S.Label>
            제목
            <div>
              <Input
                $width="97%"
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
              />
            </div>
          </S.Label>
          <S.Label>
            내용
            <div>
              <TextArea
                $width="97%"
                $height="45vh"
                value={content}
                onChange={(e) => setContent(e.target.value)}
                required
              />
            </div>
          </S.Label>
        </S.SubContainer>
      </S.MainContainer>
    </S.ParentContainer>
  );
}

export default CareGiverSendMessage;
