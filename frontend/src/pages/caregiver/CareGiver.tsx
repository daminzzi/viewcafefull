import React from 'react';
import { Outlet } from 'react-router-dom';

function CareGiver() {
  return (
    <div>
      <Outlet />
    </div>
  );
}

export default CareGiver;
