import React, { useState } from 'react';
import postSendMessage from '../../services/message/postSendMessage';
import useUserStore from '../../stores/UserStore';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import Input from '../../components/common/Input';
import { Button } from '../../components/common/Buttons';
import { failed, main3, success, white } from '../../assets/styles/palettes';
import TextArea from '../../components/common/TextArea';
import FlexRowContainer from '../../components/common/FlexRowContainer';
import Title from '../../components/common/Title';

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

  function handleClose() {
    navigate('/caregiver/message');
  }

  return (
    <ParentContainer>
      <MainContainer>
        <Title icon="message">메세지 작성</Title>
        <SubContainer onSubmit={handleSubmit}>
          <SendButton type="submit">전송</SendButton>
          <Label>
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
          </Label>
          <Label>
            내용
            <div>
              <TextArea
                $width="97%"
                $height="40vh"
                value={content}
                onChange={(e) => setContent(e.target.value)}
                required
              />
            </div>
          </Label>
        </SubContainer>
        <FlexRowContainer $justifyContent="end" $alignItems="end">
          <Button
            $borderRadius="50px"
            $bgColor={failed}
            $width="60px"
            $padding="10px"
            $margin="5px"
            $color={white}
            onClick={handleClose}
          >
            닫기
          </Button>
        </FlexRowContainer>
      </MainContainer>
    </ParentContainer>
  );
}

export default CareGiverSendMessage;

const SendButton = styled(Button)`
  border-radius: 50px;
  width: 60px;
  padding: 10px;
  color: ${white};
  background-color: ${success};
  position: absolute;
  top: -11%;
  right: 2%;
`;

const Label = styled.label`
  margin-bottom: 5px;
  font-size: 13px;
  font-weight: bold;
`;

const ParentContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80vh;
`;

const MainContainer = styled.div`
  padding: 10px;
  width: 280px;
  height: 70vh;
  border-radius: 30px;
  background-color: ${white};
  border: 2px solid ${main3};
`;

const SubContainer = styled.div`
  position: relative;
  padding: 10px;
  align-items: center;
  width: auto;
`;
