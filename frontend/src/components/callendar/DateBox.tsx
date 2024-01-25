import React from 'react';
import styled, { css } from 'styled-components';
import { white, medium, deep } from '../../assets/styles/palettes';
import FlexRowContainer from '../common/FlexRowContainer';
import DaySpan from './DaySpan';

type Props = {
  date: Date;
  today: Date;
  moved: boolean;
  selectedDate: Date;
  handleChangeSelectedDate: (newDate: Date) => void;
};

const DateContainer = styled(FlexRowContainer)<{ $isSelected: boolean }>`
  cursor: pointer;
  background-color: ${white};
  border-radius: 10px;
  width: 13%;
  height: 50px;
  align-items: center;
  justify-content: center;
  padding-top: 0.2%;
  padding-bottom: 0.2%;
  box-shadow: 2px 2px 4px ${medium};
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;

  ${(props) =>
    props.$isSelected &&
    css`
      box-shadow: 0 0 0 2px ${deep};
    `}
`;

const isSameDate = (date1: Date, date2: Date) => {
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
    <DateContainer
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
    </DateContainer>
  );
}

export default DateBox;
