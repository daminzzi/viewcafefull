import React, { ChangeEvent, FormEvent, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import useUserStore from '../../stores/userStore';
import GoogleLoginButton from '../../components/common/GoogleLoginButton';
import Input from '../../components/common/Input';
import KakaoButton from '../../assets/images/kakao_login_medium_wide.png';
import { Button } from '../../components/common/Buttons';
import UserContainer from '../../components/common/UserContainer';
import * as S from './Login.styles';

type PathType = 'app' | 'tar';

interface Form {
  id: string;
  password: string;
}

function Login() {
  const { login } = useUserStore();
  const navigate = useNavigate();
  const location = useLocation();
  const pathType: PathType = location.state?.pathType ?? 'tar';

  const [form, setForm] = useState<Form>({
    id: '',
    password: '',
  });

  function handleChange(e: ChangeEvent<HTMLInputElement>) {
    const { name, value } = e.target;

    setForm((prevForm) => ({
      ...prevForm,
      [name]: value,
    }));
  }

  async function handleLogin(e: FormEvent<HTMLFormElement>) {
    e.preventDefault();

    try {
      await login(form);
      if (pathType === 'app') {
        // 보호자 로그인 완료시
        navigate('/family');
      } else if (pathType === 'tar') {
        // 간병인 로그인 완료시
        navigate('/caregiver');
      }
    } catch (error) {
      console.error(error);
      alert('로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.');
    }
  }

  function handleAppSignUp() {
    navigate('/signup');
  }

  const REST_API_KEY = process.env.REACT_APP_KAKAO_REST_API_KEY;
  const REDIRECT_URL = process.env.REACT_APP_KAKAO_REDIRECT_URI;
  const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URL}&response_type=code`;
  const handleKakaoLogin = () => {
    window.location.href = kakaoURL;
  };

  return (
    <S.GrayBackground>
      <UserContainer $alignItems="left" $padding="12px">
        <S.LoginText>로그인</S.LoginText>
        <S.Form onSubmit={handleLogin}>
          <S.Label>
            <div>아이디</div>
            <Input
              $marginBottom="5px"
              $width="97%"
              type="text"
              name="id"
              value={form.id}
              placeholder="아이디를 입력해주세요."
              onChange={handleChange}
              required
            />
          </S.Label>
          <S.Label>
            <div>비밀번호</div>
            <Input
              $marginBottom="5px"
              $width="97%"
              type="password"
              name="password"
              value={form.password}
              placeholder="비밀번호를 입력해주세요."
              onChange={handleChange}
              required
            />
          </S.Label>
          <Button $margin="10px 0px 0px 0px" $width="100%" type="submit">
            로그인
          </Button>
        </S.Form>
        {pathType === 'app' ? (
          <S.Form>
            <S.RowContainer>
              <div>비밀번호 찾기</div> | <div>아이디 찾기</div> |
              <div onClick={handleAppSignUp}> 회원가입</div>
            </S.RowContainer>
            <S.RowContainerWithLine>
              <S.Line />
              <span>간편로그인</span>
              <S.Line />
            </S.RowContainerWithLine>
            <img
              src={KakaoButton}
              alt="Kakao login"
              onClick={handleKakaoLogin}
              style={{
                width: '100%',
                height: '40px',
                paddingBottom: '10px',
                cursor: 'pointer',
              }}
            />
            <GoogleLoginButton />
          </S.Form>
        ) : null}
      </UserContainer>
    </S.GrayBackground>
  );
}

export default Login;
