import React from 'react';
import { Pie } from '@visx/shape';
import { Group } from '@visx/group';
import { scaleOrdinal } from '@visx/scale';
import { LegendOrdinal } from '@visx/legend';
import { success, warning, failed, white } from '../../assets/styles/palettes';
import FlexRowContainer from '../common/FlexRowContainer';
import styled from 'styled-components';

type Props = {
  width: number;
  height: number;
  data: { good: number; normal: number; bad: number };
  margin?: { top: number; right: number; bottom: number; left: number };
};

const LegendDiv = styled.div`
  display: flex;
  justify-content: center;
  fontsize: 1rem;
`;

const defaultMargin = { top: 20, right: 0, bottom: 20, left: 0 };

const colorScale = scaleOrdinal({
  domain: ['good', 'normal', 'bad'],
  range: [success, warning, failed],
});

const PieChart = ({
  width,
  height,
  data,
  margin = { ...defaultMargin },
}: Props) => {
  const innerWidth = width * 0.6 - margin.left - margin.right;
  const innerHeight = height - margin.top - margin.bottom;
  const radius = Math.min(innerWidth, innerHeight) / 2;
  const centerY = innerHeight / 2;
  const centerX = innerWidth / 2;
  const top = centerY + margin.top;
  const left = centerX + margin.left;
  const pieSortValues = (a: number, b: number) => b - a;
  const chartData = [
    { condition: 'good', value: data.good },
    { condition: 'normal', value: data.normal },
    { condition: 'bad', value: data.bad },
  ];

  function parseCondition(condition: string) {
    switch (condition) {
      case 'good':
        return `좋음: ${data.good}`;
  
      case 'normal':
        return `보통: ${data.normal}`;
  
      case 'bad':
        return `나쁨: ${data.bad}`;
  
      default:
        return;
    }
  }

  return (
    <FlexRowContainer $width={`${width}`} $height={`${height}`}>
      <svg width={width * 0.6} height={height}>
        <Group top={top} left={left}>
          <Pie
            data={chartData}
            pieValue={(d) => d.value}
            pieSortValues={pieSortValues}
            outerRadius={radius}
          >
            {(pie) => {
              return pie.arcs.map((arc, index) => {
                const { condition } = arc.data;
                const arcPath = pie.path(arc);
                const arcFill = colorScale(condition as string);
                return (
                  <g key={`arc-${condition}-${index}`}>
                    <path
                      d={arcPath as string}
                      fill={arcFill}
                      stroke={white}
                      strokeWidth={3}
                    />
                  </g>
                );
              });
            }}
          </Pie>
        </Group>
      </svg>
      <LegendDiv>
        <LegendOrdinal
          scale={colorScale}
          labelFormat={parseCondition}
          direction="column"
          labelMargin="0 15px 0 0"
        />
      </LegendDiv>
    </FlexRowContainer>
  );
};

export default PieChart;
