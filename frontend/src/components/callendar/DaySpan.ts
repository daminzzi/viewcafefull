import styled, { css } from 'styled-components';
import {
  failed,
  blue,
  black,
  white,
  medium,
} from '../../assets/styles/palettes';

const DaySpan = styled.span<{
  $day?: number | null;
  $isToday?: boolean | null;
}>`
  width: 1.5rem;
  height: 1.35rem;
  padding-top: 0.15rem;
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
