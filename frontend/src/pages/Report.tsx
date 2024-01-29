import React from 'react';
import { useParams } from 'react-router-dom';
import getReportInfo from '../services/report/getReportInfo';
import ReportSubject from '../components/report/ReportSubject';
// type Props = {};

function Report() {
  const params = useParams<{ id: string }>();
  console.log(params.id);
  const reportInfo = params.id ? getReportInfo(params.id) : null;
  console.log(reportInfo);
  return (
    <div>
      <ReportSubject subject="1. 건강 정보" />
      <ReportSubject subject="2. 생활 정보" />
      <ReportSubject subject="3. 생활 영상" />
      <div>
        갑진년 새해 복<br /> 많이 받으세요
      </div>
    </div>
  );
}

export default Report;
