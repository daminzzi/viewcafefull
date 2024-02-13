import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import getReportInfo from '../services/report/getReportInfo';
import ReportTitle from '../components/report/ReportTitle';
import ReportSubject from '../components/report/ReportSubject';
import ReportHealthContent from '../components/report/ReportHealthContent';
import ReportLifeContent from '../components/report/ReportLifeContent';
import FlexColContainer from '../components/common/FlexColContainer';
import { styled } from 'styled-components';
// import styled from 'styled-components';
// type Props = {};

function Report() {
  const navigator = useNavigate();
  const params = useParams<{ id: string; yearMonth: string }>();
  console.log(params.id);
  const [reportInfo, setReportInfo] = useState<ReportInfo>();
  console.log(reportInfo);

  useEffect(() => {
    async function fetchReportInfo() {
      const result =
        params.id && params.yearMonth
          ? await getReportInfo(params.id, params.yearMonth)
          : null;
      if (result !== null) {
        setReportInfo(result);
        return;
      }
      navigator('not_found');
    }
    fetchReportInfo();
  }, []);

  return (
    <div>
      {reportInfo && (
        <FlexColContainer $alignItems="start" $padding="0.5rem" $width="96%">
          <ReportTitle
            year={reportInfo.year}
            month={reportInfo.month}
            permission="하늘선요양원"
            target="이복순"
          />
          <ReportSubject subject="1. 건강 정보" />
          <ReportHealthContent
            title="1-1. 혈압 정보"
            content={reportInfo?.pressure?.insights}
          >
            여기 그래프 들어감
          </ReportHealthContent>
          <ReportHealthContent
            title="1-2. 혈당 정보"
            content={reportInfo?.sugar?.insights}
          >
            여기 그래프 들어감
          </ReportHealthContent>
          <ReportSubject subject="2. 생활 정보" />
          <ReportLifeContent
            title="2-2. 생활 정보 요약"
            content={reportInfo?.lifeInfo}
          >
            여기 그래프 들어감
          </ReportLifeContent>
          <ReportSubject subject="3. 생활 영상" />
          {/* <video src={reportInfo.movie} /> */}
          <iframe
            width="99%"
            height="50%"
            src="https://www.youtube.com/embed/qfmdXoSlceU?si=XMPQ2aGt7jnCo-Zf"
            title="YouTube video player"
            frameBorder="7"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
          ></iframe>
          <FlexColContainer>
            <Message>
              갑진년 새해 복<br />
              많이 받으세요
              <br />
            </Message>
          </FlexColContainer>
        </FlexColContainer>
      )}
    </div>
  );
}

const Message = styled.h3`
  text-align: center;
`;

export default Report;
