import React from 'react';
import styled from 'styled-components';
import { ReactComponent as HomeIcon } from '../../assets/icons/home.svg';
import { ReactComponent as GalleryIcon } from '../../assets/icons/gallery.svg';
import { ReactComponent as MessageIcon } from '../../assets/icons/message.svg';
import { ReactComponent as VisitIcon } from '../../assets/icons/visit.svg';
import { ReactComponent as ProfileIcon } from '../../assets/icons/profile.svg';

type Icon = 'home' | 'visit' | 'message' | 'gallery' | 'profile';

type Props = {
  children: React.ReactNode;
  icon?: Icon | null;
};

const TitleDiv = styled.div`
  display: flex;
  align-items: center;
  font-size: 1.5rem;
  line-height: 2;
  padding: 0 1rem;
`;

const TitleSpan = styled.span`
  margin-left: 0.75rem;
`;

function Title({ children, icon = null }: Props) {
  function renderIcon() {
    switch (icon) {
      case 'home':
        return <HomeIcon width="2rem" />;
      case 'visit':
        return <VisitIcon width="2rem" />;
      case 'message':
        return <MessageIcon width="2rem" />;
      case 'gallery':
        return <GalleryIcon width="2rem" />;
      case 'profile':
        return <ProfileIcon width="2rem" />;
      default:
        return null;
    }
  }
  return (
    <TitleDiv>
      {renderIcon()}
      <TitleSpan>{children}</TitleSpan>
    </TitleDiv>
  );
}

export default Title;
