import React from 'react';
import { Outlet } from 'react-router-dom';
import FamilyHeader from './FamilyHeader';
import FamilyNav from './FamilyNav';

function Family() {
  return (
    <div>
      <FamilyHeader />
      <Outlet />
      <FamilyNav />
    </div>
  );
}

export default Family;
