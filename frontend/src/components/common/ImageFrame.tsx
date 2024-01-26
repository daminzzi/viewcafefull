import React from 'react';
import styled from 'styled-components';
import { white, black } from '../../assets/styles/palettes';

type Props = {
  src: string;
  alt: string;
  $size: string;
};

const FrameDiv = styled.div<{ $size: string }>`
  background-color: ${white};
  display: flex;
  justify-content: center;
  align-items: center;
  border: 2px solid ${black};
  border-radius: 50%;
  overflow: hidden;

  width: ${(props) => props.$size};
  height: ${(props) => props.$size};
`;

const ProfileImg = styled.img<{ $size: string }>`
  max-width: ${(props) => props.$size};
  max-height: ${(props) => props.$size};
`;

function ImageFrame({ src, alt, ...props }: Props) {
  return (
    <FrameDiv {...props}>
      <ProfileImg src={src} alt={alt} {...props} />
    </FrameDiv>
  );
}

export default ImageFrame;
