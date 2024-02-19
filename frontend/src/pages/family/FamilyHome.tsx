import React, { useEffect } from 'react';
import styled from 'styled-components';
import ContentsContainer from '../../components/common/ContentsContainer';
import FlexColContainer from '../../components/common/FlexColContainer';
import TabButtonGroup from '../../components/familyhome/TabButtonGroup';
import TabView from '../../components/familyhome/TabView';
import Calendar from '../../components/calendar/Calendar';
import getHealth from '../../services/health/getHealth';
import useHealthStore, {
  initialHealth,
  initialImage,
  dateToString,
} from '../../stores/HealthStore';
import useConnectStore from '../../stores/ConnectStore';
import Line from '../../components/common/Line';

const SubTitle = styled.p`
  font-weight: bold;
  width: 90%;
`;

function processInfo(info: HealthResponse) {
  const newHealth = {
    low: { ...initialHealth },
    high: { ...initialHealth },
    before: { ...initialHealth },
    after: { ...initialHealth },
    medicine: { ...initialHealth },
    meal: { ...initialImage },
  };
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

  const { currConnect } = useConnectStore();

  async function getInfo() {
    if (selectedDate !== null) {
      const date = dateToString(selectedDate);
      const response = await getHealth(currConnect.tarDomainId, date);
      setHealthInfo(processInfo(response));
    }
  }

  useEffect(() => {
    const curr = new Date();
    setToday(curr);
    if (selectedDate === null) {
      setSelectedDate(curr);
    }
  }, []);

  // date 변경 시 건강 정보 요청, 변경
  useEffect(() => {
    if (selectedDate && currConnect.tarDomainId) {
      getInfo();
    }
  }, [selectedDate, currConnect]);

  return (
    <FlexColContainer>
      <Calendar />
      <ContentsContainer>
        <SubTitle>하루 건강 정보</SubTitle>
        <TabButtonGroup />
        <Line $margin="10px 0 0 0" $width="90%" $borderColor="none" />
        <TabView />
      </ContentsContainer>
    </FlexColContainer>
  );
}

export default FamilyHome;
