import React, { useEffect, useState } from 'react';
import {
  Device,
  OpenVidu,
  Session as OVSession,
  Publisher,
  Subscriber,
} from 'openvidu-browser';
import axios from 'axios';
import UserVideo from './UserVideo';

const APPLICATION_SERVER_URL = process.env.REACT_APP_SPRING_URL;
const OV = new OpenVidu();

function VisitRoom() {
  const sessionId = 'sessionA';
  const myUserName = 'Participant' + Math.floor(Math.random() * 100);
  console.log(sessionId, myUserName);
  const [session, setSession] = useState<OVSession | null>(null);
  const [mainStreamManager, setMainStreamManager] = useState<
    Publisher | Subscriber | null
  >(null);
  const [currentVideoDevice, setCurrentVideoDevice] = useState<Device | null>(
    null,
  );
  const [subscriberList, setSubscriberList] = useState<Subscriber[]>([]);
  const [publisher, setPublisher] = useState<Publisher | null>(null);

  function handleMainVideoStream(stream: Publisher | Subscriber) {
    if (mainStreamManager !== stream) {
      setMainStreamManager(stream);
    }
  }

  function deleteSubscriber(streamManager: Subscriber) {
    if (subscriberList === null) {
      return;
    }
    const subscribers: Subscriber[] = subscriberList;
    const index = subscribers.indexOf(streamManager, 0);
    if (index > -1) {
      subscribers.splice(index, 1);
      setSubscriberList(subscribers);
    }
  }

  const joinSession = async () => {
    if (OV !== null) {
      setSession(OV.initSession());
      const mySession = session;

      mySession?.on('streamCreated', (event) => {
        const subscriber = mySession.subscribe(event.stream, undefined);
        setSubscriberList([...subscriberList, subscriber]);
      });

      mySession?.on('streamDestroyed', (event) => {
        if (event.stream.streamManager instanceof Subscriber) {
          deleteSubscriber(event.stream.streamManager);
        }
      });

      mySession?.on('exception', (exception) => {
        console.warn(exception);
      });

      const token = await getToken();
      console.log(token);
      mySession?.connect(token, { clientData: myUserName }).then(async () => {
        console.log('Connected');
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

        const devices = await OV.getDevices();
        const videoDevices = devices.filter(
          (device) => device.kind === 'videoinput',
        );

        const currentVideoDeviceId = publisher.stream
          .getMediaStream()
          .getVideoTracks()[0]
          .getSettings().deviceId;
        const currentVideoDevice = videoDevices.find(
          (device) => device.deviceId === currentVideoDeviceId,
        );

        setMainStreamManager(publisher);
        setPublisher(publisher);
        if (currentVideoDevice !== undefined) {
          setCurrentVideoDevice(currentVideoDevice);
        } else {
          setCurrentVideoDevice(null);
        }
      });
    }
  };

  function leaveSession() {
    // --- 7) Leave the session by calling 'disconnect' method over the Session object ---

    const mySession = session;

    if (mySession) {
      mySession.disconnect();
    }

    // Empty all properties...
    setSession(null);
    setSubscriberList([]);
    setMainStreamManager(null);
    setPublisher(null);
  }

  async function switchCamera() {
    try {
      const devices = await OV?.getDevices();
      const videoDevices = devices?.filter(
        (device) => device.kind === 'videoinput',
      );

      if (videoDevices && videoDevices.length > 1) {
        const newVideoDevice = videoDevices.filter(
          (device) => device.deviceId !== currentVideoDevice?.deviceId,
        );

        if (newVideoDevice.length > 0) {
          // Creating a new publisher with specific videoSource
          // In mobile devices the default and first camera is the front one
          const newPublisher = OV?.initPublisher(undefined, {
            videoSource: newVideoDevice[0].deviceId,
            publishAudio: true,
            publishVideo: true,
            mirror: true,
          });

          if (
            mainStreamManager !== null &&
            mainStreamManager instanceof Publisher
          ) {
            //newPublisher.once("accessAllowed", () => {
            await session?.unpublish(mainStreamManager);
            if (newPublisher) {
              await session?.publish(newPublisher);

              setMainStreamManager(newPublisher);
              setPublisher(newPublisher);
              setCurrentVideoDevice(newVideoDevice[0]);
            }
          }
        }
      }
    } catch (e) {
      console.error(e);
    }
  }

  async function getToken() {
    return await createToken(sessionId);
  }

  async function createSession(sessionId: string) {
    const response = await axios.post(
      APPLICATION_SERVER_URL + '/openvidu/sessions',
      { customSessionId: sessionId },
      {
        headers: { 'Content-Type': 'application/json' },
      },
    );
    return response.data; // The sessionId
  }

  async function createToken(sessionId: string) {
    const response = await axios.post(
      APPLICATION_SERVER_URL + `/openvidu/sessions/${sessionId}/connections`,
      {},
      {
        headers: { 'Content-Type': 'application/json' },
      },
    );
    return response.data; // The token
  }

  useEffect(() => {
    createSession('sessionA');
    joinSession();
  }, []);

  return (
    <div>
      <header>면회실 헤더</header>
      <button type="button" onClick={() => leaveSession}>
        나가기
      </button>
      <button type="button" onClick={() => switchCamera}>
        카메라 전환
      </button>
      {mainStreamManager !== null ? (
        <UserVideo streamManager={mainStreamManager} />
      ) : null}
      <div>
        {publisher !== null ? (
          <div onClick={() => handleMainVideoStream(publisher)}>
            <UserVideo streamManager={publisher} />
          </div>
        ) : null}
        {subscriberList.map((sub) => (
          <div key={sub.id} onClick={() => handleMainVideoStream(sub)}>
            <span>{sub.id}</span>
            <UserVideo streamManager={sub} />
          </div>
        ))}
      </div>
    </div>
  );
}

export default VisitRoom;
