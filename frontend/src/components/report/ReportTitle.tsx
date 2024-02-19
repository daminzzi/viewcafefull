import React from 'react';
import FlexColContainer from '../common/FlexColContainer';
import { styled } from 'styled-components';

type Props = {
  year: number;
  month: number;
  permission: string;
  target: string;
};

function ReportTitle({ year, month, permission, target }: Props) {
  return (
    <FlexColContainer>
      <YearMonth>
        {year}년 {month}월<br /> 월간 레포트
      </YearMonth>
      <Info>{permission}</Info>
      <Info style={{ textAlign: 'right' }}>{target}</Info>
    </FlexColContainer>
  );
}

const YearMonth = styled.div`
  width: 100%;
  text-align: center;
  font-size: 2rem;
  font-weight: 700;
  margin: 2rem;
`;

const Info = styled.div`
  width: 100%;
  text-align: right;
  font-size: 1.2rem;
  font-weight: 600;
  margin: 0.2rem;
`;

export default ReportTitle;
