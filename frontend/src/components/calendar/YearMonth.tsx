import React from 'react';
import FlexRowContainer from '../common/FlexRowContainer';
import styled from 'styled-components';
import { ReactComponent as ChevronRight } from '../../assets/icons/chevronRight.svg';
import { ReactComponent as ChevronLeft } from '../../assets/icons/chevronLeft.svg';
import useHealthStore, { isSameDate } from '../../stores/HealthStore';

const Span = styled.span`
  font-size: 1.5rem;
`;

const ChevronButton = styled.button`
  cursor: pointer;
  border: none;
  background: none;
`;

function YearMonth() {
  const { week, selectedDate, setSelectedDate } = useHealthStore();
  let res = false;
  for (let i = 0; i < 7; i++) {
    if (isSameDate(week[i]?.date, selectedDate)) {
      res = true;
      break;
    }
  }

  function handleChangeMonth(prev: boolean) {
    if (selectedDate === null) {
      return null;
    }

    if (prev === true) {
      const newDate = new Date(
        `${selectedDate.getMonth() === 0 ? selectedDate.getFullYear() - 1 : selectedDate.getFullYear()}-
        ${selectedDate.getMonth() === 0 ? 12 : selectedDate.getMonth()}-
        ${selectedDate.getDate()}`,
      );
      setSelectedDate(newDate);
    } else if (prev === false) {
      const newDate = new Date(
        `${selectedDate.getMonth() === 11 ? selectedDate.getFullYear() + 1 : selectedDate.getFullYear()}-
        ${selectedDate.getMonth() === 11 ? 1 : selectedDate.getMonth() + 2}-
        ${selectedDate.getDate()}`,
      );
      setSelectedDate(newDate);
    }
  }

  return (
    <FlexRowContainer
      $justifyContent="space-between"
      $width="90%"
      $margin="10px 0"
    >
      <ChevronButton onClick={() => handleChangeMonth(true)}>
        <ChevronLeft width="1.5rem" />
      </ChevronButton>
      {selectedDate && (
        <Span>
          {`${res ? selectedDate.getFullYear() : week[0]?.date.getFullYear()}.${res ? selectedDate?.getMonth() + 1 : week[0]?.date.getMonth() + 1}`}
        </Span>
      )}
      <ChevronButton onClick={() => handleChangeMonth(false)}>
        <ChevronRight width="1.5rem" />
      </ChevronButton>
    </FlexRowContainer>
  );
}

export default YearMonth;
