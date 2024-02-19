import React from 'react';
import VisitStateTag from './VisitStateTag';
import FlexRowContainer from '../common/FlexRowContainer';
import FlexColContainer from '../common/FlexColContainer';
import { useNavigate } from 'react-router-dom';

type Props = {
  visit: VisitData;
};

function VisitTodayRow({ visit }: Props) {
  const navigator = useNavigate();
  const visitYear = parseInt(visit.conferenceDate.substring(0, 4));
  const visitMonth = parseInt(visit.conferenceDate.substring(4, 6));
  const visitDay = parseInt(visit.conferenceDate.substring(6, 8));

  const visitDate = new Date(visitYear, visitMonth - 1, visitDay);
  const visitDayType = visitDate.getDay();
  function transDayType(day: number) {
    const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
    return daysOfWeek[day];
  }

  function handleConference() {
    navigator(`/openvidu/${visit.sessionName}`);
  }

  return (
    <FlexColContainer $alignItems="flex-start" $margin="10px 10px" $width="90%">
      <FlexRowContainer $justifyContent="space-between" $alignItems="center">
        <span style={{ fontWeight: 600 }}>
          {visitYear}.{visitMonth}.{visitDay}({transDayType(visitDayType)})
        </span>
        <a style={{ textDecoration: 'underline' }} onClick={handleConference}>
          면회실 바로가기
        </a>
      </FlexRowContainer>
      <FlexRowContainer $justifyContent="space-evenly" $margin="10px 10px">
        <span style={{ fontWeight: 600 }}>{visit.conferenceTime}</span>
        <span>{visit.targetName}</span>
        <VisitStateTag conferenceState={visit?.conferenceState} />
      </FlexRowContainer>
    </FlexColContainer>
  );
}

export default VisitTodayRow;
