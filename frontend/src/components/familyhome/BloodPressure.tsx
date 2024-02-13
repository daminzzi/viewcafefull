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

function BloodPressure() {
  const { healthInfo } = useHealthStore();
  const data = [
    {
      type: 'morning',
      low: healthInfo.low.morning,
      high: healthInfo.high.morning,
    },
    { type: 'noon', low: healthInfo.low.noon, high: healthInfo.high.noon },
    {
      type: 'dinner',
      low: healthInfo.low.dinner,
      high: healthInfo.high.dinner,
    },
  ];
  const keys = ['low', 'high'];

  return (
    <Container>
      <ParentSize debounceTime={10}>
        {({ width: visWidth, height: visHeight }) => (
          <BarChart
            type="bp"
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

export default BloodPressure;
