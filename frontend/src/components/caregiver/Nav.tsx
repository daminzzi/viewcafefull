import React, { useState, useEffect } from 'react';
import styled, { css } from 'styled-components';
import { white, black, deep, medium } from '../../assets/styles/palettes';
import { Link, useLocation } from 'react-router-dom';
import { ReactComponent as HomeIcon } from '../../assets/icons/home.svg';
import { ReactComponent as GalleryIcon } from '../../assets/icons/gallery.svg';
import { ReactComponent as MessageIcon } from '../../assets/icons/message.svg';
import { ReactComponent as VisitIcon } from '../../assets/icons/visit.svg';

const NavDiv = styled.nav`
  display: flex;
  height: 7.9vh;
  width: 100vw;
  justify-content: space-around;
  background-color: ${white};
  border-top: 0.1vh solid ${black};
  position: fixed;
  bottom: 0;
`;

const NavLink = styled(Link)<{ $isActived: boolean }>`
  text-decoration-line: none;
  display: flex;
  justify-content: center;
  width: 20%;
  color: ${medium};
  ${(props) =>
    props.$isActived &&
    css`
      color: ${deep};
    `}
`;

function Nav() {
  const location = useLocation();
  const [actived, setActived] = useState<string | null>(null);

  useEffect(() => {
    const locArr = location.pathname.split('/');

    if (location.pathname == '/caregiver') {
      setActived('home');
    } else {
      setActived(locArr[2]);
    }
  });

  return (
    <NavDiv className="caregiver-nav">
      <NavLink to="/caregiver" $isActived={actived === 'home'}>
        <HomeIcon className="home-icon" width="40%" />
      </NavLink>
      <NavLink to="/caregiver/visit" $isActived={actived === 'visit'}>
        <VisitIcon className="visit-icon" width="40%" />
      </NavLink>
      <NavLink to="/caregiver/message/send" $isActived={actived === 'message'}>
        <MessageIcon className="message-icon" width="40%" />
      </NavLink>
      <NavLink to="/caregiver/gallery" $isActived={actived === 'gallery'}>
        <GalleryIcon className="gallery-icon" width="40%" />
      </NavLink>
    </NavDiv>
  );
}

export default Nav;
