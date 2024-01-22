import React from 'react';

type Props = {
  conferenceState: string;
};

function VisitStateTag({ conferenceState }: Props) {
  if (conferenceState === 'S') {
    return <div>신청</div>;
  }
  if (conferenceState === 'A') {
    return <div>승인</div>;
  }
  if (conferenceState === 'D') {
    return <div>거부</div>;
  }
  return <div>VisitStateTag</div>;
}

export default VisitStateTag;
