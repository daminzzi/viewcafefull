import React from 'react';
import styled from 'styled-components';
import { Outlet } from 'react-router-dom';
import Nav from '../../components/caregiver/Nav';

const Wrapper = styled.div`
  min-height: 82.5vh;
  margin-bottom: 8vh;
`;

function CareGiver() {
  return (
    <div>
      <Wrapper>
        <Outlet />
        <Nav />
      </Wrapper>
    </div>
  );
}

export default CareGiver;
