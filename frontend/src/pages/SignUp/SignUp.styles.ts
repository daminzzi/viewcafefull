import styled, { createGlobalStyle } from 'styled-components';
import check from '../../assets/images/checkMark.png';

export const GlobalStyle = createGlobalStyle`
  .message {
    padding: 2px 0px;
    font-weight: bold;
    font-size: 12px;
    height: 15px;
    visibility: hidden;
  }
  .message.active {
    visibility: visible;
  }
`;

export const SignUpText = styled.div`
  font-weight: bold;
  font-size: 20px;
  padding-bottom: 20px;
`;

export const IDContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 5px;
`;

export const Label = styled.label`
  > div {
    margin: 8px 0px;
    font-size: 13px;
    font-weight: bold;
  }
`;

export const PhoneContainer = styled.div`
  display: flex;
  text-align: center;
  align-items: center;
`;

export const IdCheckIcon = styled.div`
  position: absolute;
  margin-right: 15px;
  right: 91px;
  top: -194px;
  width: 25px;
  height: 100%;
  background-image: url(${check});
  background-repeat: no-repeat;
  background-position: center;
  background-size: 70%;
  border: none;
  background-color: transparent;
`;

export const PwCheckIcon = styled(IdCheckIcon)`
  right: 18px;
  top: -115px;
`;

export const PwConfirmCheckIcon = styled(IdCheckIcon)`
  right: 18px;
  top: -29px;
`;
