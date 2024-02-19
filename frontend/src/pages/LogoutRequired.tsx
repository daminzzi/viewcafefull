import React from 'react';
import { Outlet, Navigate } from 'react-router-dom';
import useUserStore from '../stores/UserStore';

function LogoutRequired() {
  const { isLogin, user } = useUserStore();

  function permission() {
    if (isLogin && user) {
      switch (user.role) {
        case 'Caregiver':
          return <Navigate to="/caregiver" replace={true} />;

        case 'Guardian':
          return <Navigate to="/family" replace={true} />;

        default:
          return <Navigate to="/logout" replace={true} />;
      }
    }
    return <Outlet />;
  }

  return permission();
}

export default LogoutRequired;
