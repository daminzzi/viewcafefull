import styled from 'styled-components';
import { lightgray } from '../../assets/styles/palettes';

interface TextAreaProps {
  $width?: string;
  $height?: string;
  $textAlign?: string;
  $marginBottom?: string;
}

const TextArea = styled.textarea<TextAreaProps>`
  display: flex;
  border: 2px solid ${lightgray};
  border-radius: 7px;
  width: ${(props) => props.$width || '200px'};
  height: ${(props) => props.$height || '30px'};
  text-align: ${(props) => props.$textAlign || 'left'};
  margin-bottom: ${(props) => props.$marginBottom || '0px'};

  @media (max-height: 500px) {
    height: 25vh;
  }
`;

export default TextArea;
