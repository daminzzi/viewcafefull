import React from 'react';
import styled from 'styled-components';
import { ReactComponent as HomeIcon } from '../../assets/icons/home.svg';
import { ReactComponent as GalleryIcon } from '../../assets/icons/gallery.svg';
import { ReactComponent as MessageIcon } from '../../assets/icons/message.svg';
import { ReactComponent as VisitIcon } from '../../assets/icons/visit.svg';
import { ReactComponent as ProfileIcon } from '../../assets/icons/profile.svg';
import FlexRowContainer from './FlexRowContainer';
import { Button } from './Buttons';
import { success, white } from '../../assets/styles/palettes';

type Icon = 'home' | 'visit' | 'message' | 'gallery' | 'profile';

type Props = {
  children: React.ReactNode;
  icon?: Icon | null;
  buttonContents?: string;
  handleClick?: () => void;
};

const TitleDiv = styled.div`
  display: flex;
  align-items: center;
  align-content: center;
  font-size: 1.5rem;
  line-height: 2;
`;

const TitleSpan = styled.span`
  margin-left: 0.75rem;
`;

function Title({ children, icon = null, buttonContents, handleClick }: Props) {
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

  function renderButton() {
    return (
      buttonContents && (
        <Button
          $width="auto"
          $padding='0.5rem 1.5rem'
          $bgColor={success}
          $color={white}
          $fontSize="1rem"
          onClick={() => {
            if (handleClick) {
              handleClick();
            }
          }}
        >
          {buttonContents}
        </Button>
      )
    );
  }

  return (
    <FlexRowContainer
      $justifyContent="space-between"
      $width="90%"
      $margin="1.5vh auto"
    >
      <TitleDiv>
        {renderIcon()}
        <TitleSpan>{children}</TitleSpan>
      </TitleDiv>
      {renderButton()}
    </FlexRowContainer>
  );
}

export default Title;
