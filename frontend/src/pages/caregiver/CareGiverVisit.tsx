import React, { useState, useEffect, ReactElement } from 'react';
import getVisitList from '../../services/visit/getVisitList';
import VisitRow from '../../components/visit/VisitRow';
import ContentsContainer from '../../components/common/ContentsContainer';
import FlexColContainer from '../../components/common/FlexColContainer';
import Line from '../../components/common/Line';
import Title from '../../components/common/Title';
import VisitTodayRow from '../../components/visit/VisitTodayRow';

function FamilyVisit() {
  const [visitList, setVisitList] = useState<VisitData[]>([]);
  const [todayVisit, setTodayVisit] = useState<VisitData[]>([]);
  useEffect(() => {
    const fetchVisitList = async () => {
      try {
        const data = await getVisitList('tar');
        setVisitList(data.reservedConferenceList);
        setTodayVisit(data.todayConferenceList);
      } catch (error) {
        console.error('면회 일정 조회 에러:', error);
      }
    };
    fetchVisitList();
  }, []);

  function repeatVisitTodayRow(list: VisitData[]) {
    const arr: ReactElement[] = [];
    for (let i: number = 0; i < list.length; i += 1) {
      arr.push(
        <FlexColContainer key={i}>
          <VisitTodayRow visit={list[i]} />
          {i < list.length - 1 && (
            <Line $borderRight="none" $borderLeft="none" />
          )}
        </FlexColContainer>,
      );
    }
    return arr;
  }

  function repeatVisitRow(list: VisitData[]) {
    const arr: ReactElement[] = [];
    for (let i: number = 0; i < list.length; i += 1) {
      arr.push(
        <FlexColContainer key={i}>
          <VisitRow visit={list[i]} />
          {i < list.length - 1 && <Line $borderRight="none" $borderLeft="none" />}
        </FlexColContainer>,
      );
    }
    return arr;
  }

  return (
    <div>
      <Title icon={'visit'}>면회 조회</Title>
      <FlexColContainer $alignItems="center">
        <div style={{ width: '85%', fontSize: '1.2rem' }}>오늘 예정된 면회</div>
        <ContentsContainer
          $alignItems="flex-start"
          $width="85%"
          $padding="10px 0 0 0"
        >
          {todayVisit.length > 0 ? (
            repeatVisitTodayRow(todayVisit)
          ) : (
            <FlexColContainer $height="3rem" $padding="0 0 10px 0">
              예정된 면회가 없습니다.
            </FlexColContainer>
          )}
        </ContentsContainer>
        <div style={{ width: '85%', fontSize: '1.2rem' }}>면회 신청 목록</div>
        <ContentsContainer
          $alignItems="flex-start"
          $width="85%"
          $padding="10px 0 0 0"
        >
          {visitList.length > 0 ? (
            repeatVisitRow(visitList)
          ) : (
            <FlexColContainer $height="3rem" $padding="0 0 10px 0">
              신청한 면회가 없습니다.
            </FlexColContainer>
          )}
        </ContentsContainer>
      </FlexColContainer>
    </div>
  );
}

export default FamilyVisit;
