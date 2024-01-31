import React from 'react';
import OpenViduVideo from './OpenViduVideo';
import { Publisher, Subscriber } from 'openvidu-browser';

type Props = {
  streamManager: Publisher | Subscriber;
};

function UserVideo({ streamManager }: Props) {
  function getNicknameTag() {
    // Gets the nickName of the user
    return JSON.parse(streamManager.stream.connection.data).clientData;
  }

  return (
    <div>
      <OpenViduVideo streamManager={streamManager} />
      <div>
        <p>{getNicknameTag()}</p>
      </div>
    </div>
  );
}

export default UserVideo;
