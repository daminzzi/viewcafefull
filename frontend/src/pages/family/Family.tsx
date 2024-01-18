import React from 'react';
import { Outlet } from 'react-router-dom';
import FamilyHeader from './FamilyHeader';
import FamilyNav from './FamilyNav';

function Family() {
  
  return (
    <div>
      <FamilyHeader />
      아울렛
      <Outlet />
      <FamilyNav />
    </div>
  )
}

export default Family