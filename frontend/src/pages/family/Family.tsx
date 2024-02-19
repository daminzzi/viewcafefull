import React from 'react';
import { Navigate } from 'react-router-dom';
import styled from 'styled-components';
import { Outlet } from 'react-router-dom';
import useUserStore from '../../stores/UserStore';
import Header from '../../components/common/Header';
import Nav from '../../components/family/Nav';

const Wrapper = styled.div`
  min-height: 84vh;
  padding-bottom: 8vh;
`;

function Family() {
  const { user } = useUserStore();

  function checkPermission() {
    if (user?.role !== 'Guardian') {
      return <Navigate to="/logout" replace={true} />;
    }
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

  return checkPermission();
}

export default Family;
