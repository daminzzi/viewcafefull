import { useState } from 'react';
import useUserStore from '../stores/userStore';
import { useNavigate, useLocation } from 'react-router-dom';
import GoogleLoginButton from '../components/common/GoogleLoginButton';

type PathType = 'app' | 'tar';

const Login = () => {
  const { login } = useUserStore();
  const navigate = useNavigate();
  const location = useLocation();
  const pathType: PathType = location.state?.pathType ?? 'tar';

  const [form, setForm] = useState({
    id: '',
    password: '',
  });


  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm({
      ...form,
      [name]: value,
    });
  };

  const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await login(form);
      navigate('/');
    } catch (error) {
      console.error(error);
    }
  };

  const Rest_api_key = process.env.REACT_APP_KAKAO_REST_API_KEY
  const redirect_uri = process.env.REACT_APP_KAKAO_REDIRECT_URI
  const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${Rest_api_key}&redirect_uri=${redirect_uri}&response_type=code`
  const handleKakaoLogin = ()=>{
      window.location.href = kakaoURL
  }

  return (
    <div>
      <h1>로그인</h1>
        <form onSubmit={handleLogin}>
            <label>
                아이디:
                <input type="text" name="id" value={form.id} onChange={handleChange} required />
            </label>
            <br/>
            <label>
                비밀번호:
                <input type="password" name="password" value={form.password} onChange={handleChange} required />
            </label>
            <br/>
            <button type="submit">로그인</button>
        </form>
        <div>
          {
            pathType === 'app'
            ? <><button onClick={handleKakaoLogin}>카카오 로그인</button>
                <GoogleLoginButton/>
              </>
            : null
          }
        </div>
    </div>
  );
};

export default Login;
