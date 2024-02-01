import styled from 'styled-components';
import checkMark from '../../assets/images/check-mark.png';
import { white, gray } from '../../assets/styles/palettes';

export const HiddenCheckBox = styled.input.attrs({ type: 'checkbox' })`
  display: none;
  &:checked + label:after {
    transform: scale(1);
  }
`;

export const ShowCheckBox = styled.label`
  position: relative;
  padding-left: 30px;
  cursor: pointer;
  &:before {
    content: '';
    position: absolute;
    left: 0;
    top: -3px;
    width: 20px;
    height: 20px;
    border: 1px solid ${gray};
    background: ${white};
    border-radius: 3px;
  }
  &:after {
    content: '';
    position: absolute;
    left: 5px;
    top: 2px;
    width: 12px;
    height: 12px;
    border-radius: 3;
    background: url(${checkMark}) center/cover no-repeat;
    transform: scale(0);
    transition: all 0.2s ease;
  }
`;
