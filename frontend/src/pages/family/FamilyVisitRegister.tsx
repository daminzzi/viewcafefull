import React, { useState, ReactElement, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { ko } from 'date-fns/locale';
import { ReactComponent as CalendarIcon } from '../../assets/icons/calendar.svg';
import { ReactComponent as ClockIcon } from '../../assets/icons/clock.svg';
import { ReactComponent as MultiUserIcon } from '../../assets/icons/multiUser.svg';
import useConnectStore from '../../stores/ConnectStore';
import getVisitTime from '../../services/visit/getVisitTime';
import postVisitRegister from '../../services/visit/postVisitRegister';
import { useNavigate } from 'react-router-dom';
import ContentsContainer from '../../components/common/ContentsContainer';
import Select from 'react-select';
import Input from '../../components/common/Input';
import { lightgray } from '../../assets/styles/palettes';
import FlexColContainer from '../../components/common/FlexColContainer';
import Title from '../../components/common/Title';
import FlexRowContainer from '../../components/common/FlexRowContainer';
import { Button } from '../../components/common/Buttons';
import FamilyRow from '../../components/visit/FamilyRow';
import getConnectInfo from '../../services/connect/getConnectInfo';

function FamilyVisitRegister() {
  const navigator = useNavigate();

  //현재 입소자 정보
  const { currConnect } = useConnectStore();
  const resVisitTime = getVisitTime(currConnect.tarDomainId);
  const [connectFamily, setConnectFamily] = useState<ConnectResponse[]>([]);
  useEffect(() => {
    async function init() {
      // console.log('init');
      const temp = await getConnectInfo('tar', currConnect.tarDomainId);
      setConnectFamily(temp);
    }
    init();
  }, []);

  //면회 신청 날짜
  const [visitDate, setVisitDate] = useState<Date>(new Date());
  const [visitTime, setVisitTime] = useState<Option>({
    value: '시간 선택',
    label: '시간 선택',
  });
  //면회 신청 시간
  const [visitTimeArr, setVisitTimeArr] = useState<Option[]>(
    generateTimeArray(visitDate.getDay()),
  );
  const [selectedFamily, setSelectedFamily] = useState<string[]>([]);

  //면회 가능 시간을 통해 선택 가능한 시간 배열 생성
  function generateTimeArray(day: number): Option[] {
    const timeRange = resVisitTime[day];
    const { startTime, endTime, unit } = timeRange;

    const startTimeObj = new Date(`2000-01-01 ${startTime}`);
    const endTimeObj = new Date(`2000-01-01 ${endTime}`);
    const unitMinutes = parseInt(unit, 10);

    const timeArray: Option[] = [
      {
        value: '시간 선택',
        label: '시간 선택',
      },
    ];

    const currentTime = startTimeObj;
    while (currentTime <= endTimeObj) {
      const formattedTime = currentTime.toLocaleTimeString([], {
        hour: '2-digit',
        minute: '2-digit',
        hour12: false,
      });
      timeArray.push({ value: formattedTime, label: formattedTime });

      currentTime.setMinutes(currentTime.getMinutes() + unitMinutes);
    }

    return timeArray;
  }

  function handleSelectDate(date: Date) {
    // console.log(date);
    const timeRange = generateTimeArray(date.getDay());
    // console.log(timeRange);

    setVisitTimeArr(timeRange);
    // console.log(visitTimeArr);
  }

  function handleSelectTime(time: Option | null) {
    if (time !== null) {
      setVisitTime(time);
    }
  }

  function handleCheckBoxChange(memberId: string) {
    if (selectedFamily.includes(memberId)) {
      setSelectedFamily(selectedFamily.filter((id) => id !== memberId));
    } else {
      setSelectedFamily([...selectedFamily, memberId]);
    }
    // console.log(selectedFamily);
  }

  function showVisitFamily() {
    const arr: ReactElement[] = [];
    for (let i: number = 0; i < connectFamily.length; i++) {
      const isChecked = selectedFamily.includes(connectFamily[i].appDomainId);
      arr.push(
        // <FlexRowContainer key={i} $justifyContent="start" $margin="5px">
        //   <input
        //     type="checkbox"
        //     onChange={() => handleCheckBoxChange(connectFamily[i].appDomainId)}
        //   />
        //   <label>
        //     {connectFamily[i].appName} {connectFamily[i].relationship}
        //   </label>
        // </FlexRowContainer>,
        <FamilyRow
          key={i}
          isChecked={isChecked}
          handleCheckBoxChange={handleCheckBoxChange}
          connectFamily={connectFamily[i]}
        />,
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
      for (let i: number = 0; i < selectedFamily.length; i++) {
        if (selectedFamily[i]) {
          arr.push({
            applicationId: selectedFamily[i],
          });
        }
      }
      const form = {
        applicationList: arr,
        targetId: currConnect.tarDomainId,
        permissionId: currConnect.permissionId,
        conferenceDate: getFormatDate(visitDate),
        conferenceTime: visitTime.value,
      };
      // console.log(form);
      postVisitRegister(form);
      navigator('/family/visit');
    } catch (error) {
      console.error('면회 신청 실패:', error);
    }
  }

  return (
    <FlexColContainer>
      <Title>면회신청</Title>
      <ContentsContainer
        $padding="10px 20px"
        $margin="0px 0px 20px 0px"
        $width="78%"
      >
        <FlexColContainer>
          <FlexColContainer $width="100%">
            <FlexRowContainer
              $justifyContent="flex-start"
              $margin="10px"
              $width="98%"
              $gap="5px"
            >
              <CalendarIcon width="23px" />
              면회 날짜 선택
            </FlexRowContainer>
            <div
              style={{
                display: 'flex',
                flexDirection: 'column',
                width: '100%',
              }}
            >
              <DatePicker
                dateFormat="yyyy.MM.dd"
                shouldCloseOnSelect
                selected={visitDate}
                onSelect={(date: Date) => handleSelectDate(date)}
                onChange={(date: Date) => setVisitDate(date)}
                minDate={new Date()}
                placeholderText="면회 날짜 선택"
                locale={ko}
                customInput={<Input $width="98%" $fontSize="1rem" inputMode='none'/>}
                renderCustomHeader={({
                  date,
                  decreaseMonth,
                  increaseMonth,
                  prevMonthButtonDisabled,
                  nextMonthButtonDisabled,
                }) => (
                  <FlexRowContainer>
                    <button
                      type="button"
                      onClick={decreaseMonth}
                      disabled={prevMonthButtonDisabled}
                    >
                      {'<'}
                    </button>
                    {date.getFullYear()}년 {date.getMonth()}월
                    <button
                      type="button"
                      onClick={increaseMonth}
                      disabled={nextMonthButtonDisabled}
                    >
                      {'>'}
                    </button>
                  </FlexRowContainer>
                )}
              />
            </div>
          </FlexColContainer>
          <FlexRowContainer
            $width="100%"
            $margin="10px 0px"
            $justifyContent="start"
          >
            <FlexRowContainer $justifyContent="start" $width="50%" $gap="5px">
              <ClockIcon width="23px" />
              면회 시간 선택
            </FlexRowContainer>
            <Select
              options={visitTimeArr}
              value={visitTime}
              onChange={(e) => handleSelectTime(e)}
              theme={(theme) => ({
                ...theme,
                borderRadius: 7,
              })}
              styles={{
                input:(provided) => ({
                  ...provided,
                    inputMode: 'none'
                }),
                control: (provided) => ({
                  ...provided,
                  border: `2px solid ${lightgray}`,
                  inputMode: 'none'
                }),
                container: (provided) => ({
                  ...provided,
                  width: `50%`,
                  inputMode: 'none'
                }),
              }}
            />
          </FlexRowContainer>
          <FlexColContainer
            $width="98%"
            $margin="10px 0px"
            $justifyContent="start"
          >
            <FlexRowContainer
              $justifyContent="flex-start"
              $margin="10px"
              $width="98%"
              $gap="5px"
            >
              <MultiUserIcon width="23px" />
              면회 참가 인원
            </FlexRowContainer>
            <FlexColContainer>{showVisitFamily()}</FlexColContainer>
          </FlexColContainer>
          <Button
            $width="100%"
            type="button"
            onClick={() => handleVisitRegister()}
          >
            면회 신청
          </Button>
        </FlexColContainer>
      </ContentsContainer>
    </FlexColContainer>
  );
}

export default FamilyVisitRegister;
