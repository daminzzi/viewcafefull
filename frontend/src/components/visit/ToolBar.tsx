import React from 'react';
import { ReactComponent as VideoOnIcon } from '../../assets/icons/video.svg';
import { ReactComponent as VideoOffIcon } from '../../assets/icons/videoOff.svg';
import { ReactComponent as AudioOnIcon } from '../../assets/icons/mic.svg';
import { ReactComponent as AudioOffIcon } from '../../assets/icons/micOff.svg';
import { ReactComponent as LeaveIcon } from '../../assets/icons/exit.svg';
import { light } from '../../assets/styles/palettes';
import styled from 'styled-components';

type Props = {
  isVideoEnabled: boolean;
  isAudioEnabled: boolean;
  handleVideo: () => void;
  handleAudio: () => void;
  handleLeave: () => void;
};

const StyledToolBar = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 13vh;
  background-color: ${light};
`;

const ButtonGroup = styled.div`
  display: inline-flex;
`;

const Button = styled.button`
  border: none;
  background-color: rgba(255, 255, 255, 0);
  padding-top: 0.5rem;
  padding-bottom: 0.5rem;
  margin-right: 1rem;
  margin-left: 1rem;
`;

const Paragraph = styled.p`
  margin: 0rem;
`;
const ToolBar = ({
  isVideoEnabled,
  isAudioEnabled,
  handleVideo,
  handleAudio,
  handleLeave,
}: Props) => {
  return (
    <StyledToolBar>
      <span>&nbsp;</span>
      <ButtonGroup>
        <Button type="button" onClick={handleVideo}>
          {isVideoEnabled ? <VideoOnIcon /> : <VideoOffIcon />}
          <Paragraph>카메라</Paragraph>
        </Button>
        <Button type="button" onClick={handleAudio}>
          {isAudioEnabled ? <AudioOnIcon /> : <AudioOffIcon />}
          <Paragraph>마이크</Paragraph>
        </Button>
      </ButtonGroup>
      <ButtonGroup>
        <Button type="button" onClick={handleLeave}>
          <LeaveIcon />
          <Paragraph>나가기</Paragraph>
        </Button>
      </ButtonGroup>
    </StyledToolBar>
  );
};

export default ToolBar;
