import React, { useRef, useEffect } from 'react';
import { Publisher, Subscriber } from 'openvidu-browser';

type Props = {
  streamManager: Publisher | Subscriber;
};

function OpenViduVideo({ streamManager }: Props) {
  const videoRef = useRef<HTMLVideoElement | null>(null);

  useEffect(() => {
    if (streamManager && !!videoRef.current) {
      streamManager.addVideoElement(videoRef.current);
    }
  }, [streamManager]);

  return <video autoPlay={true} ref={videoRef} />;
}

export default OpenViduVideo;
