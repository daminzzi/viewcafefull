import React from 'react';
import { Route, Routes, BrowserRouter } from 'react-router-dom';
import styled from 'styled-components';
import { lightgray } from './assets/styles/palettes';
import Main from './pages/Main';
import Family from './pages/family/Family';
import CareGiver from './pages/caregiver/CareGiver';
import NotFound from './pages/NotFound';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import KakaoRedirect from './services/KakaoRedirect';
import FamilyHome from './pages/family/FamilyHome';
import FamilyGallery from './pages/family/FamilyGallery';
import FamilyMessage from './pages/family/FamilyMessage';
import FamilyVisit from './pages/family/FamilyVisit';
import FamilyProfile from './pages/family/FamilyProfile';
import CareGiverHome from './pages/caregiver/CareGiverHome';
import FamilyVisitRegister from './pages/family/FamilyVisitRegister';
import ConnectRegister from './pages/family/ConnectRegister';
import CareGiverMessage from './pages/caregiver/CareGiverMessage';
import CareGiverSendMessage from './pages/caregiver/CareGiverSendMessage';
import Report from './pages/Report';

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
          <Route path="/family" element={<Family />}>
            <Route path="" element={<FamilyHome />} />
            <Route path="gallery" element={<FamilyGallery />} />
            <Route path="message" element={<FamilyMessage />} />
            <Route path="visit" element={<FamilyVisit />} />
            <Route path="visit/register" element={<FamilyVisitRegister />} />
            <Route path="profile" element={<FamilyProfile />} />
            <Route path="connect" element={<ConnectRegister />} />
          </Route>
          <Route path="/caregiver" element={<CareGiver />}>
            <Route path="home" element={<CareGiverHome />} />
            <Route path="message" element={<CareGiverMessage />} />
            <Route path="message/send" element={<CareGiverSendMessage />} />
          </Route>
          <Route path="/report/:id" element={<Report />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </AppDiv>
  );
}

export default App;
