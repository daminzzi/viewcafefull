import React, { useState, useEffect } from 'react';
import useHealthStore, { dateToString } from '../../stores/HealthStore';
import ContentsContainer from '../common/ContentsContainer';
import getCondition from '../../services/health/getCondition';
import postCondition from '../../services/health/postCondition';
// import { Button } from '../common/Buttons';
import styled, { css } from 'styled-components';
import Title from '../common/Title';
import FlexRowContainer from '../common/FlexRowContainer';
import { white, deep } from '../../assets/styles/palettes';
import { RoundedButton } from '../../components/common/Buttons';
import { ReactComponent as Good } from '../../assets/icons/good.svg';
import { ReactComponent as Normal } from '../../assets/icons/soso.svg'
import { ReactComponent as Bad } from '../../assets/icons/bad.svg'

const ButtonSpan = styled.span`
  margin: 0 0 0 0.5rem;
`

const TabButton = styled(RoundedButton)<{ $isSelected: boolean }>`
  color: ${deep};
  font-size: 0.9rem;
  width: 4rem;
  height: 1.75rem;
  border-radius: 2rem;
  background-color: ${white};
  box-shadow: 0.1rem 0.1rem 0.2rem ${deep};
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;
  display: flex;
  align-items: center;
  justify-content: center;

  ${(props) =>
    props.$isSelected &&
    css`
      box-shadow: 0 0 0 0.1rem ${deep};
    `}
`;

function parseCondition(condition: string) {
  switch (condition) {
    case '좋음':
      return 'good';
    case '보통':
      return 'normal';
    case '나쁨':
      return 'bad';
    default:
      return null;
  }
}

function isSelected(curr: string | null, state: string) {
  return curr === state;
}

function MainCondition() {
  const { selectedDate,refreshWeek } = useHealthStore();
  const [condition, setCondition] = useState<string | null>(null);

  async function conditionGet() {
    if (selectedDate !== null) {
      const date = dateToString(selectedDate);
      const response = await getCondition(date, date);
      setCondition(parseCondition(response[0].data));
    }
  }

  useEffect(() => {
    conditionGet();
  }, [selectedDate]);

  async function handleClick() {
    if (selectedDate !== null && condition !== null) {
      const date = dateToString(selectedDate);
      await postCondition(date, condition);
      conditionGet();
      refreshWeek();
    }
  }

  return (
    <ContentsContainer>
      <Title icon="condition" buttonContents="저장" handleClick={handleClick}>
        컨디션
      </Title>
      <FlexRowContainer $justifyContent="space-evenly" $margin="0 0 2vh 0">
        <TabButton
          $isSelected={isSelected(condition, 'good')}
          onClick={() => setCondition('good')}
        >
          <Good width="1rem" />
          <ButtonSpan>좋음</ButtonSpan>
        </TabButton>
        <TabButton
          $isSelected={isSelected(condition, 'normal')}
          onClick={() => setCondition('normal')}
        >
          <Normal width="1rem" />
          <ButtonSpan>보통</ButtonSpan>
        </TabButton>
        <TabButton
          $isSelected={isSelected(condition, 'bad')}
          onClick={() => setCondition('bad')}
        >
          <Bad width="1rem" />
          <ButtonSpan>나쁨</ButtonSpan>
        </TabButton>
      </FlexRowContainer>
    </ContentsContainer>
  );
}

export default MainCondition;
