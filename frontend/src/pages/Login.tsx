import { useState } from 'react';
import useUserStore from '../stores/userStore';
import { useNavigate } from 'react-router-dom';


const Login = () => {
  const { login } = useUserStore();
  const { navigate } = useNavigate;
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

  const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => { // 변경
    e.preventDefault();
    try {
      await login(form);
      navigate('/');
    } catch (error) {
      console.error(error);
    }
  };

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
    </div>
  );
};

export default Login;
