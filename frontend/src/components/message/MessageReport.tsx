import React from 'react';
import { ReactComponent as EnvelopeClosed } from '../../assets/icons/envelopeClosed.svg';
import { ReactComponent as EnvelopeOpen } from '../../assets/icons/envelopeOpen.svg';
import styled from 'styled-components';
import FlexRowContainer from '../common/FlexRowContainer';
import { gray, black } from '../../assets/styles/palettes';
import { HiddenCheckBox, ShowCheckBox } from '../common/CheckBox';

type MessageProps = {
  openModal: (message: Message) => void;
  message: Message;
  $isChecked: boolean;
  handleCheckboxChange: (
    e: React.ChangeEvent<HTMLInputElement>,
    message: Message,
  ) => void;
};

function MessageReport({
  openModal,
  message,
  $isChecked,
  handleCheckboxChange,
}: MessageProps) {
  const dateTime = new Date(message.time);
  const year = dateTime.getFullYear();

  const month = ('0' + (dateTime.getMonth() + 1)).slice(-2);
  const date = ('0' + dateTime.getDate()).slice(-2);
  const hours = ('0' + dateTime.getHours()).slice(-2);
  const minutes = ('0' + dateTime.getMinutes()).slice(-2);

  const time = `${year}/${month}/${date} ${hours}:${minutes}`;

  // ReportMessage 여부를 확인하는 함수
  function isReportMessage(obj: string | ReportMessage): obj is ReportMessage {
    return (
      typeof obj === 'object' &&
      'year' in obj &&
      'month' in obj &&
      'targetId' in obj &&
      'targetName' in obj
    );
  }

  function renderContentText(): React.ReactNode {
    if (isReportMessage(message.content)) {
      return (
        <div>
          {message.content?.year}년 {message.content?.month}월{' '}
          {message.content?.targetName}님의 건강레포트가 도착했습니다!
        </div>
      );
    }
    return message.content;
  }

  return (
    <MainContainer>
      <div key={message.id}>
        <HiddenCheckBox
          id={`message-checkbox-${message.id}`}
          checked={$isChecked}
          onChange={(e) => handleCheckboxChange(e, message)}
        />
        <ShowCheckBox
          htmlFor={`message-checkbox-${message.id}`}
          $isChecked={$isChecked}
        />

        <div onClick={() => openModal(message)}>
          <TitleContainer>
            <TitleText $isRead={message.isRead}>{message.title}</TitleText>
            <ContentText $isRead={message.isRead}>
              {renderContentText()}
            </ContentText>
          </TitleContainer>
        </div>
        <FlexRowContainer
          $width="135px"
          $alignItems="center"
          $justifyContent="flex-start"
          $position="absolute"
          $right="6px"
          $top="0"
        >
          {message.isRead ? (
            <EnvelopeOpen
              width="20px"
              height="23px"
              style={{ marginRight: '2px' }}
            />
          ) : (
            <EnvelopeClosed
              width="20px"
              height="23px"
              style={{ marginRight: '2px' }}
            />
          )}
          <TimeText $isRead={message.isRead}>{time}</TimeText>
        </FlexRowContainer>
      </div>
    </MainContainer>
  );
}

export default MessageReport;

const TitleText = styled.div<{ $isRead?: boolean }>`
  font-weight: bold;
  padding-bottom: 13px;
  color: ${(props) => (props.$isRead ? gray : black)};
  width: 35vw;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

const ContentText = styled(TitleText)`
  font-weight: normal;
  width: 45vw;
`;

const TimeText = styled.div<{ $isRead?: boolean }>`
  font-size: 13px;
  padding-bottom: 2px;
  margin-left: 2px;
  color: ${(props) => (props.$isRead ? gray : black)};
`;

const MainContainer = styled.div`
  margin: 10px 0 0 20px;
`;

const TitleContainer = styled.div`
  margin: 0 0 0 20px;
`;
