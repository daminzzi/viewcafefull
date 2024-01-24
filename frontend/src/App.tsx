import React from 'react';
import { Route, Routes, BrowserRouter } from 'react-router-dom';
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
import CareGiverMessage from './pages/caregiver/CareGiverMessage';

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Main />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/kakaoLogin" element={<KakaoRedirect />} />
          <Route path="/family" element={<Family />}>
            <Route path="home" element={<FamilyHome />} />
            <Route path="gallery" element={<FamilyGallery />} />
            <Route path="message" element={<FamilyMessage />} />
            <Route path="visit" element={<FamilyVisit />} />
            <Route path="visit/register" element={<FamilyVisitRegister />} />
            <Route path="profile" element={<FamilyProfile />} />
          </Route>
          <Route path="/caregiver" element={<CareGiver />}>
            <Route path="home" element={<CareGiverHome />} />
            <Route path="message" element={<CareGiverMessage />} />
          </Route>
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
