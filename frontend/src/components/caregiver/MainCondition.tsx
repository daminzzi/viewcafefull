import React, { useState, useEffect } from 'react';
import useHealthStore, { dateToString } from '../../stores/HealthStore';
import ContentsContainer from '../common/ContentsContainer';
import getCondition from '../../services/health/getCondition';
import postCondition from '../../services/health/postCondition';
import { Button } from '../common/Buttons';

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

function MainCondition() {
  const { selectedDate } = useHealthStore();
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
    }
  }

  return (
    <ContentsContainer>
      <p>컨디션</p>
      <Button
        onClick={() => {
          handleClick();
        }}
      >
        저장
      </Button>
      <button onClick={() => {setCondition('good')}}>좋음</button>
      <button onClick={() => {setCondition('normal')}}>보통</button>
      <button onClick={() => {setCondition('bad')}}>나쁨</button>
    </ContentsContainer>
  );
}

export default MainCondition;
