import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from '../components/common/Buttons';
import styled from 'styled-components';
import UserContainer from '../components/common/UserContainer';

function Main() {
  const navigate = useNavigate();

  function handleAppLogin() {
    navigate('/login', { state: { pathType: 'app' } });
  }

  function handleTarLogin() {
    navigate('/login', { state: { pathType: 'tar' } });
  }

  return (
    <UserContainer $flexDirection="col" $padding="12px">
      <MainText>뷰케어풀</MainText>
      <Button $width="100%" $margin="0px 10px 0px 0px" onClick={handleAppLogin}>
        보호자 로그인
      </Button>
      <Button $width="100%" onClick={handleTarLogin}>
        간병인 로그인
      </Button>
    </UserContainer>
  );
}

export default Main;

const MainText = styled.div`
  position: absolute;
  top: 100px;
  font-weight: bold;
  font-size: 30px;
`;
