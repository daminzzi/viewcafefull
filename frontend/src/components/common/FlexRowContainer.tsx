import styled, { css } from 'styled-components';

type Props = {
  $backgroundColor?: string;
  $border?: string;
  $borderRadius?: string;
};

const FlexRowContainer = styled.div<Props>`
  width: 100%;
  display: flex;
  justify-content: space-around;
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
`;

export default FlexRowContainer;
