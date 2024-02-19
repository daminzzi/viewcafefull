import React from 'react';
import styled from 'styled-components';
import { light } from '../../assets/styles/palettes';
import gramdma from '../../assets/images/grandma.png';
import ProfileFrame from './ProfileFrame';
import useConnectStore from '../../stores/ConnectStore';
import useUserStore from '../../stores/UserStore';
import FlexRowContainer from './FlexRowContainer';

const HeaderDiv = styled.header`
  display: flex;
  height: 8vh;
  background-color: ${light};
  padding: 0 10px 0px 10px;
  align-items: center;
  z-index: 1;
  top: 0;
`;

const HeaderSpan = styled.span`
  margin: 0 1vh;
  font-size: 1.8rem;
  font-weight: 700;
`;
const HeaderSubSpan = styled.span`
  font-size: 1.3rem;
  font-weight: 500;
  padding: 0 0 3px 0;
`;

function Header() {
  const { connectArr, currConnect, updateConnect } = useConnectStore();
  const { user } = useUserStore();

  if (connectArr.length === 0 && user) {
    updateConnect('app', user?.id);
  }

  return (
    <HeaderDiv className="family-header">
      <ProfileFrame src={gramdma} alt="profile" $size="4vh" />
      <FlexRowContainer $justifyContent="start" $alignItems="end">
        <HeaderSpan>{currConnect.tarName}</HeaderSpan>
        <HeaderSubSpan>님</HeaderSubSpan>
      </FlexRowContainer>
    </HeaderDiv>
  );
}

export default Header;
