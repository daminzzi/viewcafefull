import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useConnectStore from '../../stores/ConnectStore';
import useUserStore from '../../stores/userStore';
import getConnectInfo from '../../services/connect/getConnectInfo';
import styled from 'styled-components';

function FamilyProfile() {
  const { currConnect } = useConnectStore();
  const { user, logout } = useUserStore();
  const [isOpen, setIsOpen] = useState(false);
  const navigate = useNavigate();

  const toggleAccordion = () => {
    setIsOpen(!isOpen);
  };

  function handleLogout() {
    logout();
    navigate('/');
  }

  // function handleDelete() {
  //   deleteUser();
  //   navigate('/');
  // }

  // 입소자 기준으로 연결 정보를 받아옴(아코디언)
  function TarConnectInfoList({ domainId }: { domainId: string }) {
    const connectInfoList = getConnectInfo('tar', domainId);
    return (
      <div>
        {connectInfoList.map((connectInfo, index) => (
          <div key={index}>
            <hr />
            <RowContainer>
              <div>{connectInfo.appName}</div>
              <RelationText>{connectInfo.relationship}</RelationText>
            </RowContainer>
          </div>
        ))}
      </div>
    );
  }

  if (!user) {
    return <div>Now Loading...</div>;
  }

  return (
    <div>
      <h2>개인정보</h2>
      <div>이름</div>
      <div>{user.name}</div>
      <div>생년월일</div>
      <div>{user.birth}</div>
      <div>휴대전화</div>
      <div>{user.phoneNumber}</div>
      <br />
      <h2>입소자</h2>
      <Container>
        <PerText>{currConnect.perName}</PerText>
        <div onClick={toggleAccordion}>{currConnect.tarName} 님</div>
        {isOpen && <TarConnectInfoList domainId={user.id} />}
      </Container>
      <br />
      {/* <button type="button" onClick={() => handleDelete()}>
        회원탈퇴
      </button> */}
      <button type="button" onClick={() => handleLogout()}>
        로그아웃
      </button>
    </div>
  );
}

export default FamilyProfile;

const Container = styled.div`
  display: flex;
  position: relative;
  flex-direction: column;
  justify-content: center;
  border-radius: 30px;
  border: 2px solid black;
`;

const RowContainer = styled.div`
  display: flex;
  flex-direction: row;
`;

const RelationText = styled.div`
  font-size: 10px;
  align-self: center;
`;

const PerText = styled.div`
  font-size: 12px;
`;
