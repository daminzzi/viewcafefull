import React from 'react';
import styled from 'styled-components';
import DaySpan from './DaySpan';

const DayContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-around;
`;

function Days() {
  return (
    <DayContainer>
      <DaySpan $day={0}>일</DaySpan>
      <DaySpan $day={1}>월</DaySpan>
      <DaySpan $day={2}>화</DaySpan>
      <DaySpan $day={3}>수</DaySpan>
      <DaySpan $day={4}>목</DaySpan>
      <DaySpan $day={5}>금</DaySpan>
      <DaySpan $day={6}>토</DaySpan>
    </DayContainer>
  );
}

export default Days;
