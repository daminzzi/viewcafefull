import React from 'react';
import { Outlet } from 'react-router-dom';
import Header from '../../components/family/Header';
import Nav from '../../components/family/Nav';

function Family() {
  return (
    <div>
      <Header />
      <Outlet />
      <Nav />
    </div>
  );
}

export default Family;
