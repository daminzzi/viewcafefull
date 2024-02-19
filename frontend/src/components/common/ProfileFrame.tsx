import React from 'react';
import styled from 'styled-components';
import { white, black } from '../../assets/styles/palettes';

type Props = {
  src: string;
  alt: string;
  $size: string;
};

const Wrapper = styled.div<{ $size: string }>`
  background-color: ${white};
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid ${black};
  border-radius: 50%;
  overflow: hidden;

  width: ${(props) => props.$size};
  aspect-ratio: 1 / 1;
`;

const ProfileImg = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

function ProfileFrame({ src, alt, ...props }: Props) {
  return (
    <Wrapper {...props}>
      <ProfileImg src={src} alt={alt} />
    </Wrapper>
  );
}

export default ProfileFrame;
