import styled, { css } from 'styled-components';
import FlexRowContainer from './FlexRowContainer';
import { white, medium, deep } from '../../assets/styles/palettes';

const Button = styled(FlexRowContainer)<{ $isSelected: boolean }>`
  cursor: pointer;
  background-color: ${white};
  border-radius: 10px;
  width: 13%;
  height: 60px;
  align-items: center;
  justify-content: center;
  padding-top: 0.2%;
  padding-bottom: 0.2%;
  box-shadow: 2px 2px 4px ${medium};
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;

  ${(props) =>
    props.$isSelected &&
    css`
      box-shadow: 0 0 0 2px ${deep};
    `}
`;

export default Button;
