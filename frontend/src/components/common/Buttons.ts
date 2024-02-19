import styled from 'styled-components';
import {
  gray,
  lightgray,
  main3,
  black,
  white,
} from '../../assets/styles/palettes';

interface ButtonProps {
  $color?: string;
  $width?: string;
  $height?: string;
  $fontSize?: string;
  $bgColor?: string;
  $margin?: string;
  $padding?: string;
  $hoverColor?: string;
  $borderRadius?: string;
  $border?: string;
}

// 공통 버튼
const Button = styled.button<ButtonProps>`
  border: none;
  font-weight: bold;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  color: ${(props) => props.$color || white};
  width: ${(props) => props.$width || 'auto'};
  height: ${(props) => props.$height || 'auto'};
  padding: ${(props) => props.$padding || '4%'};
  font-size: ${(props) => props.$fontSize || '15px'};
  background-color: ${(props) => props.$bgColor || main3};
  border-radius: ${(props) => props.$borderRadius || '6px'};
  border: ${(props) => props.$border || 'none'};
  margin: ${(props) => props.$margin || '0px'};
`;

// border 버튼(컨디션, 식사 버튼에 사용)
// -> 활성화시 background 색만 border 색으로 바뀌도록
const OutlineButton = styled(Button)`
  background: ${(props) => props.$bgColor || 'transparent'};
  border: 2px solid ${main3};
  color: ${black};
`;

// 비활성화 버튼
const DisabledButton = styled(Button)`
  background: ${lightgray};
  color: ${gray};
  cursor: not-allowed;
`;

const RoundedButton = styled(Button)`
  color: ${(props) => props.$color || white};
  padding: ${(props) => props.$padding || '2px'};
  border-radius: ${(props) => props.$height || '10px'};
`;

export { Button, OutlineButton, DisabledButton, RoundedButton };
