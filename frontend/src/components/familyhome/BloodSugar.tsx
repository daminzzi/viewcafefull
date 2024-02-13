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

function BloodSugar() {
  const { healthInfo } = useHealthStore();
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
            data={healthInfo}
          />
        )}
      </ParentSize>
    </Container>
  );
}

export default BloodSugar;
