import React, { useState, ReactElement } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { ReactComponent as CalendarIcon } from '../../assets/icons/calendar.svg';
import { ReactComponent as ClockIcon } from '../../assets/icons/clock.svg';
import useConnectStore from '../../stores/ConnectStore';
import getVisitTime from '../../services/visit/getVisitTime';
import getConnectInfo from '../../services/connect/getConnectInfo';

function VisitRegister() {
  //현재 입소자 정보
  const { currConnect } = useConnectStore();
  //면회 신청 날짜
  const [visitDate, setVisitDate] = useState<Date>();
  //면회 신청 시간
  const [visitTimeArr, setVisitTimeArr] = useState<string[]>(['시간선택']);
  const resVisitTime = getVisitTime(currConnect.tarDomainId);
  const connectFamily = getConnectInfo('tar', currConnect.tarDomainId);

  function generateTimeArray(timeRange: TimeRange): string[] {
    const { startTime, endTime, unit } = timeRange;

    const startTimeObj = new Date(`2000-01-01 ${startTime}`);
    const endTimeObj = new Date(`2000-01-01 ${endTime}`);
    const unitMinutes = parseInt(unit, 10);

    const timeArray: string[] = [];

    const currentTime = startTimeObj;
    while (currentTime <= endTimeObj) {
      const formattedTime = currentTime.toLocaleTimeString([], {
        hour: '2-digit',
        minute: '2-digit',
      });
      timeArray.push(formattedTime);

      currentTime.setMinutes(currentTime.getMinutes() + unitMinutes);
    }

    return timeArray;
  }

  function handleSelectDate(date: Date) {
    console.log(date);
    const timeRange = generateTimeArray(resVisitTime[date.getDay()]);
    const arr = [visitTimeArr[0]];
    timeRange.forEach((time) => {
      arr.push(time);
    });

    setVisitTimeArr(arr);
    console.log(visitTimeArr);
  }

  function showVisitTime() {
    const arr: ReactElement[] = [];
    for (let i: number = 0; i < visitTimeArr.length; i++) {
      arr.push(
        <option key={i} value={visitTimeArr[i]}>
          {visitTimeArr[i]}
        </option>,
      );
    }
    return arr;
  }

  function showVisitFamily() {
    const arr: ReactElement[] = [];
    for (let i: number = 0; i < connectFamily.length; i++) {
      arr.push(
        <div key={i}>
          <input
            type="checkbox"
            name={connectFamily[i].appDomainId}
            value={connectFamily[i].appDomainId}
          />
          <label>{connectFamily[i].appName}</label>
        </div>,
      );
    }
    return arr;
  }

  return (
    <div>
      <h1>면회신청</h1>
      <form>
        <div>
          <label>
            <CalendarIcon />
            면회 날짜 선택
          </label>
          <DatePicker
            selected={visitDate}
            onSelect={(date: Date) => handleSelectDate(date)}
            onChange={(date: Date) => setVisitDate(date)}
            minDate={new Date()}
          />
        </div>
        <div>
          <label>
            <ClockIcon />
            면회 시간 선택
          </label>
          <select>{showVisitTime()}</select>
        </div>
        <div>
          <label>면회 참가 인원</label>
          <div>{showVisitFamily()}</div>
        </div>
        <button type="button">면회 신청</button>
      </form>
    </div>
  );
}

export default VisitRegister;
