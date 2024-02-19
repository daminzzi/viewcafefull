import styled from 'styled-components';
import { lightgray } from '../../assets/styles/palettes';

interface InputProps {
  $width?: string;
  $height?: string;
  $textAlign?: string;
  $marginBottom?: string;
  $fontSize?: string;
}

// 공통 인풋
const Input = styled.input<InputProps>`
  display: flex;
  border: 2px solid ${lightgray};
  border-radius: 7px;
  width: ${(props) => props.$width || '200px'};
  height: ${(props) => props.$height || '30px'};
  text-align: ${(props) => props.$textAlign || 'left'};
  margin-bottom: ${(props) => props.$marginBottom || '0px'};
  text-indent: 5px;
  font-size: ${(props) => props.$fontSize || '16px'};
`;

export default Input;
