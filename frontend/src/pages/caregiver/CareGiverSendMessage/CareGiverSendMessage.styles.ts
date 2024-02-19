import styled from 'styled-components';
import { Button } from '../../../components/common/Buttons';
import { main3, success, white } from '../../../assets/styles/palettes';

export const SendButton = styled(Button)`
  border-radius: 50px;
  width: 60px;
  padding: 10px;
  color: ${white};
  background-color: ${success};
  position: absolute;
  top: -11%;
  right: 2%;
`;

export const Label = styled.label`
  margin-bottom: 5px;
  font-size: 13px;
  font-weight: bold;
`;

export const ParentContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80vh;
`;

export const MainContainer = styled.div`
  padding: 10px;
  width: 280px;
  height: 70vh;
  border-radius: 30px;
  background-color: ${white};
  border: 4px solid ${main3};
`;

export const SubContainer = styled.div`
  position: relative;
  padding: 10px;
  align-items: center;
  width: auto;
`;
