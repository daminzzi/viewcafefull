import React from 'react';
import { Route, Routes, BrowserRouter } from 'react-router-dom';
import styled from 'styled-components';
import { lightgray } from './assets/styles/palettes';
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
import FamilyGalleryDetail from './pages/family/FamilyGalleryDetail';
import CareGiver from './pages/caregiver/CareGiver';
import CareGiverHome from './pages/caregiver/CareGiverHome';
import CareGiverMessage from './pages/caregiver/CareGiverMessage';
import CareGiverSendMessage from './pages/caregiver/CareGiverSendMessage';
import CareGiverGallery from './pages/caregiver/CareGiverGallery';
import CareGiverGalleryDetail from './pages/caregiver/CareGiverGalleryDetail';
import CareGiverGalleryUpload from './pages/caregiver/CareGiverGalleryUpload';
import Report from './pages/Report';
import VisitRoom from './components/visit/VisitRoom';
import ConnectRegister from './pages/family/ConnectRegister';

const AppDiv = styled.div`
  background-color: ${lightgray};
  margin: 0;
`;

function App() {
  return (
    <AppDiv>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Main />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/kakaoLogin" element={<KakaoRedirect />} />
          <Route path="/connect/register" element={<ConnectRegister />} />
          <Route path="/family" element={<Family />}>
            <Route path="" element={<FamilyHome />} />
            <Route path="gallery" element={<FamilyGallery />} />
            <Route path="gallery/detail" element={<FamilyGalleryDetail />} />
            <Route path="message" element={<FamilyMessage />} />
            <Route path="visit" element={<FamilyVisit />} />
            <Route path="visit/register" element={<FamilyVisitRegister />} />
            <Route path="profile" element={<FamilyProfile />} />
          </Route>
          <Route path="/caregiver" element={<CareGiver />}>
            <Route path="" element={<CareGiverHome />} />
            <Route path="message" element={<CareGiverMessage />} />
            <Route path="message/send" element={<CareGiverSendMessage />} />
            <Route path="gallery" element={<CareGiverGallery />} />
            <Route path="gallery/detail" element={<CareGiverGalleryDetail />} />
            <Route path="gallery/upload" element={<CareGiverGalleryUpload />} />
          </Route>
          <Route path="/report/:id" element={<Report />} />
          <Route path="/openvidu/:id" element={<VisitRoom />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </AppDiv>
  );
}

export default App;
