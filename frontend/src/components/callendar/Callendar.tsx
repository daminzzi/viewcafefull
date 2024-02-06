import React from 'react';
import Week from './Week';
import Day from './Days';
import YearMonth from './YearMonth';
import { light } from '../../assets/styles/palettes';
import FlexColContainer from '../common/FlexColContainer';

function Callendar() {
  return (
    <FlexColContainer $backgroundColor={light}>
      <YearMonth />
      <Day />
      <Week />
    </FlexColContainer>
  );
}

export default Callendar;
