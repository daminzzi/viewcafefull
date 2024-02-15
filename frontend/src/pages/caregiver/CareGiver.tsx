import React from 'react';
import { Navigate } from 'react-router-dom';
import styled from 'styled-components';
import { Outlet } from 'react-router-dom';
import useUserStore from '../../stores/UserStore';
import Nav from '../../components/caregiver/Nav';
import Header from '../../components/common/Header';

const Wrapper = styled.div`
  min-height: 84vh;
  padding-bottom: 8vh;
`;

function CareGiver() {
  const { user } = useUserStore();

  function checkPermission() {
    if (user?.role !== 'Caregiver') {
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

export default CareGiver;
