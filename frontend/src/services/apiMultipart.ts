// /* eslint-disable import/no-cycle */

import axios from 'axios';
import useUserStore from '../stores/UserStore';
import refreshAccessToken from './user/refreshAccessToken';

// axios 인스턴스 생성
const apiMultipart = axios.create({ baseURL: process.env.REACT_APP_SPRING_URL });

// 요청 인터셉터 추가
apiMultipart.interceptors.request.use(
  (config) => {
    const { accessToken } = useUserStore.getState();
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
      config.headers['Content-Type'] = 'multipart/form-data';
    }
    return config;
  },
  (error) => Promise.reject(error),
);

// 응답 인터셉터 추가
apiMultipart.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    if (error.response.status === 401 && !originalRequest.retry) {
      originalRequest.retry = true;
      const { accessToken } = await refreshAccessToken();
      useUserStore.getState().setAccessToken(accessToken);
      return apiMultipart(originalRequest);
    }
    return Promise.reject(error);
  },
);

export default apiMultipart;
