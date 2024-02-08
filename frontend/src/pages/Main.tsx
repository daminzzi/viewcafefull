import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from '../components/common/Buttons';
import styled from 'styled-components';
import UserContainer from '../components/common/UserContainer';
import { ReactComponent as Logo } from '../assets/icons/mainLogo.svg';
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
      <FlexColContainer $gap="10px">
        <Logo width={'150px'}></Logo>
        <FlexRowContainer>
          <Button
            $width="100%"
            $margin="0 10px 0 0"
            onClick={handleAppLogin}
          >
            보호자 로그인
          </Button>
          <Button $width="100%" onClick={handleTarLogin}>
            간병인 로그인
          </Button>
        </FlexRowContainer>
      </FlexColContainer>
    </UserContainer>
  );
}

export default Main;

const MainText = styled.div`
  position: absolute;
  top: 110px;
  font-weight: bold;
  font-size: 35px;
`;
