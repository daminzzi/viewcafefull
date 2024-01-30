import React from 'react';
import styled from 'styled-components';
import { light } from '../../assets/styles/palettes';
import { ReactComponent as ChevronDownIcon } from '../../assets/icons/chevron-down.svg';
import NoProfile from '../../assets/images/NoProfile.png';
import ProfileFrame from '../common/ProfileFrame';
import useConnectStore from '../../stores/ConnectStore';

const HeaderDiv = styled.div`
  background-color: ${light};
  padding: 10px 10px;
  display: flex;
  align-items: center;
`;

const HeaderSpan = styled.span`
  margin: 0 5px;
  font-size: 20px;
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
      <ProfileFrame src={NoProfile} alt="profile" $size="30px" />
      <HeaderSpan>{currConnect.tarName}</HeaderSpan>
      <ChevronDownIcon
        className="chevron-down-icon"
        width="30px"
        height="30px"
      />
    </HeaderDiv>
  );
}

export default Header;
