import React from "react";
import Header from "../components/common/Header";
import { useNavigate } from "react-router-dom";

function Main() {
  const navigate = useNavigate();

  function handleAppLogin() {
    navigate("/login", { state: { pathType: "app" } });
  }

  function handleTarLogin() {
    navigate("/login", { state: { pathType: "tar" } });
  }

  return (
    <>
      <div>
        <Header />
        Main
      </div>

      <div>
        <button onClick={handleAppLogin}>보호자 로그인</button>
        <button onClick={handleTarLogin}>간병인 로그인</button>
      </div>
    </>
  );
}

export default Main;
