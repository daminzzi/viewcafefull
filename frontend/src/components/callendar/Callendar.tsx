import React, { useState } from 'react';
import Week from './Week';
import Day from './Days';
import { light } from '../../assets/styles/palettes';
import FlexColContainer from '../common/FlexColContainer';

type Props = {
  today: Date;
  selectedDate: Date;
  handleChangeSelectedDate: (newDate: Date) => void;
};

function Callendar({ today, selectedDate, handleChangeSelectedDate }: Props) {
  const start: Date = new Date(selectedDate);
  start.setDate(start.getDate() - start.getDay());
  const week: Array<Date> = [];
  const [startOfWeek, setStartOfWeek] = useState<Date>(start);

  for (let i = 0; i < 7; i++) {
    const copyStart: Date = new Date(startOfWeek);
    week.push(new Date(copyStart.setDate(copyStart.getDate() + i)));
  }

  function handleChangeWeek(prev: boolean) {
    if (prev) {
      const newStart: Date = new Date(startOfWeek);
      newStart.setDate(newStart.getDate() - 7);
      setStartOfWeek(newStart);
    } else {
      const newStart: Date = new Date(startOfWeek);
      newStart.setDate(newStart.getDate() + 7);
      setStartOfWeek(newStart);
    }
  }

  return (
    <FlexColContainer $backgroundColor={light}>
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
