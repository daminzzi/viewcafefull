import api from '../api';

async function postToken(sessionId: string) {
  try {
    const response = await api.post(
      `/openvidu/sessions/${sessionId}/connections`,
      {},
    );
    return response.data;
  } catch (error) {
    console.log(error);
    throw error;
  }
}

export default postToken;
