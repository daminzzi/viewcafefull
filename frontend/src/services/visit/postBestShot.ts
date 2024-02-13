import api from '../api';

async function postBestShot(sessionId: string, photo: string) {
  try {
    const response = await api.post(`/bestphoto/${sessionId}`, {
      base64Data: photo,
    });
    if (response.status === 201) {
      return;
    }
  } catch (error) {
    console.log(error);
    throw error;
  }
}

export default postBestShot;
