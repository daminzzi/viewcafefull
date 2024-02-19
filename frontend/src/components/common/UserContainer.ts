import styled from 'styled-components';
import { white, main3 } from '../../assets/styles/palettes';

interface ContainerProps {
  $width?: string;
  $height?: string;
  $padding?: string;
  $margin?: string;
  $flexDirection?: string;
  $alignItems?: string;
  $justifyContent?: string;
}

// 회원가입&로그인 전용 컨테이너
const UserContainer = styled.div<ContainerProps>`
  display: flex;
  margin: ${(props) => props.$margin || '0'};
  padding: ${(props) => props.$padding || '0'};
  flex-direction: ${(props) => props.$flexDirection || 'column'};
  align-items: ${(props) => props.$alignItems || 'center'};
  justify-content: ${(props) => props.$justifyContent || 'center'};
  width: ${(props) => props.$width || '300px'};
  height: ${(props) => props.$height || '500px'};
  background-color: ${white};
  border: 4px solid ${main3};
  border-radius: 30px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
`;

export default UserContainer;
