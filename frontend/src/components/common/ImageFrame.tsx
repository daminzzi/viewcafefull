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
  box-shadow: 0 0 0 1px ${black};
  border-radius: 8px;
  overflow: hidden;

  width: ${(props) => props.$size};
  aspect-ratio: 1 / 1;
`;

const InnerImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

function ImageFrame({ src, alt, ...props }: Props) {
  return (
    <Wrapper {...props}>
      <InnerImage src={src} alt={alt} />
    </Wrapper>
  );
}

export default ImageFrame;
