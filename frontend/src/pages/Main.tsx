import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from '../components/common/Buttons';
import styled from 'styled-components';
import UserContainer from '../components/common/UserContainer';
import { ReactComponent as Logo } from '../assets/icons/mainLogo.svg';
import tarLogin from '../assets/icons/tarLogin.svg';
import appLogin from '../assets/icons/appLogin.svg';
import FlexColContainer from '../components/common/FlexColContainer';
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
    <UserContainer $flexDirection="col" $padding="12px">
      <MainText>뷰케어풀</MainText>
      <FlexColContainer $width="80%" $gap="10px">
        <Logo width={'200px'}></Logo>
        <FlexRowContainer>
          <Button $width="100px" $padding="20px" onClick={handleAppLogin}>
            <img src={appLogin} />
            <div>보호자</div>
          </Button>
          <Button $width="100px" $padding="20px" onClick={handleTarLogin}>
            <img src={tarLogin} />
            <div>간병인</div>
          </Button>
        </FlexRowContainer>
      </FlexColContainer>
    </UserContainer>
  );
}

export default Main;

const MainText = styled.div`
  position: absolute;
  top: 60px;
  font-weight: bold;
  font-size: 30px;
`;
