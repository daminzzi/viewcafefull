import React, { useEffect, useRef, useState } from 'react';
import { OpenVidu, Publisher, Subscriber } from 'openvidu-browser';
import UserVideo from './UserVideo';
import useStreamStore from '../../stores/StreamStore';
import useUserStore from '../../stores/UserStore';
import { useNavigate, useParams } from 'react-router-dom';
import postSession from '../../services/visit/postSession';
import postToken from '../../services/visit/postToken';
import html2canvas from 'html2canvas';
import { styled } from 'styled-components';

function VisitRoom() {
  const { subscriberList, addSubscriber, delSubscriber, resetSubscriberList } =
    useStreamStore();
  const OV = new OpenVidu();
  const session = OV.initSession();
  const myUserName = useUserStore().user?.name;
  const navigator = useNavigate();
  const params = useParams<{ id: string }>();
  const sessionId = params.id ? params.id : '';
  console.log(sessionId, myUserName);

  const [publisher, setPublisher] = useState<Publisher | null>(null);
  const [isVideoEnabled, setIsVideoEnabled] = useState(true);
  const [isAudioEnabled, setIsAudioEnabled] = useState(true);

  const captureRef = useRef<HTMLDivElement>(null);

  const joinSession = async () => {
    console.log('joinSession');
    console.log(OV, session);
    if (OV !== null && session !== null) {
      console.log('session not null');
      const mySession = session;

      mySession.on('streamCreated', (event) => {
        // console.log('new stream created detected');
        const subscriber = mySession.subscribe(event.stream, undefined);
        // console.log('SubList - ', subscriberList);
        addSubscriber(subscriber);
        // console.log('add new subscriber');
      });

      mySession.on('streamDestroyed', (event) => {
        if (event.stream.streamManager instanceof Subscriber) {
          delSubscriber(event.stream.streamManager);
        }
      });

      mySession.on('exception', (exception) => {
        console.warn(exception);
      });

      const token = await postToken(sessionId);
      mySession.connect(token, { clientData: myUserName }).then(async () => {
        const publisher = await OV.initPublisherAsync(undefined, {
          audioSource: undefined,
          videoSource: undefined,
          publishAudio: true,
          publishVideo: true,
          resolution: '640x480',
          frameRate: 30,
          insertMode: 'APPEND',
          mirror: false,
        });

        mySession?.publish(publisher);

        setPublisher(publisher);
      });
    }
  };

  function leaveSession() {
    console.log('leaving session');

    // Empty all properties...
    session.disconnect();
    resetSubscriberList();
    setPublisher(null);
    navigator('/family/visit');
  }

  function toggleVideo() {
    console.log('toggleVideo');
    if (publisher) {
      // 비디오 상태 토글
      publisher.publishVideo(!isVideoEnabled);
      setIsVideoEnabled(!isVideoEnabled);
      console.log(isVideoEnabled);
      console.log(publisher.publishVideo);
    }
  }

  function toggleAudio() {
    console.log('toggleAudio');
    if (publisher) {
      // 오디오 상태 토글
      publisher.publishAudio(!isAudioEnabled);
      setIsAudioEnabled(!isAudioEnabled);
      console.log(isAudioEnabled);
    }
  }

  function renderSubscriberList() {
    return subscriberList.map((sub) => (
      <VideoOne key={sub.id}>
        <UserVideo streamManager={sub} />
      </VideoOne>
    ));
  }

  async function capturePublisher() {
    if (captureRef.current) {
      try {
        const canvas = await html2canvas(captureRef.current);
        const imageDataURL = canvas.toDataURL('image/jpeg');
        console.log(imageDataURL);
      } catch (error) {
        console.log(error);
      }
    }
  }

  useEffect(() => {
    const init = async () => {
      await postSession(sessionId);
      await joinSession();
      console.log('useEffect');
    };
    init();

    return () => {
      // unmount
      console.log('컴포넌트가 화면에서 사라짐');
      leaveSession();
    };
  }, []);

  return (
    <div>
      <header>면회실 헤더</header>
      <button type="button" onClick={leaveSession}>
        나가기
      </button>
      <VideoGroup>
        <VideoOne ref={captureRef}>
          {publisher !== null ? <UserVideo streamManager={publisher} /> : null}
        </VideoOne>
        {renderSubscriberList()}
      </VideoGroup>
      <div>
        <button type="button" onClick={toggleVideo}>
          카메라
        </button>
        <button type="button" onClick={toggleAudio}>
          마이크
        </button>
        <button type="button" onClick={capturePublisher}>
          캡쳐
        </button>
      </div>
    </div>
  );
  8;
}

export default VisitRoom;

const VideoGroup = styled.div`
  height: 85vh;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  align-items: center;
`;

const VideoOne = styled.div`
  display: inline-block;
  height: 50%;
`;
