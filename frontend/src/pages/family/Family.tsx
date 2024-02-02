import React from 'react';
import styled from 'styled-components';
import { Outlet } from 'react-router-dom';
import Header from '../../components/family/Header';
import Nav from '../../components/family/Nav';

const Wrapper = styled.div`
  margin-bottom: 8vh;
`;

function Family() {
  return (
    <div>
      <Header />
      <Wrapper>
        <Outlet />
      </Wrapper>
      <Nav />
    </div>
  );
}

export default Family;
