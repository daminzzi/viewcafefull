import React from 'react';
import { Route, Routes, BrowserRouter } from 'react-router-dom';
import Main from './pages/Main';
import Family from './pages/family/Family';
import CareGiver from './pages/caregiver/CareGiver';
import NotFound from './pages/NotFound';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import Visit from './pages/family/Visit';

function App() {
  return (
    <div>
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Main />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path='/family' element={<Family />} >
          <Route path="visit" element={<Visit />} />
        </Route>
        <Route path='/caregiver' element={<CareGiver />}>
        </Route>
        <Route path='*' element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  </div>
  );
}

export default App;
