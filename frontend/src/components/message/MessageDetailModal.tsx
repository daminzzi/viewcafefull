import React from 'react';
import styled from 'styled-components';
import { failed, white, main3 } from '../../assets/styles/palettes';
import { Button } from '../common/Buttons';
import FlexColContainer from '../common/FlexColContainer';
import FlexRowContainer from '../common/FlexRowContainer';
import Line from '../common/Line';
import { ReactComponent as Xmark } from '../../assets/icons/xMark.svg';

type MessageDetailModalProps = {
  message: Message;
  userId: string | null;
  onClose: () => void;
  time: string;
};

function MessageDetailModal({
  message,
  userId,
  onClose,
  time,
}: MessageDetailModalProps) {
  const dateObj = new Date(time);

  const formattedDate =
    dateObj
      .toLocaleDateString('ko-KR')
      .replaceAll('. ', '/')
      .replaceAll('.', '') +
    '/' +
    dateObj.toLocaleTimeString('ko-KR', { hour: '2-digit', minute: '2-digit' });

  async function handleDelete() {
    // try {
    //   await deleteMessage(message.id);
    //   onClose();
    // } catch (error) {
    //   console.error('메세지를 삭제하지 못했습니다');
    // }
    alert('추후 지원 예정 기능입니다');
  }

  return (
    <Modal>
      <FlexRowContainer $justifyContent="end">
        <XmarkContainer>
          <Xmark width="30px" onClick={onClose}></Xmark>
        </XmarkContainer>
      </FlexRowContainer>

      <FlexColContainer
        $justifyContent="start"
        $gap="8px"
        $alignItems="stretch"
        $position="relative"
      >
        <FlexColContainer $alignItems="stretch" $justifyContent="stretch">
          <TitleText>{message.title}</TitleText>
        </FlexColContainer>

        <FlexColContainer $alignItems="stretch" $justifyContent="stretch">
          <Title>내용</Title>
          <Line />
          <ContentText>{message.content}</ContentText>
        </FlexColContainer>
      </FlexColContainer>
      <TimeText>{formattedDate}</TimeText>
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
  display: flex;
  flex-direction: column;
  justify-content: space-between;
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
  height: 65%;
  overflow: auto;
  @media (max-width: 600px) {
    width: 70%;
  }
`;

const Title = styled.div`
  font-weight: bold;
  margin: 5px 0 5px 0;
`;

const TitleText = styled.div`
  font-weight: bold;
  font-size: 27px;
`;
const ContentText = styled.div`
  flex: 1;
`;

const TimeText = styled.div`
  align-self: flex-end;
  margin-top: auto;
`;

const XmarkContainer = styled.div`
  cursor: pointer;
`;
