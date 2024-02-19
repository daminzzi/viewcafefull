import React from 'react';
import { Route, Routes, BrowserRouter } from 'react-router-dom';
import styled from 'styled-components';
import { white } from './assets/styles/palettes';
import Main from './pages/Main';
import NotFound from './pages/NotFound';
import Login from './pages/Login/Login';
import SignUp from './pages/SignUp/SignUp';
import KakaoRedirect from './services/KakaoRedirect';
import Family from './pages/family/Family';
import FamilyHome from './pages/family/FamilyHome';
import FamilyGallery from './pages/family/FamilyGallery';
import FamilyMessage from './pages/family/FamilyMessage';
import FamilyVisit from './pages/family/FamilyVisit';
import FamilyProfile from './pages/family/FamilyProfile/FamilyProfile';
import FamilyVisitRegister from './pages/family/FamilyVisitRegister';
import CareGiver from './pages/caregiver/CareGiver';
import CareGiverHome from './pages/caregiver/CareGiverHome';
import CareGiverSendMessage from './pages/caregiver/CareGiverSendMessage/CareGiverSendMessage';
import CareGiverGallery from './pages/caregiver/CareGiverGallery';
import CareGiverGalleryUpload from './pages/caregiver/CareGiverGalleryUpload';
import CareGiverVisit from './pages/caregiver/CareGiverVisit';
import GalleryDetail from './pages/GalleryDetail';
import Report from './pages/Report';
import VisitRoom from './components/visit/VisitRoom';
import ConnectRegister from './pages/family/ConnectRegister';
import LogoutRequired from './pages/LogoutRequired';
import LogoutPage from './pages/LogoutPage';

const AppDiv = styled.div`
  background-color: ${white};
  margin: 0;
`;

function App() {
  return (
    <AppDiv>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<LogoutRequired />}>
            <Route path="" element={<Main />} />
            <Route path="login" element={<Login />} />
            <Route path="signup" element={<SignUp />} />
            <Route path="kakaoLogin" element={<KakaoRedirect />} />
          </Route>
          <Route path="/connect/register" element={<ConnectRegister />} />
          <Route path="/family" element={<Family />}>
            <Route path="" element={<FamilyHome />} />
            <Route path="gallery" element={<FamilyGallery />} />
            <Route path="message" element={<FamilyMessage />} />
            <Route path="visit" element={<FamilyVisit />} />
            <Route path="visit/register" element={<FamilyVisitRegister />} />
            <Route path="profile" element={<FamilyProfile />} />
          </Route>
          <Route path="/caregiver" element={<CareGiver />}>
            <Route path="" element={<CareGiverHome />} />
            <Route path="message/send" element={<CareGiverSendMessage />} />
            <Route path="gallery" element={<CareGiverGallery />} />
            <Route path="gallery/upload" element={<CareGiverGalleryUpload />} />
            <Route path="visit" element={<CareGiverVisit />} />
          </Route>
          <Route path="gallery/detail" element={<GalleryDetail />} />
          <Route path="/report/:id/:yearMonth" element={<Report />} />
          <Route path="/openvidu/:id" element={<VisitRoom />} />
          <Route path='/logout' element={<LogoutPage />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </AppDiv>
  );
}

export default App;
