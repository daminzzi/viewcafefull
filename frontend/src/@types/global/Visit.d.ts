type VisitData = {
  applicationId: string;
  targetName: string;
  targetRoom: string;
  permissionId: string;
  createdDatetime: string;
  conferenceDate: string;
  conferenceTime: string;
  conferenceState: string;
  conferenceLink: string | null;
  startDatetime: string | null;
  endDatetime: string | null;
};

type TimeRange = {
  startTime: string;
  endTime: string;
  unit: string;
  day: string;
};
