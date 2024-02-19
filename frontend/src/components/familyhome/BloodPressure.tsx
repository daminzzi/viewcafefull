import React from 'react';
import styled from 'styled-components';
import useHealthStore from '../../stores/HealthStore';
import ParentSize from '@visx/responsive/lib/components/ParentSize';
import BarChart from '../chart/BarChart';

const Container = styled.div`
  width: 90%;
  height: 42vh;
  padding: 4vh 0;
`;

function BloodPressure() {
  const { healthInfo } = useHealthStore();
  const keys = ['low', 'high'];
  const isData =
  !!healthInfo.before.morning ||
  !!healthInfo.before.noon ||
  !!healthInfo.before.dinner ||
  !!healthInfo.after.morning ||
  !!healthInfo.after.noon ||
  !!healthInfo.after.dinner;

  return isData ? (
    <Container>
      <ParentSize debounceTime={10}>
        {({ width: visWidth, height: visHeight }) => (
          <BarChart
            type="bp"
            width={visWidth}
            height={visHeight}
            keys={keys}
            data={healthInfo}
          />
        )}
      </ParentSize>
    </Container>
  ) : (
    <Container>
      <p>건강정보가 없습니다.</p>
    </Container>
  )
}

export default BloodPressure;
