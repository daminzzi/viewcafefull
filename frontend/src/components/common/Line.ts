import styled, { css } from 'styled-components';
import { gray } from '../../assets/styles/palettes';

type LineProps = {
  $borderColor?: string;
  $margin?: string;
  $width?: string;
  $borderWidth?: string;
  $borderRight?: string;
  $borderLeft?: string;
};
const Line = styled.hr<LineProps>`
  width: ${(props) => props.$width || '100%'};
  border-bottom: 1px solid ${(props) => props.$borderColor || gray};
  margin: ${(props) => props.$margin || '0px 0px 3px 0px'};
  border-width: ${(props) => props.$borderWidth || '1px'};

  ${(props) =>
    props.$borderRight &&
    css`
      border-right: ${props.$borderRight};
    `}

  ${(props) =>
    props.$borderLeft &&
    css`
      border-left: ${props.$borderLeft};
    `}
`;

export default Line;
