import React from 'react';
import Week from './Week';
import Day from './Days';
import YearMonth from './YearMonth';
import { light } from '../../assets/styles/palettes';
import FlexColContainer from '../common/FlexColContainer';

type Props = {
  today: Date;
  selectedDate: Date;
  startOfWeek: Date;
  handleChangeSelectedDate: (newDate: Date) => void;
  handleChangeStart: (newDate: Date) => void;
};

function Callendar({
  today,
  selectedDate,
  startOfWeek,
  handleChangeSelectedDate,
}: Props) {
  const start: Date = new Date(selectedDate);
  start.setDate(start.getDate() - start.getDay());
  const week: Array<Date> = [];

  for (let i = 0; i < 7; i++) {
    const copyStart: Date = new Date(startOfWeek);
    week.push(new Date(copyStart.setDate(copyStart.getDate() + i)));
  }

  function handleChangeWeek(prev: boolean) {
    if (prev) {
      const newDate: Date = new Date(selectedDate);
      newDate.setDate(newDate.getDate() - 7);
      handleChangeSelectedDate(newDate);
    } else {
      const newDate: Date = new Date(selectedDate);
      newDate.setDate(newDate.getDate() + 7);
      handleChangeSelectedDate(newDate);
    }
  }

  return (
    <FlexColContainer $backgroundColor={light}>
      <YearMonth
        selectedDate={selectedDate}
        week={week}
        handleChangeSelectedDate={handleChangeSelectedDate}
      />
      <Day />
      <Week
        week={week}
        handleChangeWeek={handleChangeWeek}
        today={today}
        selectedDate={selectedDate}
        handleChangeSelectedDate={handleChangeSelectedDate}
      />
    </FlexColContainer>
  );
}

export default Callendar;
