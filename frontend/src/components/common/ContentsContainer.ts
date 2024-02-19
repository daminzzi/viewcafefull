import styled, { css } from 'styled-components';
import { white, main3 } from '../../assets/styles/palettes';
import FlexColContainer from './FlexColContainer';

const ContentsContainer = styled(FlexColContainer)`
  background-color: ${white};
  border: 4px solid ${main3};
  border-radius: 30px;
  margin: 20px 0;
  width: 90%;
  padding: 10px 0;

  ${(props) =>
    props.$padding &&
    css`
      padding: ${props.$padding};
    `}
  ${(props) =>
    props.$width &&
    css`
      width: ${props.$width};
    `}
  ${(props) =>
    props.$margin &&
    css`
      margin: ${props.$margin};
    `}
  ${(props) =>
    props.$alignItems &&
    css`
      margin: ${props.$alignItems};
    `}
`;

export default ContentsContainer;
