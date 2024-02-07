import styled from 'styled-components';
import { gray } from '../../assets/styles/palettes';

type LineProps = {
  $borderColor?: string;
  $margin?: string;
};
const Line = styled.hr<LineProps>`
  width: 100%;
  border-bottom: 1px solid ${(props) => props.$borderColor || gray};
  margin: ${(props) => props.$margin || '0px 0px 3px 0px'};
`;

export default Line;
