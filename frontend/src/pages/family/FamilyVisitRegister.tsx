import React, { useState, ReactElement } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { ReactComponent as CalendarIcon } from '../../assets/icons/calendar.svg';
import { ReactComponent as ClockIcon } from '../../assets/icons/clock.svg';
import useConnectStore from '../../stores/ConnectStore';
import getVisitTime from '../../services/visit/getVisitTime';
import getConnectInfo from '../../services/connect/getConnectInfo';
import postVisitRegister from '../../services/visit/postVisitRegister';
import { useNavigate } from 'react-router-dom';

function FamilyVisitRegister() {
  const navigator = useNavigate();
  //현재 입소자 정보
  const { currConnect } = useConnectStore();
  const resVisitTime = getVisitTime(currConnect.tarDomainId);
  const connectFamily = getConnectInfo('tar', currConnect.tarDomainId);
  //면회 신청 날짜
  const [visitDate, setVisitDate] = useState<Date>(new Date());
  const [visitTime, setVisitTime] = useState<string>('시간선택');
  //면회 신청 시간
  const [visitTimeArr, setVisitTimeArr] = useState<string[]>(
    generateTimeArray(visitDate.getDay()),
  );

  //면회 가능 시간을 통해 선택 가능한 시간 배열 생성
  function generateTimeArray(day: number): string[] {
    const timeRange = resVisitTime[day];
    const { startTime, endTime, unit } = timeRange;

    const startTimeObj = new Date(`2000-01-01 ${startTime}`);
    const endTimeObj = new Date(`2000-01-01 ${endTime}`);
    const unitMinutes = parseInt(unit, 10);

    const timeArray: string[] = ['시간선택'];

    const currentTime = startTimeObj;
    while (currentTime <= endTimeObj) {
      const formattedTime = currentTime.toLocaleTimeString([], {
        hour: '2-digit',
        minute: '2-digit',
        hour12: false,
      });
      timeArray.push(formattedTime);

      currentTime.setMinutes(currentTime.getMinutes() + unitMinutes);
    }

    return timeArray;
  }

  function handleSelectDate(date: Date) {
    console.log(date);
    const timeRange = generateTimeArray(date.getDay());
    console.log(timeRange);

    setVisitTimeArr(timeRange);
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

  function getFormatDate(date: Date) {
    const year = date.getFullYear(); //yyyy
    let month: string | number = 1 + date.getMonth(); //M
    month = month >= 10 ? month : '0' + month; //month 두자리로 저장
    let day: string | number = date.getDate(); //d
    day = day >= 10 ? day : '0' + day; //day 두자리로 저장
    return year + '' + month + '' + day; //'-' 추가하여 yyyy-mm-dd 형태 생성 가능
  }

  function handleVisitRegister() {
    try {
      const arr = [];
      for (let i: number = 0; i < connectFamily.length; i++) {
        if (connectFamily[i]) {
          arr.push({
            applicationId: connectFamily[i].appDomainId,
          });
        }
      }
      const form = {
        applicationList: arr,
        permissionId: currConnect.permissionId,
        conferenceDate: getFormatDate(visitDate),
        conferenceTime: visitTime,
      };
      console.log(form);
      postVisitRegister(form);
      navigator('/family/visit');
    } catch (error) {
      console.error('회원가입 실패:', error);
    }
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
            placeholderText="면회 날짜 선택"
          />
        </div>
        <div>
          <label>
            <ClockIcon />
            면회 시간 선택
          </label>
          <select
            value={visitTime}
            onChange={(e) => setVisitTime(e.target.value)}
          >
            {showVisitTime()}
          </select>
        </div>
        <div>
          <label>면회 참가 인원</label>
          <div>{showVisitFamily()}</div>
        </div>
        <button type="button" onClick={() => handleVisitRegister()}>
          면회 신청
        </button>
      </form>
    </div>
  );
}

export default FamilyVisitRegister;
