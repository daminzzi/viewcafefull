import axios from 'axios';
import useUserStore from '../stores/userStore';

// axios 인스턴스 생성
const api = axios.create({
  baseURL: 'http://localhost:3000',
});

// 요청 인터셉터 추가
api.interceptors.request.use((config) => {
  const { accessToken } = useUserStore.getState();
  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

export default api;
