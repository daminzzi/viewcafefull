import { ChangeEvent, FormEvent, useState } from 'react';
import useUserStore from '../stores/userStore';
import { useNavigate, useLocation } from 'react-router-dom';
import GoogleLoginButton from '../components/common/GoogleLoginButton';

type PathType = 'app' | 'tar';

interface Form {
  id: string;
  password: string;
}

const Login = () => {
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

    setForm(prevForm => ({
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
          navigate('/family/home');
        } else if (pathType === 'tar') {
          // 간병인 로그인 완료시
          navigate('/caregiver/home');
        }
    } catch (error) {
        console.error(error);
    }
  }

  function handleAppSignUp() {
    navigate("/signup");
  }

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
                <hr />
                <button onClick={handleAppSignUp}>회원가입</button>
              </>
            : null
          }
        </div>
    </div>
  );
};

export default Login;
