import React, { ChangeEvent, useState } from 'react';
import postConnection from '../../services/connect/postConnection';
import useUserStore from '../../stores/UserStore';
import { useNavigate } from 'react-router-dom';
import UserContainer from '../../components/common/UserContainer';
import styled from 'styled-components';
import { lightgray } from '../../assets/styles/palettes';
import Input from '../../components/common/Input';
import { Button } from '../../components/common/Buttons';
import FlexRowContainer from '../../components/common/FlexRowContainer';
import useConnectStore from '../../stores/ConnectStore';

function ConnectRegister() {
  const navigate = useNavigate();
  const { user } = useUserStore();
  const { updateConnect } = useConnectStore();
  const [form, setForm] = useState({
    nursingHome: '',
    targetCode: '',
    targetName: '',
    relationship: '아들',
  });
  const [otherRelationship, setOtherRelationship] = useState<string>('');
  function handleChange(e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) {
    const { name, value } = e.target;
    setForm((prevForm) => ({
      ...prevForm,
      [name]: value,
    }));
  }

  async function handleSubmit() {
    // 로그인이 되어있지 않은 상태
    if (user === null) {
      // console.log('로그인이 필요합니다.', form, otherRelationship);
      navigate('/family/');
      return;
    }

    const body = {
      domainId: user.id,
      nursingHome: form.nursingHome,
      targetCode: form.targetCode,
      targetName: form.targetName,
      relationship: form.relationship,
    };
    // otherRelationship의 값이 존재하면 relationship에 할당
    if (body.relationship === 'etc' && otherRelationship) {
      body.relationship = otherRelationship;
    }

    await postConnection(body);
    // console.log(body);
    await updateConnect('app', user.id);
    navigate('/family');
  }

  return (
    <UserContainer
      $height="420px"
      $width="300px"
      $padding="12px"
      $alignItems="left"
      $justifyContent="start"
    >
      <FlexRowContainer
        $justifyContent="space-between"
        $padding="10px"
        $width="auto"
      >
        <Title>입소자 연결 신청</Title>
        <Button
          $width="22%"
          $padding="8px"
          type="button"
          onClick={handleSubmit}
        >
          신청
        </Button>
      </FlexRowContainer>

      <Label>
        <div>요양원명</div>
        <Input
          $width="98%"
          type="text"
          name="nursingHome"
          placeholder="요양원명을 입력해주세요."
          value={form.nursingHome}
          onChange={handleChange}
        />
      </Label>
      <Label>
        <div>입소자코드</div>
        <Input
          $width="98%"
          name="targetCode"
          placeholder="입소자코드를 입력해주세요."
          value={form.targetCode}
          onChange={handleChange}
        />
      </Label>
      <Label>
        <div>입소자명</div>
        <Input
          $width="98%"
          type="text"
          name="targetName"
          placeholder="입소자명을 입력해주세요."
          value={form.targetName}
          onChange={handleChange}
        />
      </Label>
      <Label>
        <div>입소자와의 관계</div>
        <Select
          name="relationship"
          value={form.relationship}
          onChange={handleChange}
        >
          <option value="아들">아들</option>
          <option value="딸">딸</option>
          <option value="손자">손자</option>
          <option value="손녀">손녀</option>
          <option value="etc">기타</option>
        </Select>
      </Label>
      {form.relationship === 'etc' && (
        <Label>
          <div>기타 관계</div>
          <Input
            $width="98%"
            type="text"
            name="otherRelationship"
            placeholder="관계를 입력해주세요."
            value={otherRelationship}
            onChange={(e) => setOtherRelationship(e.target.value)}
          />
        </Label>
      )}
    </UserContainer>
  );
}

export default ConnectRegister;

const Title = styled.div`
  font-weight: bold;
  font-size: 20px;
  padding-bottom: 10px;
`;

const Label = styled.label`
  > div {
    margin: 8px 0px;
    font-size: 13px;
    font-weight: bold;
  }
`;

const Select = styled.select`
  width: 100%;
  display: flex;
  border: 2px solid ${lightgray};
  border-radius: 7px;
  height: 30px;
  text-align: 'left';
  margin-bottom: '0px';
  text-indent: 5px;
`;
