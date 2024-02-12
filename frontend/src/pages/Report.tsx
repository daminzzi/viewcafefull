import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import useConnectStore from '../stores/ConnectStore';
import getReportInfo from '../services/report/getReportInfo';
import ReportSubject from '../components/report/ReportSubject';
import ReportContent from '../components/report/ReportContent';
// type Props = {};

function Report() {
  const params = useParams<{ id: string }>();
  const { currConnect } = useConnectStore();
  console.log(params.id);
  const [reportInfo, setReportInfo] = useState<ReportInfo | null>();
  console.log(reportInfo);

  useEffect(() => {
    async function fetchReportInfo() {
      const result = params.id
        ? await getReportInfo(currConnect.targetId, params.id)
        : null;
      setReportInfo(result);
    }
    fetchReportInfo();
  }, []);

  return (
    <div>
      <ReportSubject subject="1. 건강 정보" />
      {reportInfo && (
        <ReportContent
          title="1-1. 혈당 정보"
          content={reportInfo?.pressure?.insights?.early}
        />
      )}
      <ReportSubject subject="2. 생활 정보" />
      <ReportSubject subject="3. 생활 영상" />
      <video src="https://youtu.be/hOJ76cZEt08?si=dxhVJbJ1HMGAdveC" />
      <div>
        갑진년 새해 복<br /> 많이 받으세요
      </div>
    </div>
  );
}

export default Report;
