import React from 'react';
import VisitStateTag from './VisitStateTag';
import FlexRowContainer from '../common/FlexRowContainer';
import FlexColContainer from '../common/FlexColContainer';

type Props = {
  visit: VisitData;
};

function VisitRow({ visit }: Props) {
  const visitYear = parseInt(visit.conferenceDate.substring(0, 4));
  const visitMonth = parseInt(visit.conferenceDate.substring(4, 6));
  const visitDay = parseInt(visit.conferenceDate.substring(6, 8));
  // function formatDate(date: Date) {
  //   // return format(date, 'yyyy.mm.dd hh:m');
  //   return date.toString();
  // }
  const visitDate = new Date(visitYear, visitMonth - 1, visitDay);
  const visitDayType = visitDate.getDay();

  function transDayType(day: number) {
    const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
    return daysOfWeek[day];
  }
  return (
    <FlexColContainer $alignItems="flex-start" $margin="10px 10px" $width="90%">
      <FlexRowContainer $justifyContent="space-between" $alignItems="center">
        <span>
          {visitYear}.{visitMonth}.{visitDay}({transDayType(visitDayType)})
        </span>
      </FlexRowContainer>
      <FlexRowContainer $justifyContent="space-evenly" $margin="10px 10px">
        <span>{visit.conferenceTime}</span>
        <span>{visit.targetName}</span>
        <VisitStateTag conferenceState={visit?.conferenceState} />
      </FlexRowContainer>
    </FlexColContainer>
  );
}

export default VisitRow;
