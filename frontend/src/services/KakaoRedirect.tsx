import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from './api';
import useUserStore from '../stores/userStore';

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
    // 로그인 시도 시 카카오토큰 유무 확인, 있으면 로그인 완료
    api
      .get<ResponseData>(`/users/kakao/signin?code=${kakaoToken}`)
      .then((response) => {
        setUser(response.data.user);
        setAccessToken(response.data.accessToken);
        navigate('/family');
      })
      .catch((error) => {
        // 카카오로 가입되지 않은 사용자인 경우(카카오토큰이 없음)
        if (error.response.status === 400) {
          const kakaoEmail = error.response.data.email;
          // 이메일을 포함시킨 회원가입 페이지로 이동
          navigate('/signup', { state: { kakaoEmail } });
        } else {
          console.error(error);
        }
      });
  }, [navigate, setAccessToken, setUser]);

  return <div>카카오 로그인 처리중...</div>;
}

export default KakaoRedirect;
