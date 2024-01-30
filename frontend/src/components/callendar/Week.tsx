import React, { useState } from 'react';
// import React from 'react';
import DateBox from './DateBox';
import FlexRowContainer from '../common/FlexRowContainer';

type Props = {
  week: Array<Date>;
  today: Date;
  selectedDate: Date;
  handleChangeWeek: (prev: boolean) => void;
  handleChangeSelectedDate: (newDate: Date) => void;
};

function Week({
  week,
  today,
  selectedDate,
  handleChangeSelectedDate,
  handleChangeWeek,
}: Props) {
  const [startX, setStartX] = useState<number | null>(null);
  const [moved, setMoved] = useState<boolean>(false);

  function renderDate() {
    const result = [];
    for (let i = 0; i < 7; i++) {
      result.push(
        <DateBox
          key={i}
          date={week[i]}
          today={today}
          moved={moved}
          selectedDate={selectedDate}
          handleChangeSelectedDate={handleChangeSelectedDate}
        />
      );
    }

    return result;
  }

  return (
    <FlexRowContainer
      $margin="10px 0"
      onMouseDown={(e) => {
        setStartX(e.pageX);
        setMoved(false);
      }}
      onMouseUp={(e) => {
        if (startX !== null && startX > e.pageX) {
          setMoved(true);
          handleChangeWeek(false);
        } else if (startX !== null && startX < e.pageX) {
          setMoved(true);
          handleChangeWeek(true);
          setStartX(null);
        }
      }}
    >
      {renderDate()}
    </FlexRowContainer>
  );
}

export default Week;
