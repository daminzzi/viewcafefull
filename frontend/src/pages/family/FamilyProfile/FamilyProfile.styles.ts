import styled from 'styled-components';
import { gray } from '../../../assets/styles/palettes';

export const ListContainer = styled.div`
  padding: 8px 15px 15px 20px;
  display: flex;
  gap: 7px;
`;

export const LogoutButtonContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  padding-bottom: 18%;
`;

export const SubTitle = styled.div`
  font-size: 10px;
  align-self: center;
`;

export const Title = styled.div`
  font-size: 20px;
  padding-left: 20px;
  margin: 5px;
`;

export const Dict = styled.div`
  display: flex;
  padding: 6px;
  border-bottom: 1px solid ${gray};
  width: 99%;

  @media (max-width: 600px) {
    width: 97%;
  }
  span {
    display: flex;
    justify-content: flex-end;
    width: 70px;
  }
  div {
    margin-left: 15px;
  }
`;

export const NoLineDict = styled.div`
  display: flex;
  padding: 6px;
  span {
    display: flex;
    justify-content: flex-end;
    width: 70px;
  }
  div {
    margin-left: 15px;
  }
`;

export const ProfileImg = styled.img`
  width: 100px;
  margin: 20px;
`;

export const ImgContainer = styled.div`
  display: flex;
  justify-content: center;
`;

export const PlusContainer = styled.div`
  display: flex;
  gap: 3px;
  color: ${gray};
  cursor: pointer;
`;
