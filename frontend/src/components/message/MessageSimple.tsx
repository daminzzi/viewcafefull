import React, { useState, useEffect } from 'react';
import { ReactComponent as EnvelopeSimpleClosed } from '../../assets/icons/envelopeSimpleClosed.svg';
import { ReactComponent as EnvelopeSimpleOpen } from '../../assets/icons/envelopeSimpleOpen.svg';
import { Message } from '../../services/message/getMessage';
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
            {message.title.length > 15
              ? `${message.title.substring(0, 15)}...`
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
              <EnvelopeSimpleOpen width="20px" height="23px" />
            ) : (
              <EnvelopeSimpleClosed width="20px" height="23px" />
            )}
            <TimeText isRead={message.isRead}>
              {message.time.split(' ')[0]}
            </TimeText>
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
