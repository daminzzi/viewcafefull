import React, { useEffect, useRef, useState } from 'react';
import { OpenVidu, Publisher, Subscriber } from 'openvidu-browser';
import UserVideo from './UserVideo';
import useStreamStore from '../../stores/StreamStore';
import useUserStore from '../../stores/UserStore';
import useConnectStore from '../../stores/ConnectStore';
import { useNavigate, useParams } from 'react-router-dom';
import postSession from '../../services/visit/postSession';
import postToken from '../../services/visit/postToken';
import html2canvas from 'html2canvas';
import { styled } from 'styled-components';
import ToolBar from './ToolBar';
import { light } from '../../assets/styles/palettes';
import deleteSession from '../../services/visit/deleteSession';
import postBestShot from '../../services/visit/postBestShot';

function VisitRoom() {
  const { subscriberList, addSubscriber, delSubscriber, resetSubscriberList } =
    useStreamStore();
  const { user } = useUserStore();
  const OV = new OpenVidu();
  const session = OV.initSession();
  const myUserName = user?.name;
  const tarUserName = useConnectStore().currConnect.tarName;
  const navigator = useNavigate();
  const params = useParams<{ id: string }>();
  const sessionId = params.id ? params.id : '';
  const [publisher, setPublisher] = useState<Publisher | null>(null);
  const [isVideoEnabled, setIsVideoEnabled] = useState(true);
  const [isAudioEnabled, setIsAudioEnabled] = useState(true);
  const [videoHeight, setVideoHeight] = useState('70%');
  const [videoWidth, setVideoWidth] = useState('50%');
  const captureRef = useRef<HTMLDivElement>(null);

  const joinSession = async () => {
    // console.log('joinSession');
    // console.log(OV, session);
    if (OV !== null && session !== null) {
      // console.log('session not null');
      const mySession = session;

      mySession.on('streamCreated', (event) => {
        // console.log('new stream created detected');
        const subscriber = mySession.subscribe(event.stream, undefined);
        // console.log('SubList - ', subscriberList);
        const videoNum = addSubscriber(subscriber);
        calculateVideoSize(videoNum);
        // console.log('add new subscriber');
      });

      mySession.on('streamDestroyed', (event) => {
        if (event.stream.streamManager instanceof Subscriber) {
          const videoNum = delSubscriber(event.stream.streamManager);
          calculateVideoSize(videoNum);
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
    // console.log('leaving session');

    // Empty all properties...
    session.disconnect();
    resetSubscriberList();
    setPublisher(null);
    if (user?.role === 'Guardian') {
      navigator('/family/visit');
    } else if (user?.role === 'Caregiver') {
      deleteSession(sessionId);
      navigator('/caregiver/visit');
    }
  }

  function toggleVideo() {
    // console.log('toggleVideo');
    if (publisher) {
      // 비디오 상태 토글
      publisher.publishVideo(!isVideoEnabled);
      setIsVideoEnabled(!isVideoEnabled);
      // console.log(isVideoEnabled);
      // console.log(publisher.publishVideo);
    }
  }

  function toggleAudio() {
    // console.log('toggleAudio');
    if (publisher) {
      // 오디오 상태 토글
      publisher.publishAudio(!isAudioEnabled);
      setIsAudioEnabled(!isAudioEnabled);
      // console.log(isAudioEnabled);
    }
  }

  function calculateVideoSize(videoCount: number) {
    // console.log(videoCount);
    if (videoCount === 0) {
      setVideoHeight('70%');
      setVideoWidth('60%');
    } else if (videoCount >= 1 && videoCount <= 2) {
      setVideoHeight('47%');
      setVideoWidth('30%');
    } else if (videoCount === 3) {
      setVideoHeight('47%');
      setVideoWidth('40%');
    } else if (videoCount >= 4 && videoCount <= 5) {
      setVideoHeight('47%');
      setVideoWidth('25%');
    } else {
      setVideoHeight('47%');
      setVideoWidth('20%');
    }
  }

  function renderSubscriberList() {
    return subscriberList.map((sub) => (
      <VideoOne key={sub.id} $height={videoHeight} $width={videoWidth}>
        <UserVideo streamManager={sub} />
      </VideoOne>
    ));
  }

  async function capturePublisher() {
    if (captureRef.current) {
      try {
        const canvas = await html2canvas(captureRef.current);
        const imageDataURL = canvas.toDataURL('image/jpeg');
        // console.log(imageDataURL);
        const parts = imageDataURL.split(',');
        postBestShot(sessionId, parts[1]);
      } catch (error) {
        console.log(error);
      }
    }
  }

  useEffect(() => {
    const init = async () => {
      await postSession(sessionId);
      await joinSession();
      // console.log('useEffect');
    };
    init();

    return () => {
      // unmount
      // console.log('/컴포넌트가 화면에서 사라짐');
      leaveSession();
    };
  }, []);

  useEffect(() => {
    // user의 type이 'Guardian'인 경우에만 setInterval 설정
    if (user?.role === 'Caregiver') {
      const captureInterval = setInterval(capturePublisher, 30000);

      return () => {
        clearInterval(captureInterval);
      };
    }
  }, []); // user가 변경될 때마다 useEffect 실행

  useEffect(() => {
    calculateVideoSize(subscriberList.length);
  }, [subscriberList]);
  return (
    <div>
      <StyledHeader>{tarUserName}님의 면회실</StyledHeader>
      <VideoGroup>
        <VideoOne ref={captureRef} $height={videoHeight} $width={videoWidth}>
          {publisher !== null ? <UserVideo streamManager={publisher} /> : null}
        </VideoOne>
        {renderSubscriberList()}
      </VideoGroup>
      <ToolBar
        isVideoEnabled={isVideoEnabled}
        isAudioEnabled={isAudioEnabled}
        handleVideo={toggleVideo}
        handleAudio={toggleAudio}
        handleLeave={leaveSession}
      />
    </div>
  );
}

export default VisitRoom;

const VideoGroup = styled.div`
  height: 80vh;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  align-items: center;
  gap: 2rem;
`;

const VideoOne = styled.div<{ $height?: string; $width?: string }>`
  display: inline-block;
  height: ${(props) => props.$height || '70%'};
  width: ${(props) => props.$width || '70%'};
`;

const StyledHeader = styled.div`
  display: flex;
  align-items: center;
  padding: 0px 10px;
  height: 7vh;
  background-color: ${light};
  font-size: 1.2rem;
`;
