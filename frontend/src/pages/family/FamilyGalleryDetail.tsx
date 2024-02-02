import React, { useState } from 'react';
import { ReactComponent as ChevronLeft } from '../../assets/icons/chevron-left.svg';
import { useNavigate, useLocation } from 'react-router-dom';
import styled from 'styled-components';
import { black, white } from '../../assets/styles/palettes';
import FlexColContainer from '../../components/common/FlexColContainer';

const Container = styled(FlexColContainer)`
  background-color: ${black};
  display: flex;
  width: 100%;
  height: 84vh;
  position: relative;
  cursor: pointer;
`;

const VoidButton = styled.button`
  background-color: transparent;
  border: none;
  cursor: pointer;
  padding: 0;
  margin: 0 2vw;
`;

const Toolbar = styled.div<{ $isVisible: boolean }>`
  display: flex;
  align-content: center;
  width: 100%;
  position: absolute;
  top: 0;
  background-color: ${white + '99'};
  visibility: ${(props) => (props.$isVisible ? 'visible' : 'hidden')};
`;

const Wrapper = styled.div`
  display: flex;
  height: 100%;
  justify-content: center;
  align-items: center;
`;

const InnerImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: contain;
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;
`;

function FamilyGalleryDetail() {
  const location = useLocation();
  const [isVisible, setIsVisible] = useState(true);
  const navigate = useNavigate();

  function handleVisible() {
    setIsVisible(!isVisible);
  }

  return (
    <Container onClick={() => handleVisible()}>
      <Toolbar $isVisible={isVisible} onClick={(e) => e.stopPropagation()}>
        <VoidButton onClick={() => navigate(-1)}>
          <ChevronLeft width="4rem" color={black} />
        </VoidButton>
      </Toolbar>
      <Wrapper>
        <InnerImage src={location.state.src} alt="detail" />
      </Wrapper>
    </Container>
  );
}

export default FamilyGalleryDetail;
