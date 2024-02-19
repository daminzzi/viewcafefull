import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from './api';
import useUserStore from '../stores/UserStore';
import getConnectInfo from './connect/getConnectInfo';
import useConnectStore from '../stores/ConnectStore';

interface ResponseData {
  user: User | null;
  accessToken: string | null;
}

// 카카오 로그인 후 Redirect되는 함수
function KakaoRedirect() {
  const navigate = useNavigate();
  const { setUser, setAccessToken } = useUserStore();
  const { updateConnect } = useConnectStore();

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
      .then(async (response) => {
        const user = response.data.user;
        const accessToken = response.data.accessToken;

        setUser(user);
        setAccessToken(accessToken);

        if (user) {
          const connectArray = await getConnectInfo('app', user.id);
          updateConnect('app', user.id);

          if (connectArray.length === 0) {
            navigate('/connect/register');
          } else if (connectArray.length !== 0) {
            navigate('/family');
          }
        }
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
