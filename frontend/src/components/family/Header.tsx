import React from 'react';
import styled from 'styled-components';
import { light } from '../../assets/styles/palettes';
import NoProfile from '../../assets/images/NoProfile.png';
import ProfileFrame from '../common/ProfileFrame';
import useConnectStore from '../../stores/ConnectStore';

const HeaderDiv = styled.header`
  display: flex;
  height: 8vh;
  background-color: ${light};
  padding: 0 10px;
  align-items: center;
  position: sticky;
  top: 0;
`;

const HeaderSpan = styled.span`
  margin: 0 1vh;
  font-size: 3vh;
`;

function Header() {
  const {
    connectArr,
    currConnect,
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    setCurr,
    updateConnect,
  } = useConnectStore();

  if (connectArr.length === 0) {
    updateConnect('app', 'asdf');
  }

  return (
    <HeaderDiv className="family-header">
      <ProfileFrame src={NoProfile} alt="profile" $size="4vh" />
      <HeaderSpan>{currConnect.tarName}</HeaderSpan>
    </HeaderDiv>
  );
}

export default Header;
