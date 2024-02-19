import styled from 'styled-components';
import checkMark from '../../assets/images/checkMark.png';
import { white, gray } from '../../assets/styles/palettes';

export const HiddenCheckBox = styled.input.attrs({ type: 'checkbox' })`
  display: none;
  &:checked + label:after {
    transform: scale(1);
  }
`;
export const ShowCheckBox = styled.label<{ checked: boolean }>`
  position: relative;
  cursor: pointer;
  width: 20px;
  height: 20px;
  &:before {
    content: '';
    position: absolute;
    width: 20px;
    height: 20px;
    border: 1px solid ${gray};
    background: ${white};
    border-radius: 3px;
  }
  &:after {
    content: '';
    position: absolute;
    left: 3px;
    top: 3px;
    width: 15px;
    height: 15px;
    border-radius: 3px;
    background: url(${checkMark}) center/cover no-repeat;
    transform: ${(props) => (props.checked ? 'scale(1)' : 'scale(0)')};
    transition: all 0.2s ease;
  }
`;
