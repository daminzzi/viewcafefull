import React from 'react';
import FlexRowContainer from '../common/FlexRowContainer';
import DaySpan from './DaySpan';

function Days() {
  return (
    <FlexRowContainer $justifyContent='space-around'>
      <DaySpan $day={0}>일</DaySpan>
      <DaySpan $day={1}>월</DaySpan>
      <DaySpan $day={2}>화</DaySpan>
      <DaySpan $day={3}>수</DaySpan>
      <DaySpan $day={4}>목</DaySpan>
      <DaySpan $day={5}>금</DaySpan>
      <DaySpan $day={6}>토</DaySpan>
    </FlexRowContainer>
  );
}

export default Days;
