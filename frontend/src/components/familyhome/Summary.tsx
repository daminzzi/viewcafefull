import React from 'react';
import useHealthStore from '../../stores/HealthStore';
import FlexColContainer from '../common/FlexColContainer';
import styled from 'styled-components';
import MainChart from '../chart/MainChart';
import ParentSize from '@visx/responsive/lib/components/ParentSize';

const StyledHr = styled.hr`
  width: 100%;
`;

const Wrapper = styled.div`
  width: 100%;
  height: 45;
`;

function maxValue(obj: HealthInfoData) {
  const values = Object.values(obj) as Array<number>;
  const max = Math.max(...values);
  return max === 0 ? null : max;
}

function Summary() {
  const { healthInfo } = useHealthStore();

  return (
    <FlexColContainer $width="90%" $alignItems="start">
      <p>혈당</p>
      <p>공복:</p>
      <Wrapper>
        <ParentSize>
          {({ width: visWidth }) => (
            <MainChart
              width={visWidth}
              maxValue={300}
              value={maxValue(healthInfo.before)}
              range={[100, 125]}
            />
          )}
        </ParentSize>
      </Wrapper>
      <p>식후: </p>
      <Wrapper>
        <ParentSize>
          {({ width: visWidth }) => (
            <MainChart
              width={visWidth}
              maxValue={300}
              value={maxValue(healthInfo.after)}
              range={[140, 200]}
            />
          )}
        </ParentSize>
      </Wrapper>
      <StyledHr />
      <span>혈압</span>
      <p>이완:</p>
      <Wrapper>
        <ParentSize>
          {({ width: visWidth }) => (
            <MainChart
              width={visWidth}
              maxValue={200}
              value={maxValue(healthInfo.low)}
              range={[80, 90]}
            />
          )}
        </ParentSize>
      </Wrapper>
      <p>수축:</p>
      <Wrapper>
        <ParentSize>
          {({ width: visWidth }) => (
            <MainChart
              width={visWidth}
              maxValue={300}
              value={maxValue(healthInfo.high)}
              range={[120, 140]}
            />
          )}
        </ParentSize>
      </Wrapper>
    </FlexColContainer>
  );
}

export default Summary;
