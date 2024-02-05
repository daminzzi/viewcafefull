import api from '../api';

async function postSession(sessionId: string) {
  try {
    const response = await api.post('/openvidu/sessions', {
      customSessionId: sessionId,
    });
    return response.data;
  } catch (error) {
    console.log(error);
    throw error;
  }
}

export default postSession;
