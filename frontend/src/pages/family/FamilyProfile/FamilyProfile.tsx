import React from 'react';
import { useNavigate } from 'react-router-dom';
import useConnectStore from '../../../stores/ConnectStore';
import useUserStore from '../../../stores/UserStore';
import getConnectInfo from '../../../services/connect/getConnectInfo';
import { Button } from '../../../components/common/Buttons';
import FlexRowContainer from '../../../components/common/FlexRowContainer';
import Accordion from '../../../components/common/Accordion';
import ContentsContainer from '../../../components/common/ContentsContainer';
import Line from '../../../components/common/Line';
import NoProfile from '../../../assets/images/NoProfile.png';
import { ReactComponent as Plus } from '../../../assets/icons/plus.svg';
import { failed, white } from '../../../assets/styles/palettes';
import * as S from './FamilyProfile.styles';

function FamilyProfile() {
  const { currConnect } = useConnectStore();
  const { user, logout } = useUserStore();
  const navigate = useNavigate();

  function handleLogout() {
    logout();
    navigate('/');
  }

  // 입소자 기준으로 연결 정보를 받아옴(아코디언)
  function TarConnectInfoList({ domainId }: { domainId: string }) {
    const connectInfoList = getConnectInfo('tar', domainId);
    const apps = [];

    for (let index = 0; index < connectInfoList.length; index++) {
      const connectInfo = connectInfoList[index];
      apps.push(
        <div key={index}>
          <Line />
          <S.ListContainer>
            <img src={NoProfile} width={'35px'} height={'35px'} />
            <FlexRowContainer
              $justifyContent="stretch"
              $gap="3px"
              $padding="5px 0 5px 0"
            >
              <div>{connectInfo.appName}</div>
              <S.SubTitle>{connectInfo.relationship}</S.SubTitle>
            </FlexRowContainer>
          </S.ListContainer>
        </div>,
      );
    }

    return <div>{apps}</div>;
  }

  if (!user) {
    return <div>Now Loading...</div>;
  }

  return (
    <div>
      <S.ImgContainer>
        <S.ProfileImg src={NoProfile} />
      </S.ImgContainer>
      <S.Title>개인정보</S.Title>
      <ContentsContainer
        $margin="10px"
        $width="auto"
        $alignItems="start"
        $padding="15px"
      >
        <S.Dict>
          <span>이름</span>
          <div>{user.name}</div>
        </S.Dict>
        <S.Dict>
          <span>생년월일</span>
          <div>{user.birth}</div>
        </S.Dict>
        <S.NoLineDict>
          <span>휴대전화</span>
          <div>{user.phoneNumber}</div>
        </S.NoLineDict>
      </ContentsContainer>
      <S.Title>입소자</S.Title>
      <Accordion
        title={currConnect.tarName}
        subTitle={currConnect.perName}
        imgUrl={NoProfile}
        suffix="님"
        content={<TarConnectInfoList domainId={user.id} />}
      />

      <ContentsContainer $margin="10px" $width="auto" $padding="15px">
        <S.PlusContainer onClick={() => navigate('/connect/register')}>
          <span>입소자 추가</span>
          <div>
            <Plus width="20px" />
          </div>
        </S.PlusContainer>
      </ContentsContainer>

      <S.LogoutButtonContainer>
        <Button
          $color={white}
          $bgColor={failed}
          $hoverColor={failed}
          $width="100px"
          $padding="10px"
          $margin="10px"
          onClick={() => handleLogout()}
        >
          로그아웃
        </Button>
      </S.LogoutButtonContainer>
    </div>
  );
}

export default FamilyProfile;
