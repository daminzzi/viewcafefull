import React from 'react';
import VisitStateTag from './VisitStateTag';

type Props = {
  key: number | null;
  visit: VisitData | null;
};

function VisitRow({ key, visit }: Props) {
  return (
    <div>
      <span>{key}</span>
      <span>{visit?.conferenceDate}</span>
      <span>{visit?.conferenceTime}</span>
      {visit?.conferenceState && (
        <VisitStateTag conferenceState={visit?.conferenceState} />
      )}
    </div>
  );
}

export default VisitRow;
