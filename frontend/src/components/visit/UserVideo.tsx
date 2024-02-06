import React from 'react';
import styled from 'styled-components';
import OpenViduVideo from './OpenViduVideo';
import { Publisher, Subscriber } from 'openvidu-browser';
type Props = {
  streamManager: Publisher | Subscriber;
};

const VideoWrapper = styled.div`
  display: inline-block;
  position: relative;
  height: 100%;
`;

const NameTag = styled.span`
  position: absolute;
  margin-left: -100px;
  color: white;
  background-color: gray;
  bottom: 5px;
`;

function UserVideo({ streamManager }: Props) {
  function getNicknameTag() {
    // Gets the nickName of the user
    return JSON.parse(streamManager.stream.connection.data).clientData;
  }

  return (
    <VideoWrapper>
      <OpenViduVideo streamManager={streamManager} />
      <NameTag>{getNicknameTag()}</NameTag>
    </VideoWrapper>
  );
}

export default UserVideo;
