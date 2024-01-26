import React from 'react';
import FlexRowContainer from '../common/FlexRowContainer';
import styled from 'styled-components';
import { ReactComponent as ChevronRight } from '../../assets/icons/chevron-right.svg';
import { ReactComponent as ChevronLeft } from '../../assets/icons/chevron-left.svg';
import { isSameDate } from './DateBox';

type Props = {
  selectedDate: Date;
  week: Array<Date>;
  handleChangeSelectedDate: (newDate: Date) => void;
};

const Span = styled.span`
  font-size: 20pt;
`;

const ChevronButton = styled.button`
  cursor: pointer;
  border: none;
  background: none;
`;

function YearMonth({ selectedDate, week, handleChangeSelectedDate }: Props) {
  let res = false;
  for (let i = 0; i < 7; i++) {
    if (isSameDate(week[i], selectedDate)) {
      res = true;
      break;
    }
  }

  function handleChangeMonth(prev: boolean) {
    if (prev) {
      const newDate = new Date(
        `${selectedDate.getMonth() === 0 ? selectedDate.getFullYear() - 1 : selectedDate.getFullYear()}-
        ${selectedDate.getMonth() === 0 ? 12 : selectedDate.getMonth()}-
        ${selectedDate.getDate()}`,
      );
      handleChangeSelectedDate(newDate);
    } else {
      const newDate = new Date(
        `${selectedDate.getMonth() === 11 ? selectedDate.getFullYear() + 1 : selectedDate.getFullYear()}-
        ${selectedDate.getMonth() === 11 ? 1 : selectedDate.getMonth() + 2}-
        ${selectedDate.getDate()}`,
      );
      handleChangeSelectedDate(newDate);
    }
  }

  return (
    <FlexRowContainer
      $justifyContent="space-between"
      $width="90%"
      $margin="10px 0"
    >
      <ChevronButton onClick={() => handleChangeMonth(true)}>
        <ChevronLeft />
      </ChevronButton>
      <Span>
        {`${res ? selectedDate.getFullYear() : week[0].getFullYear()}.${res ? selectedDate.getMonth() + 1 : week[0].getMonth() + 1}`}
      </Span>
      <ChevronButton onClick={() => handleChangeMonth(false)}>
        <ChevronRight />
      </ChevronButton>
    </FlexRowContainer>
  );
}

export default YearMonth;
