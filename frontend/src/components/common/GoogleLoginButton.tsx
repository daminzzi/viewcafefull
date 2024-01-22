import React from "react";
import { GoogleLogin, GoogleOAuthProvider } from "@react-oauth/google";
import { useNavigate } from "react-router-dom";
import useUserStore, { User } from "../../stores/userStore";
import api from "../../services/api";

interface ResponseData {
  user: User | null;
  accessToken: string | null;
}

function GoogleLoginButton() {
  const navigate = useNavigate();
  const { setUser, setAccessToken } = useUserStore();

  // 환경 변수에서 Google client ID를 가져옴
  const clientId = process.env.REACT_APP_GOOGLE_CLIENT_ID || "";
  return (
    <>
      {/* GoogleOAuthProvider 컴포넌트로 Google로그인 설정 */}
      <GoogleOAuthProvider clientId={clientId}>
        {/* GoogleLogin 컴포넌트로 Google 로그인 버튼 생성 */}
        {/* 버튼 클릭 -> Google 로그인 페이지를 열고, 로그인을 완료시 결과 반환 */}
        <GoogleLogin
          // 구글 인증 완료시 googleToken 발급 후 로그인or회원가입 처리
          onSuccess={(credentialResponse) => {
            const googleToken = credentialResponse.credential;

            api
              .post<ResponseData>("/users/google/signin", {
                googleToken: googleToken,
              })
              .then((response) => {
                setUser(response.data.user);
                setAccessToken(response.data.accessToken);
                navigate("/family/home");
              })
              .catch((error) => {
                // 구글로 가입되지 않은 사용자인 경우
                if (error.response.status === 404) {
                  // googleToken을 포함시킨 회원가입 페이지로 이동
                  navigate("/signup", { state: { googleToken } });
                } else {
                  console.error(error);
                }
              });
          }}
          onError={() => {
            console.log("구글 로그인 실패");
          }}
        />
      </GoogleOAuthProvider>
    </>
  );
}

export default GoogleLoginButton;
