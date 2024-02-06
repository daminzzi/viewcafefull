import React from 'react';
import { Message } from '../../services/message/getMessage';
import deleteMessage from '../../services/message/deleteMessage';
import styled from 'styled-components';
import { failed, white, main3 } from '../../assets/styles/palettes';
import { Button } from '../common/Buttons';
import FlexColContainer from '../common/FlexColContainer';
import FlexRowContainer from '../common/FlexRowContainer';

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
    <Modal>
      <FlexRowContainer $justifyContent="end">
        <Button
          $bgColor={failed}
          $width="25px"
          $padding="3px"
          $margin="0 0 5px 0"
          $color={white}
          onClick={onClose}
        >
          X
        </Button>
      </FlexRowContainer>

      <FlexColContainer
        $justifyContent="start"
        $gap="8px"
        $alignItems="stretch"
        $position="relative"
      >
        <FlexRowContainer $alignItems="stretch" $justifyContent="stretch">
          <Title>제목:</Title>
          <Content>{message.title}</Content>
        </FlexRowContainer>

        <FlexRowContainer $alignItems="stretch" $justifyContent="stretch">
          <Title>내용:</Title>
          <Content>{message.content}</Content>
        </FlexRowContainer>
      </FlexColContainer>

      {message.from === userId ? (
        <FlexRowContainer $justifyContent="end" $alignItems="end">
          <Button
            $bgColor={failed}
            $color={white}
            $padding="8px"
            $width="4rem"
            onClick={handleDelete}
          >
            삭제
          </Button>
        </FlexRowContainer>
      ) : null}
    </Modal>
  );
}

export default MessageDetailModal;

const Modal = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: ${white};
  padding: 20px;
  z-index: 10;
  border-radius: 30px;
  border: 2px solid ${main3};
  width: 30%;
  height: 33%;
  overflow: auto;
  @media (max-width: 600px) {
    width: 70%;
  }
`;

const Title = styled.div`
  font-weight: bold;
  margin-right: 10px;
`;

const Content = styled.div`
  flex: 1;
`;
