import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from '../components/common/Buttons';
// import useUserStore from '../stores/UserStore';
import styled from 'styled-components';
import UserContainer from '../components/common/UserContainer';
import logo from '../assets/images/logo512.png';
import tarLogin from '../assets/images/tarLogin.png';
import appLogin from '../assets/images/appLogin.png';
import FlexRowContainer from '../components/common/FlexRowContainer';

function Main() {
  const navigate = useNavigate();
  // const { isLogin, role, logout } = useUserStore();

  // if (isLogin) {
  //   switch (role) {
  //     case 'Caregiver':
  //       navigate('/caregiver', { replace: true })
  //       break;

  //     case 'Guardian':
  //       navigate('/family', { replace: true })
  //       break;

  //     default:
  //       console.log('aaaaa')
  //       alert('33접근 권한이 없습니다.');
  //       logout();
  //       break;
  //   }
  // }

  function handleAppLogin() {
    navigate('/login', { state: { pathType: 'app' } });
  }

  function handleTarLogin() {
    navigate('/login', { state: { pathType: 'tar' } });
  }

  return (
    <UserContainer $padding="12px" $justifyContent="space-around">
      <MainText>뷰케어풀</MainText>
      <img src={logo} width={'200px'} />
      <FlexRowContainer $width="90%" $margin="-60px 0 0 0">
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
  margin-bottom: -70px;
`;
