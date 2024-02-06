import styled from 'styled-components';
import checkMark from '../../assets/images/check-mark.png';
import { white, gray } from '../../assets/styles/palettes';

export const HiddenCheckBox = styled.input.attrs({ type: 'checkbox' })`
  display: none;
  &:checked + label:after {
    transform: scale(1);
  }
`;
export const ShowCheckBox = styled.label<{ isChecked: boolean }>`
  position: relative;
  cursor: pointer;
  &:before {
    content: '';
    position: absolute;
    left: 0;
    top: 0;
    width: 20px;
    height: 20px;
    border: 1px solid ${gray};
    background: ${white};
    border-radius: 3px;
  }
  &:after {
    content: '';
    position: absolute;
    left: 4px;
    top: 4px;
    width: 12px;
    height: 12px;
    border-radius: 3;
    background: url(${checkMark}) center/cover no-repeat;
    transform: ${(props) => (props.isChecked ? 'scale(1)' : 'scale(0)')};
    transition: all 0.2s ease;
  }
`;
