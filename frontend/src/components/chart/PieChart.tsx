import React from 'react';
import { Pie } from '@visx/shape';
import { Group } from '@visx/group';
import { scaleOrdinal } from '@visx/scale';
import { success, warning, failed } from '../../assets/styles/palettes';

type Props = {
  width: number;
  height: number;
  data: Array<{ condition: string, value: number }>;
  margin?: { top: number; right: number; bottom: number; left: number };
};

const defaultMargin = { top: 20, right: 20, bottom: 20, left: 20 };

const colorScale = scaleOrdinal({
  domain: ['좋음', '보통', '나쁨'],
  range: [success, warning, failed],
});

const PieChart = ({ width, height, data, margin = { ...defaultMargin } }: Props) => {
  const innerWidth = width - margin.left - margin.right;
  const innerHeight = height - margin.top - margin.bottom;
  const radius = Math.min(innerWidth, innerHeight) / 2;
  const centerY = innerHeight / 2;
  const centerX = innerWidth / 2;
  const top = centerY + margin.top;
  const left = centerX + margin.left;
  const pieSortValues = (a: number, b: number) => b - a;

  return (
    <svg width={width} height={height}>
      <Group top={top} left={left}>
        <Pie
          data={data}
          pieValue={(d) => d.value}
          pieSortValues={pieSortValues}
          outerRadius={radius}
        >
          {(pie) => {
            return pie.arcs.map((arc, index) => {
              const { condition } = arc.data;
              const [centroidX, centroidY] = pie.path.centroid(arc);
              const hasSpaceForLabel = arc.endAngle - arc.startAngle >= 0.1;
              const arcPath = pie.path(arc);
              const arcFill = colorScale(condition);
              return (
                <g key={`arc-${condition}-${index}`}>
                  <path d={arcPath as string} fill={arcFill} />
                  {hasSpaceForLabel && (
                    <text
                      x={centroidX}
                      y={centroidY}
                      dy=".33em"
                      fill="#ffffff"
                      fontSize={22}
                      textAnchor="middle"
                      pointerEvents="none"
                    >
                      {condition}
                    </text>
                  )}
                </g>
              );
            });
          }}
        </Pie>
      </Group>
    </svg>
  );
};

export default PieChart;
