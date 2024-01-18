import React from 'react';
import { Outlet } from 'react-router-dom';
import FamilyHeader from './FamilyHeader';

function Family() {
  
  return (
    <div>
      <FamilyHeader />
      아울렛
      <Outlet />
    </div>
  )
}

export default Family