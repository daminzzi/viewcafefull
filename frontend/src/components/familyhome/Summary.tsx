import React from 'react';
import useHealthStore from '../../stores/HealthStore';
import FlexColContainer from '../common/FlexColContainer';
import styled from 'styled-components';

const StyledHr = styled.hr`
  width: 100%;
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
      <p>공복: {maxValue(healthInfo.before)}</p>
      <p>식후: {maxValue(healthInfo.after)}</p>
      <StyledHr />
      <span>혈압</span>
      <p>이완: {maxValue(healthInfo.low)}</p>
      <p>수축: {maxValue(healthInfo.high)}</p>
    </FlexColContainer>
  );
}

export default Summary;
