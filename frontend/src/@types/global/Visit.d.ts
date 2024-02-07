type VisitData = {
  applicationId: string;
  targetName: string;
  permissionId: string;
  createdDatetime: string;
  conferenceDate: string;
  conferenceTime: string;
  conferenceState: string;
  sessionName: string | null;
  startDatetime: string | null;
  endDatetime: string | null;
};

type TimeRange = {
  startTime: string;
  endTime: string;
  unit: string;
  day: string;
};
