import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import useConnectStore from '../../../stores/ConnectStore';
import useUserStore from '../../../stores/UserStore';
import { Button } from '../../../components/common/Buttons';
import FlexRowContainer from '../../../components/common/FlexRowContainer';
import Accordion from '../../../components/common/Accordion';
import ContentsContainer from '../../../components/common/ContentsContainer';
import Line from '../../../components/common/Line';
import NoProfile from '../../../assets/images/noProfile.png';
import { ReactComponent as Plus } from '../../../assets/icons/plus.svg';
import { failed, white } from '../../../assets/styles/palettes';
import * as S from './FamilyProfile.styles';

function FamilyProfile() {
  const { currConnect, connectArr, updateConnect } = useConnectStore();
  const { user } = useUserStore();
  const navigate = useNavigate();

  useEffect(() => {
    updateConnect('tar', currConnect.tarDomainId);
  }, []);

  async function handleLogout() {
    navigate('/logout');
  };

  function TarConnectInfoList() {
    const apps = [];
    for (let index = 0; index < connectArr.length; index++) {
      const connectInfo = connectArr[index];
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
        content={<TarConnectInfoList />}
      />

      <ContentsContainer $margin="10px" $width="auto" $padding="15px">
        <S.PlusContainer onClick={() => navigate('/connect/register')}>
          <span>입소자 추가</span>
            <Plus width="20px" />
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
