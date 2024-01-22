import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from './api';
import useUserStore, { User } from '../stores/userStore';

interface ResponseData {
  user: User | null;
  accessToken: string | null;
}

// 카카오 로그인 후 Redirect되는 함수
function KakaoRedirect() {
  const navigate = useNavigate();
  const { setUser, setAccessToken } = useUserStore();

  useEffect(() => {
    const url = new URL(window.location.href);
    const kakaoToken = url.searchParams.get('code');

    if (!kakaoToken) {
      console.error('카카오톡 인증 실패');
      return;
    }

    // 카카오 로그인
    api
      .post<ResponseData>('/users/kakao/signin', { kakaoToken })
      .then((response) => {
        setUser(response.data.user);
        setAccessToken(response.data.accessToken);
        navigate('/family/home');
      })
      .catch((error) => {
        // 카카오로 가입되지 않은 사용자인 경우
        if (error.response.status === 404) {
          // kakaoToken을 포함시킨 회원가입 페이지로 이동
          navigate('/signup', { state: { kakaoToken } });
        } else {
          console.error(error);
        }
      });
  }, [navigate, setAccessToken, setUser]);

  return <div>카카오 로그인 처리중...</div>;
}

export default KakaoRedirect;
