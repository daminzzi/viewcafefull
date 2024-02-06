import React from 'react';
import styled from 'styled-components';
import { Outlet } from 'react-router-dom';

const Wrapper = styled.div`
  min-height: 84vh;
  margin-bottom: 8vh;
`;

function CareGiver() {
  return (
    <div>
      <Wrapper>
        <Outlet />
      </Wrapper>
    </div>
  );
}

export default CareGiver;
