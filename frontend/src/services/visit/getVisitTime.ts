// import api from '../api';

const dummyData: TimeRange[] = [
  {
    startTime: '12:00',
    endTime: '18:00',
    unit: '30',
    day: '0',
  },
  {
    startTime: '10:00',
    endTime: '20:00',
    unit: '30',
    day: '1',
  },
  {
    startTime: '10:00',
    endTime: '20:00',
    unit: '30',
    day: '2',
  },
  {
    startTime: '10:00',
    endTime: '20:00',
    unit: '30',
    day: '3',
  },
  {
    startTime: '10:00',
    endTime: '20:00',
    unit: '30',
    day: '4',
  },
  {
    startTime: '10:00',
    endTime: '20:00',
    unit: '30',
    day: '5',
  },
  {
    startTime: '12:00',
    endTime: '18:00',
    unit: '30',
    day: '6',
  },
];

function getVisitTime(domainId: string) {
  console.log(domainId);
  // const response = await api.get(`/schedule/${params.domainId}`);
  // return response.data.scheduleList;

  return dummyData;
}

export default getVisitTime;
