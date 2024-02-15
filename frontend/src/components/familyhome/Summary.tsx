import React from 'react';
import useHealthStore from '../../stores/HealthStore';
import FlexColContainer from '../common/FlexColContainer';
import styled from 'styled-components';
import MainChart from '../chart/MainChart';
import ParentSize from '@visx/responsive/lib/components/ParentSize';
import { success, warning, failed } from '../../assets/styles/palettes';
const StyledHr = styled.hr`
  width: 100%;
`;

const Wrapper = styled.div`
  width: 100%;
  height: 45;
`;

const LabelWrapper = styled.div`
  display: flex;
  width: 100%;
  justify-content: center;
`;

function maxValue(obj: HealthInfoData) {
  const values = Object.values(obj) as Array<number>;
  const max = Math.max(...values);
  return max === 0 ? null : max;
}

function Summary() {
  const { healthInfo } = useHealthStore();

  function nodata(data: number | null) {
    if (data === null) {
      return <p>건강정보가 없습니다</p>;
    }
    return null;
  }

  return (
    <FlexColContainer $width="90%" $alignItems="start">
      <StyledLabel style={{ margin: '1rem 0 0.5rem', fontWeight: '600' }}>
        최고 혈당
      </StyledLabel>
      <StyledLabel2>공복:</StyledLabel2>
      {nodata(maxValue(healthInfo.before)) || (
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
          <LabelWrapper>
            <Legend>
              <SmallRectangle $backgroundColor={success} />
              <span>정상 ~99</span>
            </Legend>
            <Legend>
              <SmallRectangle $backgroundColor={warning} />
              <span>주의 100~124</span>
            </Legend>
            <Legend>
              <SmallRectangle $backgroundColor={failed} />
              <span>위험 125~</span>
            </Legend>
          </LabelWrapper>
        </Wrapper>
      )}
      <StyledLabel2>식후: </StyledLabel2>
      {nodata(maxValue(healthInfo.after)) || (
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
          <LabelWrapper>
            <Legend>
              <SmallRectangle $backgroundColor={success} />
              <span>정상 ~139</span>
            </Legend>
            <Legend>
              <SmallRectangle $backgroundColor={warning} />
              <span>주의 140~199</span>
            </Legend>
            <Legend>
              <SmallRectangle $backgroundColor={failed} />
              <span>위험 200~</span>
            </Legend>
          </LabelWrapper>
        </Wrapper>
      )}
      <StyledHr />
      <StyledLabel style={{ fontWeight: '600' }}> 최고 혈압</StyledLabel>
      <StyledLabel2>이완:</StyledLabel2>
      {nodata(maxValue(healthInfo.low)) || (
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
          <LabelWrapper>
            <Legend>
              <SmallRectangle $backgroundColor={success} />
              <span>정상 ~79</span>
            </Legend>
            <Legend>
              <SmallRectangle $backgroundColor={warning} />
              <span>주의 80~89</span>
            </Legend>
            <Legend>
              <SmallRectangle $backgroundColor={failed} />
              <span>위험 90~</span>
            </Legend>
          </LabelWrapper>
        </Wrapper>
      )}
      <StyledLabel2>수축:</StyledLabel2>
      {nodata(maxValue(healthInfo.before)) || (
        <Wrapper>
          <ParentSize>
            {({ width: visWidth }) => (
              <MainChart
                width={visWidth}
                maxValue={200}
                value={maxValue(healthInfo.high)}
                range={[120, 140]}
              />
            )}
          </ParentSize>
          <LabelWrapper>
            <Legend>
              <SmallRectangle $backgroundColor={success} />
              <span>정상 ~119</span>
            </Legend>
            <Legend>
              <SmallRectangle $backgroundColor={warning} />
              <span>주의 120~139</span>
            </Legend>
            <Legend>
              <SmallRectangle $backgroundColor={failed} />
              <span>위험 140~</span>
            </Legend>
          </LabelWrapper>
        </Wrapper>
      )}
    </FlexColContainer>
  );
}

const StyledLabel = styled.p`
  margin: 0.5rem 0;
`;

const StyledLabel2 = styled.p`
  margin: 0.8rem 0 0 0;
`;

const SmallRectangle = styled.div<{ $backgroundColor: string }>`
  display: inline-block;
  width: 10px;
  height: 10px;
  background-color: ${(props) => props.$backgroundColor};
  margin: 0 0.5rem;
`;

const Legend = styled.div`
  display: inline-flex;
  justify-content: center;
  align-items: center;
  font-size: 0.7rem;
  width: 130px;
`;

export default Summary;
