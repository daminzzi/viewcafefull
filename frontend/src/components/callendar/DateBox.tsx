import React from 'react';
import styled, { css } from 'styled-components';
// import FlexRowContainer from '../common/FlexRowContainer';
import FlexColContainer from '../common/FlexColContainer';
import { white, medium, deep } from '../../assets/styles/palettes';
import DaySpan from './DaySpan';
import useHealthStore, { isSameDate } from '../../stores/HealthStore';
import { ReactComponent as Good } from '../../assets/icons/good.svg'

type Props = {
  date: Date;
  moved: boolean;
};

const Wrapper = styled(FlexColContainer)<{ $isSelected: boolean | null }>`
  cursor: pointer;
  background-color: ${white};
  border-radius: 10px;
  width: 13%;
  height: 60px;
  align-items: center;
  justify-content: space-around;
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

function DateBox({ date, moved }: Props) {
  const { today, selectedDate, setSelectedDate } = useHealthStore();
  const isToday: boolean | null = isSameDate(date, today);
  let isSelected: boolean | null = false;
  isSelected = isSameDate(date, selectedDate);

  return (
    <Wrapper
      $isSelected={isSelected}
      onClick={() => {
        if (!moved) {
          setSelectedDate(date);
        }
      }}
    >
      <DaySpan $day={date?.getDay()} $isToday={isToday}>
        {date?.getDate()}
      </DaySpan>
      <Good width='1.5rem' height='1.5rem' />
    </Wrapper>
  );
}

export default DateBox;
