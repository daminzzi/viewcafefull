import React, { useEffect, useState } from 'react';
import { OpenVidu, Publisher, Session, Subscriber } from 'openvidu-browser';
import UserVideo from './UserVideo';
import useStreamStore from '../../stores/StreamStore';
import useUserStore from '../../stores/UserStore';
import { useNavigate, useParams } from 'react-router-dom';
import postSession from '../../services/visit/postSession';
import postToken from '../../services/visit/postToken';

function VisitRoom() {
  let OV: OpenVidu;
  let session: Session;
  let sessionId = '';
  const myUserName = useUserStore().user?.name;
  const { subscriberList, addSubscriber, delSubscriber, resetSubscriberList } =
    useStreamStore();
  const navigator = useNavigate();
  const params = useParams<{ id: string }>();
  sessionId = params.id ? params.id : '';
  console.log(sessionId, myUserName);

  const [publisher, setPublisher] = useState<Publisher | null>(null);
  const [isVideoEnabled, setIsVideoEnabled] = useState(true);
  const [isAudioEnabled, setIsAudioEnabled] = useState(true);

  const joinSession = async () => {
    console.log('joinSession');
    if (OV !== null) {
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
    const mySession = session;

    if (mySession) {
      mySession.disconnect();
    }

    // Empty all properties...
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
      <div key={sub.id}>
        <span>{sub.id}</span>
        <UserVideo streamManager={sub} />
      </div>
    ));
  }

  useEffect(() => {
    const init = async () => {
      OV = new OpenVidu();
      session = OV.initSession();
      await postSession(sessionId);
      await joinSession();
      console.log('useEffect');
    };
    init();
  }, []);

  return (
    <div>
      <header>면회실 헤더</header>
      <button type="button" onClick={leaveSession}>
        나가기
      </button>
      <div>
        {publisher !== null ? <UserVideo streamManager={publisher} /> : null}
        <div>위에는 나 밑에는 다른 사람들 {subscriberList.length}</div>
        {renderSubscriberList()}
      </div>
      <div>
        <button type="button" onClick={toggleVideo}>
          카메라
        </button>
        <button type="button" onClick={toggleAudio}>
          마이크
        </button>
      </div>
    </div>
  );
}

export default VisitRoom;
