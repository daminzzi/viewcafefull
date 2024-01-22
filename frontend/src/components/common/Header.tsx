import React from "react";
import useUserStore from "../../stores/userStore";
import { useNavigate } from "react-router-dom";

function Header() {
  const navigate = useNavigate();
  const { isAuthenticated, logout, deleteUser } = useUserStore();

  function handleLogout() {
    logout();
    navigate("/");
  }

  function handleDelete() {
    deleteUser();
    navigate("/");
  }

  return (
    <div>
      <h1>Header</h1>
      {isAuthenticated ? (
        <div>
          <button onClick={() => handleLogout()}>로그아웃</button>
          <button onClick={() => handleDelete()}>회원탈퇴</button>
        </div>
      ) : null}
    </div>
  );
}

export default Header;
