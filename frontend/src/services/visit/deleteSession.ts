import api from '../api';

async function deleteSession(sessionId: string) {
  try {
    const response = await api.delete(`/openvidu/sessions/${sessionId}`);
    return response.data;
  } catch (error) {
    console.log(error);
    throw error;
  }
}

export default deleteSession;
