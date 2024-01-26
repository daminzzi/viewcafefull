import React, { useState, useEffect } from 'react';
import styled, { css } from 'styled-components';
import { white, black, deep, medium } from '../../assets/styles/palettes';
import { Link, useLocation } from 'react-router-dom';
import FlexRowContainer from '../../components/common/FlexRowContainer';
import { ReactComponent as HomeIcon } from '../../assets/icons/home.svg';
import { ReactComponent as GalleryIcon } from '../../assets/icons/gallery.svg';
import { ReactComponent as MessageIcon } from '../../assets/icons/message.svg';
import { ReactComponent as VisitIcon } from '../../assets/icons/visit.svg';
import { ReactComponent as ProfileIcon } from '../../assets/icons/profile.svg';

const NavDiv = styled(FlexRowContainer)`
  background-color: ${white};
  padding: 10px 0;
  border-top: 2px solid ${black};
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

function FamilyNav() {
  const location = useLocation();
  const [actived, setActived] = useState<string | null>(null);

  useEffect(() => {
    const locArr = location.pathname.split('/');

    if (location.pathname == '/family') {
      setActived('home');
    } else {
      setActived(locArr[2]);
    }
  });

  return (
    <NavDiv className="family-nav">
      <NavLink to="/family" $isActived={actived === 'home'}>
        <HomeIcon className="home-icon" />
      </NavLink>
      <NavLink to="/family/visit" $isActived={actived === 'visit'}>
        <VisitIcon className="visit-icon" />
      </NavLink>
      <NavLink to="/family/message" $isActived={actived === 'message'}>
        <MessageIcon className="message-icon" />
      </NavLink>
      <NavLink to="/family/gallery" $isActived={actived === 'gallery'}>
        <GalleryIcon className="gallery-icon" />
      </NavLink>
      <NavLink to="/family/profile" $isActived={actived === 'profile'}>
        <ProfileIcon className="profile-icon" />
      </NavLink>
    </NavDiv>
  );
}

export default FamilyNav;
