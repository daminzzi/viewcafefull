import styled, { css } from 'styled-components';

type Props = {
  $backgroundColor?: string;
  $border?: string;
  $borderRadius?: string;
  $justifyContent?: string;
  $margin?: string;
  $padding?: string;
  $width?: string;
  $alignItems?: string;
};

const FlexRowContainer = styled.div<Props>`
  width: 100%;
  display: flex;
  justify-content: space-around;
  align-items: center;

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
`;

export default FlexRowContainer;
