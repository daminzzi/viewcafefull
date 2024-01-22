import React from 'react';
import { useNavigate } from 'react-router-dom';
import useUserStore from '../../stores/userStore';

function Header() {
  const navigate = useNavigate();
  const { isAuthenticated, logout, deleteUser } = useUserStore();

  function handleLogout() {
    logout();
    navigate('/');
  }

  function handleDelete() {
    deleteUser();
    navigate('/');
  }

  return (
    <div>
      <h1>Header</h1>
      {isAuthenticated ? (
        <div>
          <button type="button" onClick={() => handleLogout()}>
            로그아웃
          </button>
          <button type="button" onClick={() => handleDelete()}>
            회원탈퇴
          </button>
        </div>
      ) : null}
    </div>
  );
}

export default Header;
