import React from 'react';
import styled, { css } from 'styled-components';
import { white, black } from '../../assets/styles/palettes';

type Props = {
  src: string;
  alt: string;
  handleClick?: (image: string) => void;
  image?: string;
  $size: string;
  $cursor?: string;
  $aspectRatio?: string;
};

export const Wrapper = styled.div<{
  $size: string;
  $cursor?: string;
  $aspectRatio?: string;
}>`
  background-color: ${white};
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0 0 1px ${black};
  border-radius: 8px;
  overflow: hidden;

  width: ${(props) => props.$size};
  aspect-ratio: ${(props) => props.$aspectRatio || 1 / 1};

  ${(props) =>
    props.$cursor &&
    css`
      cursor: ${props.$cursor};
    `}
`;

const InnerImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

function ImageFrame({ src, alt, image, handleClick, ...props }: Props) {
  return (
    <Wrapper {...props}>
      <InnerImage
        src={src}
        alt={alt}
        onClick={() => {
          if (handleClick && image) {
            handleClick(image);
          }
        }}
      />
    </Wrapper>
  );
}

export default ImageFrame;
