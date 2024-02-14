import React, { useEffect } from 'react';
import FlexColContainer from '../../components/common/FlexColContainer';
import TodayConference from '../../components/caregiver/TodayConference';
import Calendar from '../../components/calendar/Calendar';
import useHealthStore from '../../stores/HealthStore';
import MainCondition from '../../components/caregiver/MainCondition';
import MainMeal from '../../components/caregiver/MainMeal';

function CareGiverHome() {
  const { selectedDate, setSelectedDate, setToday } = useHealthStore();

  useEffect(() => {
    const curr = new Date();
    setToday(curr);
    if (selectedDate === null) {
      setSelectedDate(curr);
    }
  }, []);

  return (
    <FlexColContainer>
      <Calendar />
      <TodayConference />
      <MainMeal />
      <MainCondition />
    </FlexColContainer>
  );
}

export default CareGiverHome;
