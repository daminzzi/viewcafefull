import React, { useEffect } from 'react';
import styled from 'styled-components';
import ContentsContainer from '../../components/common/ContentsContainer';
import FlexColContainer from '../../components/common/FlexColContainer';
import TabButtonGroup from '../../components/familyhome/TabButtonGroup';
import TabView from '../../components/familyhome/TabView';
import Callendar from '../../components/callendar/Callendar';
import getHealth from '../../services/health/getHealth';
import useHealthStore, { initialState } from '../../stores/HealthStore';

const SubTitle = styled.p`
  font-weight: bold;
  width: 90%;
`;

export function dateToString(date: Date): string {
  const year: number = date.getFullYear();
  const month: string = (date.getMonth() + 1).toString().padStart(2, '0');
  const day: string = date.getDate().toString().padStart(2, '0');

  return `${year}${month}${day}`;
}

function processInfo(info: HealthResponse) {
  const newHealth = { ...initialState.healthInfo };

  newHealth.medicine = { ...info.medicine };

  info.health.forEach((value) => {
    switch (value.healthType) {
      case 'L':
        value.data.forEach((data: HealthData) => {
          switch (data.time) {
            case '아침':
              newHealth.low.morning = Number(data.level);
              break;
            case '점심':
              newHealth.low.noon = Number(data.level);
              break;
            case '저녁':
              newHealth.low.dinner = Number(data.level);
              break;
            default:
              break;
          }
        });
        break;
      case 'H':
        value.data.forEach((data: HealthData) => {
          switch (data.time) {
            case '아침':
              newHealth.high.morning = Number(data.level);
              break;
            case '점심':
              newHealth.high.noon = Number(data.level);
              break;
            case '저녁':
              newHealth.high.dinner = Number(data.level);
              break;
            default:
              break;
          }
        });
        break;
      case 'B':
        value.data.forEach((data: HealthData) => {
          switch (data.time) {
            case '아침':
              newHealth.before.morning = Number(data.level);
              break;
            case '점심':
              newHealth.before.noon = Number(data.level);
              break;
            case '저녁':
              newHealth.before.dinner = Number(data.level);
              break;
            default:
              break;
          }
        });
        break;
      case 'A':
        value.data.forEach((data: HealthData) => {
          switch (data.time) {
            case '아침':
              newHealth.after.morning = Number(data.level);
              break;
            case '점심':
              newHealth.after.noon = Number(data.level);
              break;
            case '저녁':
              newHealth.after.dinner = Number(data.level);
              break;
            default:
              break;
          }
        });
        break;
      default:
        break;
    }
  });

  info.meal.images.forEach((value: ImagesData) => {
    switch (value.time) {
      case '아침':
        newHealth.meal.morning = value.url;
        break;
      case '점심':
        newHealth.meal.noon = value.url;
        break;
      case '저녁':
        newHealth.meal.dinner = value.url;
        break;
      default:
        break;
    }
  });

  return newHealth;
}

function FamilyHome() {
  const { selectedDate, setSelectedDate, setToday, setHealthInfo } =
    useHealthStore();

  function getInfo() {
    const response = getHealth('asdf', '20240116');
    setHealthInfo(processInfo(response));
  }

  useEffect(() => {
    setToday(new Date());
    if (selectedDate === null) {
      setSelectedDate(new Date());
    }
  }, []);

  // date 변경 시 건강 정보 요청, 변경
  useEffect(() => {
    getInfo();
  }, [selectedDate]);

  return (
    <FlexColContainer>
      <Callendar />
      <ContentsContainer>
        <SubTitle>하루 건강 정보</SubTitle>
        <TabButtonGroup />
        <TabView />
      </ContentsContainer>
    </FlexColContainer>
  );
}

export default FamilyHome;
