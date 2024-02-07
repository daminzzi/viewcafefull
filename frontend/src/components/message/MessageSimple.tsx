import React, { useState, useEffect } from 'react';
import { ReactComponent as EnvelopeClosed } from '../../assets/icons/envelopeClosed.svg';
import { ReactComponent as EnvelopeOpen } from '../../assets/icons/envelopeOpen.svg';
import styled from 'styled-components';
import FlexRowContainer from '../common/FlexRowContainer';
import { gray, black } from '../../assets/styles/palettes';

type MessageProps = {
  openModal: (message: Message) => void;
  message: Message;
};

function MessageSimple({ openModal, message }: MessageProps) {
  const [contentMaxLength, setContentMaxLength] = useState(
    window.innerWidth > 1200 ? 80 : 20,
  );

  const dateTime = new Date(message.time);
  const year = dateTime.getFullYear();

  const month = ('0' + (dateTime.getMonth() + 1)).slice(-2);
  const date = ('0' + dateTime.getDate()).slice(-2);
  const hours = ('0' + dateTime.getHours()).slice(-2);
  const minutes = ('0' + dateTime.getMinutes()).slice(-2);

  const time = `${year}/${month}/${date} ${hours}:${minutes}`;

  useEffect(() => {
    function handleResize() {
      setContentMaxLength(window.innerWidth > 1200 ? 80 : 20);
    }

    window.addEventListener('resize', handleResize);

    return () => window.removeEventListener('resize', handleResize);
  }, []);
  return (
    <MainContainer>
      <div onClick={() => openModal(message)}>
        <div key={message.id}>
          <TitleText isRead={message.isRead}>
            {message.title.length > 10
              ? `${message.title.substring(0, 10)}...`
              : message.title}
          </TitleText>
          <ContentText isRead={message.isRead}>
            {message.content.length > contentMaxLength
              ? `${message.content.substring(0, contentMaxLength)}...`
              : message.content}
          </ContentText>

          <FlexRowContainer
            $justifyContent="end"
            $gap="5px"
            $position="absolute"
            $padding="0 7px 0 0"
            $right="-0px"
            $top="-2px"
          >
            {message.isRead ? (
              <EnvelopeOpen width="20px" height="23px" />
            ) : (
              <EnvelopeClosed width="20px" height="23px" />
            )}
            <TimeText isRead={message.isRead}>{time}</TimeText>
          </FlexRowContainer>
        </div>
      </div>
    </MainContainer>
  );
}

export default MessageSimple;

const TitleText = styled.div<{ isRead?: boolean }>`
  font-weight: bold;
  padding-bottom: 13px;
  color: ${(props) => (props.isRead ? gray : black)};
`;

const ContentText = styled(TitleText)`
  font-weight: normal;
`;

const TimeText = styled.div<{ isRead?: boolean }>`
  font-size: 13px;
  padding-bottom: 2px;
  color: ${(props) => (props.isRead ? gray : black)};
`;

const MainContainer = styled.div`
  margin: 10px 0 0 20px;
`;
