import React from 'react';
import styled from 'styled-components';
import useHealthStore from '../../stores/HealthStore';
import ParentSize from '@visx/responsive/lib/components/ParentSize';
import BarChart, { ChartData } from '../chart/BarChart';

const Container = styled.div`
  width: 90%;
  height: 42vh;
  padding: 4vh 0;
`;

function BloodSugar() {
  const { healthInfo } = useHealthStore();
  const data = [
    {
      type: 'morning',
      before: healthInfo.before.morning,
      after: healthInfo.after.morning,
    },
    {
      type: 'noon',
      before: healthInfo.before.noon,
      after: healthInfo.after.noon,
    },
    {
      type: 'dinner',
      before: healthInfo.before.dinner,
      after: healthInfo.after.dinner,
    },
  ];
  const keys = ['before', 'after'];

  return (
    <Container>
      <ParentSize debounceTime={10}>
        {({ width: visWidth, height: visHeight }) => (
          <BarChart
            type="bs"
            width={visWidth}
            height={visHeight}
            keys={keys}
            data={data as Array<ChartData>}
          />
        )}
      </ParentSize>
    </Container>
  );
}

export default BloodSugar;
