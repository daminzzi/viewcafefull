import React, { useEffect } from 'react';
import { Navigate } from 'react-router-dom';
import useUserStore from '../stores/UserStore';
import useHealthStore from '../stores/HealthStore';
import useCareGiverMainStore from '../stores/CaregiverMainStore';
import useConnectStore from '../stores/ConnectStore';
import useGalleryStore from '../stores/GalleryStore';


function LogoutPage() {
  const { isLogin, logout } = useUserStore();
  const ResetHealth = useHealthStore().reset
  const ResetGallery = useGalleryStore().reset
  const ResetConnect = useConnectStore().reset
  const ResetCareGiverMain = useCareGiverMainStore().reset

  useEffect(() => {
    logout()
    ResetHealth()
    ResetGallery()
    ResetConnect()
    ResetCareGiverMain()
  }, []);

  function render() {
    return <Navigate to="/" replace={true} />
  }

  return isLogin ? null : render()
}

export default LogoutPage;
