import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from '../components/common/Buttons';
import styled from 'styled-components';
import UserContainer from '../components/common/UserContainer';
import { ReactComponent as Logo } from '../assets/icons/mainLogo.svg';
import tarLogin from '../assets/images/tarLogin.png'
import appLogin from '../assets/images/appLogin.png';
import FlexRowContainer from '../components/common/FlexRowContainer';

function Main() {
  const navigate = useNavigate();

  function handleAppLogin() {
    navigate('/login', { state: { pathType: 'app' } });
  }

  function handleTarLogin() {
    navigate('/login', { state: { pathType: 'tar' } });
  }

  return (
    <UserContainer $padding="12px" $justifyContent="space-around">
      <MainText>뷰케어풀</MainText>

      <Logo width={'200px'}></Logo>

      <FlexRowContainer $width="90%" $margin="-80px 0 0 0">
        <Button $width="100px" $padding="20px" onClick={handleAppLogin}>
          <img src={appLogin} width={'40px'} />
          <div>보호자</div>
        </Button>
        <Button $width="100px" $padding="20px" onClick={handleTarLogin}>
          <img src={tarLogin} width={'40px'} />
          <div>간병인</div>
        </Button>
      </FlexRowContainer>
    </UserContainer>
  );
}

export default Main;

const MainText = styled.div`
  font-weight: bold;
  font-size: 30px;
  margin-bottom: -60px;
`;
