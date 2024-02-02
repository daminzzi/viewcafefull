import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import ContentsContainer from '../../components/common/ContentsContainer';
import FlexColContainer from '../../components/common/FlexColContainer';
import TabButtonGroup from '../../components/familyhome/TabButtonGroup';
import TabView from '../../components/familyhome/TabView';
import Callendar from '../../components/callendar/Callendar';
import getHealth from '../../services/health/getHealth';
import noImage from '../../assets/images/noImage.jpg';
import breakfast from '../../assets/images/breakfast.jpg';
import lunch from '../../assets/images/lunch.jpg';
import dinner from '../../assets/images/dinner.jpg';

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

function FamilyHome() {
  // const today: Date = new Date();

  // 더미 데이터 날짜로 설정
  const today: Date = new Date('2024-01-15');

  // State
  const [tab, setTab] = useState<Page>('sum');
  const [selectedDate, setSelectedDate] = useState<Date>(today);
  const [healthInfo, setHealthInfo] = useState<HealthInfo>({
    beforeArr: [],
    afterArr: [],
    lowArr: [],
    highArr: [],
    medicineObj: {
      breakfast: null,
      lunch: null,
      dinner: null,
    },
    mealObj: {
      breakfast: noImage,
      lunch: noImage,
      dinner: noImage,
    },
  });
  const start: Date = new Date(selectedDate);
  start.setDate(start.getDate() - start.getDay());
  const [startOfWeek, setStartOfWeek] = useState<Date>(start);

  // useEffect
  // 날짜 변경시 주의 시작 일 변경
  useEffect(() => {
    const newStart: Date = new Date(selectedDate);
    newStart.setDate(newStart.getDate() - newStart.getDay());
    setStartOfWeek(newStart);
  }, [selectedDate]);

  // date 변경 시 건강 정보 요청, 변경
  useEffect(() => {
    const date = dateToString(selectedDate);
    const health = getHealth('asdfsa', date);
    const before: Array<number> = [...health.before];
    const after: Array<number> = [...health.after];
    const low: Array<number> = [...health.low];
    const high: Array<number> = [...health.high];
    const medicine: MedicineObj = {
      breakfast: null,
      lunch: null,
      dinner: null,
    };
    const meal: MealObj = {
      breakfast: noImage,
      lunch: noImage,
      dinner: noImage,
    };

    health.medicine.forEach((item) => {
      switch (item.medicineType) {
        case 'B':
          medicine.breakfast = item;
          break;
        case 'L':
          medicine.lunch = item;
          break;
        case 'D':
          medicine.dinner = item;
          break;
        default:
          break;
      }
    });

    health.meal.forEach((item) => {
      switch (item.type) {
        case 'B':
          meal.breakfast = breakfast;
          break;
        case 'L':
          meal.lunch = lunch;
          break;
        case 'D':
          meal.dinner = dinner;
          break;
        default:
          break;
      }
    });

    setHealthInfo({
      beforeArr: before,
      afterArr: after,
      lowArr: low,
      highArr: high,
      medicineObj: medicine,
      mealObj: meal,
    });
  }, [selectedDate]);

  // handle function
  function handleChangeSelectedDate(newDate: Date): void {
    setSelectedDate(newDate);
  }

  function handleChangeTab(tabParam: Page): void {
    setTab(tabParam);
  }

  function handleChangeStart(newStart: Date): void {
    setStartOfWeek(newStart);
  }

  return (
    <FlexColContainer>
      <Callendar
        today={today}
        selectedDate={selectedDate}
        startOfWeek={startOfWeek}
        handleChangeSelectedDate={handleChangeSelectedDate}
        handleChangeStart={handleChangeStart}
      />
      <ContentsContainer>
        <SubTitle>하루 건강 정보</SubTitle>
        <TabButtonGroup tab={tab} handleChangeTab={handleChangeTab} />
        <TabView tab={tab} healthInfo={healthInfo} />
      </ContentsContainer>
    </FlexColContainer>
  );
}

export default FamilyHome;
