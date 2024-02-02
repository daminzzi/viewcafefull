import { AxiosResponse } from 'axios';
import api from '../api';

async function getGallery(page: number): Promise<AxiosResponse> {
  try {
    const response = await api.get('/gallery', { params: { page: page } });
    if (response.status !== 200) {
      throw new Error(`오류: ${response.status}`);
    }
    return response.data;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export default getGallery;
