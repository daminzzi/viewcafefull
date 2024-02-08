import React from 'react';
import styled from 'styled-components';
import { light } from '../../assets/styles/palettes';
import NoProfile from '../../assets/images/noProfile.png';
import ProfileFrame from './ProfileFrame';
import useConnectStore from '../../stores/ConnectStore';
import useUserStore from '../../stores/UserStore';

const HeaderDiv = styled.header`
  display: flex;
  height: 8vh;
  background-color: ${light};
  padding: 0 10px;
  align-items: center;
  position: sticky;
  z-index: 1;
  top: 0;
`;

const HeaderSpan = styled.span`
  margin: 0 1vh;
  font-size: 3vh;
`;

function Header() {
  const { connectArr, currConnect, updateConnect } = useConnectStore();
  const { user } = useUserStore();

  if (connectArr.length === 0 && user) {
    updateConnect('app', user?.id);
  }

  return (
    <HeaderDiv className="family-header">
      <ProfileFrame src={NoProfile} alt="profile" $size="4vh" />
      <HeaderSpan>{currConnect.tarName}</HeaderSpan>
    </HeaderDiv>
  );
}

export default Header;
