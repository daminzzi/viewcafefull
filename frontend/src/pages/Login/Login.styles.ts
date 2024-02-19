import styled from 'styled-components';
import { gray } from '../../assets/styles/palettes';


export const RowContainer = styled.div`
  display: flex;
  margin: 0px 20px;
  justify-content: space-around;
  cursor: pointer;
  font-size: 13px;
  color: ${gray};
  margin-top: 10px;
`;

export const Line = styled.div`
  flex: 1;
  border-top: 1px solid ${gray};
  margin: 0 10px;
`;

export const RowContainerWithLine = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  margin: 20px 0;
  color: ${gray};
`;

export const LoginText = styled.div`
  align-self: center;
  font-weight: bold;
  font-size: 30px;
  padding-bottom: 20px;
`;

export const Label = styled.label`
  > div {
    margin-bottom: 5px;
    font-size: 13px;
    font-weight: bold;
  }
`;

export const Form = styled.form`
  padding: 13px;
`;
