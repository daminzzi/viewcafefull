import React from 'react';
import styled from 'styled-components';
import { Outlet } from 'react-router-dom';
import Nav from '../../components/caregiver/Nav';
import Header from '../../components/common/Header';

const Wrapper = styled.div`
  min-height: 84vh;
  padding-bottom: 8vh;
`;

function CareGiver() {
  return (
    <div>
      <Wrapper>
        <Header />
        <Outlet />
        <Nav />
      </Wrapper>
    </div>
  );
}

export default CareGiver;
