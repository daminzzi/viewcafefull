// /* eslint-disable import/no-cycle */

import axios from 'axios';
import useUserStore from '../stores/UserStore';
import refreshAccessToken from './user/refreshAccessToken';

// axios 인스턴스 생성
const api = axios.create({ baseURL: process.env.REACT_APP_SPRING_URL });

// 요청 인터셉터 추가
api.interceptors.request.use(
  (config) => {
    const { accessToken } = useUserStore.getState();
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => Promise.reject(error),
);

// 응답 인터셉터 추가
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    if (error.response.status === 401 && !originalRequest.retry) {
      originalRequest.retry = true;
      const { accessToken } = await refreshAccessToken();
      useUserStore.getState().setAccessToken(accessToken);
      return api(originalRequest);
    }
    return Promise.reject(error);
  },
);

export default api;
