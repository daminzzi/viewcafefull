import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ContentsContainer from '../common/ContentsContainer';
import getVisitList from '../../services/visit/getVisitList';
import FlexColContainer from '../common/FlexColContainer';
import VisitStateTag from '../visit/VisitStateTag';
import FlexRowContainer from '../common/FlexRowContainer';
import { ReactComponent as VisitIcon } from '../../assets/icons/visit.svg';

import { ReactComponent as ChevronRight } from '../../assets/icons/chevronRight.svg';
import styled from 'styled-components';

function TodayConference() {
  const navigator = useNavigate();
  const [conference, setConference] = useState<VisitData>();

  function handleGotoVisit() {
    navigator('/caregiver/visit');
  }

  useEffect(() => {
    const fetchVisitList = async () => {
      try {
        const data = await getVisitList('tar');
        setConference(data.todayConferenceList[0]);
      } catch (error) {
        console.error('면회 일정 조회 에러:', error);
      }
    };
    fetchVisitList();
  }, []);

  return (
    <ContentsContainer>
      <FlexColContainer>
        <FlexRowContainer
          $width="90%"
          $margin="0"
          $justifyContent="space-between"
        >
          <FlexRowContainer $width="60%" $gap="5px" $justifyContent="start">
            <VisitIcon width="30px" />
            <SubTitle>오늘 예정된 면회</SubTitle>
          </FlexRowContainer>
          <ChevronRight width="30px" type="button" onClick={handleGotoVisit} />
        </FlexRowContainer>
        {conference ? (
          <FlexRowContainer
            $justifyContent="space-evenly"
            $margin="0 10px 10px 10px"
          >
            <span style={{ fontSize: '1.2rem', fontWeight: 600 }}>
              {conference.conferenceTime}
            </span>
            <span>{conference.targetName}</span>
            <VisitStateTag conferenceState={conference?.conferenceState} />
          </FlexRowContainer>
        ) : (
          <NoContent>오늘 예정된 면회가 없습니다</NoContent>
        )}
      </FlexColContainer>
    </ContentsContainer>
  );
}

const SubTitle = styled.span`
  font-size: 1.3rem;
  font-weight: 400;
`;

const NoContent = styled.div`
  font-size: 1rem;
  margin: 10px 0;
`;

export default TodayConference;
