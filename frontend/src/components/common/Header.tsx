import useUserStore from '../../stores/userStore';
import { Link, useNavigate } from 'react-router-dom';

type Props = {
}

function Header({}: Props) {
  const navigate = useNavigate();
  const { isAuthenticated, logout, deleteUser } = useUserStore();

  function handleLogout() {
    logout();
    navigate("/");
  }

  function handleDelete() {
    deleteUser();
    navigate("/")
  }

  return (
    <div>
      <h1>Header</h1>
      {isAuthenticated ? (
        <div>
          <button onClick={() => handleLogout()}>로그아웃</button>
          <button onClick={() => handleDelete()}>회원탈퇴</button>
        </div>
      ) : (
        <div>
          <Link to="login">로그인</Link>|  
          <Link to="signup">회원가입</Link>
        </div>      
      )} 
    </div>
  )
}

export default Header