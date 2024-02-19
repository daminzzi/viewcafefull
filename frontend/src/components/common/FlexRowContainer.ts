import styled, { css } from 'styled-components';

type Props = {
  $backgroundColor?: string;
  $border?: string;
  $borderRadius?: string;
  $justifyContent?: string;
  $margin?: string;
  $padding?: string;
  $width?: string;
  $height?: string;
  $alignItems?: string;
  $gap?: string;
  $borderColor?: string;
  $position?: string;
  $right?: string;
  $top?: string;
};

const FlexRowContainer = styled.div<Props>`
  width: 100%;
  display: flex;
  justify-content: space-around;
  align-items: center;

  ${(props) =>
    props.$height &&
    css`
      height: ${props.$height};
    `}
  ${(props) =>
    props.$backgroundColor &&
    css`
      background-color: ${props.$backgroundColor};
    `}
  ${(props) =>
    props.$border &&
    css`
      border: ${props.$border};
    `}
  ${(props) =>
    props.$borderRadius &&
    css`
      border-radius: ${props.$borderRadius};
    `}
  ${(props) =>
    props.$justifyContent &&
    css`
      justify-content: ${props.$justifyContent};
    `}
  ${(props) =>
    props.$margin &&
    css`
      margin: ${props.$margin};
    `}
  ${(props) =>
    props.$width &&
    css`
      width: ${props.$width};
    `}
  ${(props) =>
    props.$padding &&
    css`
      padding: ${props.$padding};
    `}
  ${(props) =>
    props.$alignItems &&
    css`
      align-items: ${props.$alignItems};
    `}
  ${(props) =>
    props.$gap &&
    css`
      gap: ${props.$gap};
    `}
  ${(props) =>
    props.$borderColor &&
    css`
      border-color: ${props.$borderColor};
    `}
  ${(props) =>
    props.$position &&
    css`
      position: ${props.$position};
    `}
  ${(props) =>
    props.$right &&
    css`
      right: ${props.$right};
    `}
  ${(props) =>
    props.$top &&
    css`
      top: ${props.$top};
    `}
`;

export default FlexRowContainer;
