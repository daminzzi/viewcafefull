import styled, { css } from 'styled-components';
import {
  failed,
  blue,
  black,
  white,
  medium,
} from '../../assets/styles/palettes';

const DaySpan = styled.span<{ $day?: number; $isToday?: boolean }>`
  width: 25px;
  height: 25px;
  text-align: center;
  border-radius: 20%;
  color: ${(props) => {
    if (props.$day === 0) {
      return failed;
    } else if (props.$day === 6) {
      return blue;
    } else {
      return black;
    }
  }};

  ${(props) =>
    props.$isToday &&
    css`
      color: ${white};
      background-color: ${medium};
    `}
`;

export default DaySpan;
