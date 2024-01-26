import React from 'react';
import Button from '../common/Button';
import DaySpan from './DaySpan';

type Props = {
  date: Date;
  today: Date;
  moved: boolean;
  selectedDate: Date;
  handleChangeSelectedDate: (newDate: Date) => void;
};

export const isSameDate = (date1: Date, date2: Date) => {
  return (
    date1.getFullYear() === date2.getFullYear() &&
    date1.getMonth() === date2.getMonth() &&
    date1.getDate() === date2.getDate()
  );
};

function DateBox({
  date,
  today,
  moved,
  selectedDate,
  handleChangeSelectedDate,
}: Props) {
  const isToday: boolean = isSameDate(date, today);
  const isSelected: boolean = isSameDate(date, selectedDate);

  return (
    <Button
      $isSelected={isSelected}
      onClick={() => {
        if (!moved) {
          handleChangeSelectedDate(date);
        }
      }}
    >
      <DaySpan $day={date.getDay()} $isToday={isToday}>
        {date.getDate()}
      </DaySpan>
    </Button>
  );
}

export default DateBox;
