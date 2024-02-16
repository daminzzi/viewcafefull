import React from 'react';
import styled from 'styled-components';
import { failed, white, main3 } from '../../assets/styles/palettes';
import { Button } from '../common/Buttons';
import FlexColContainer from '../common/FlexColContainer';
import FlexRowContainer from '../common/FlexRowContainer';
import Line from '../common/Line';
import { ReactComponent as Xmark } from '../../assets/icons/xMark.svg';
import { useNavigate } from 'react-router';

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
  const navigator = useNavigate();
  function handleGoToReport() {
    if (isReportMessage(message.content)) {
      const month =
        message.content.month < 10
          ? '0' + message.content.month
          : message.content.month.toString();

      navigator(
        `/report/${message.content.targetId}/${message.content.year}${month}`,
      );
    }
  }
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

  // ReportMessage 여부를 확인하는 함수
  function isReportMessage(obj: string | ReportMessage): obj is ReportMessage {
    return typeof obj === 'object';
  }

  function renderContentText(): React.ReactNode {
    // console.log(isReportMessage(message.content));
    if (isReportMessage(message.content)) {
      return (
        <FlexColContainer $gap="3rem">
          {message.content.year}년 {message.content.month}월{' '}
          {message.content.targetName}님의 건강레포트가 도착했습니다!
          <Button type="button" onClick={handleGoToReport} $width="100%">
            레포트 바로가기
          </Button>
        </FlexColContainer>
      );
    }
    return message.content;
  }

  return (
    <ModalContainer onClick={onClose}>
      <Modal onClick={(e) => e.stopPropagation()}>
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
            <ContentText>{renderContentText()}</ContentText>
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
    </ModalContainer>
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
  border-radius: 30px;
  border: 4px solid ${main3};
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
  font-size: 1.5rem;
`;
const ContentText = styled.div`
  flex: 1;
  word-break: keep-all;
`;

const TimeText = styled.div`
  align-self: flex-end;
  margin-top: auto;
`;

const XmarkContainer = styled.div`
  cursor: pointer;
`;

const ModalContainer = styled.div`
  width: 100vw;
  height: 100vh;
  position: fixed;
  background-color: rgba(0, 0, 0, 0.2);
  top: 0;
  left: 0;
  z-index: 2;
`;
