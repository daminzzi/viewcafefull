import React from 'react'
import { VisitData } from '../../pages/family/FamilyVisit'
import VisitStateTag from './VisitStateTag';
type Props = {
	visit: VisitData | null;
}

function VisitRow({visit}: Props) {
  return (
	  <div>
      <span>{visit?.conferenceDate}</span>
      <span>{visit?.conferenceTime}</span>
      {visit?.conferenceState && <VisitStateTag conferenceState={visit?.conferenceState} />}
	  </div>
  )
}

export default VisitRow