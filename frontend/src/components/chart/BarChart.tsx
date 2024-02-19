import React from 'react';
import styled from 'styled-components';
import { BarGroupHorizontal, Bar } from '@visx/shape';
import { Group } from '@visx/group';
import { AxisLeft, AxisBottom } from '@visx/axis';
import { LegendOrdinal } from '@visx/legend';
import { scaleBand, scaleLinear, scaleOrdinal } from '@visx/scale';
import { Grid } from '@visx/grid';
import { useSpring, animated } from '@react-spring/web';
import { black, main3, medium, white } from '../../assets/styles/palettes';

export type ChartData = {
  [key: string]: number | string | null;
  type: string;
};

type Props = {
  type: 'bs' | 'bp';
  width: number;
  height: number;
  margin?: { top: number; right: number; bottom: number; left: number };
  keys: Array<string>;
  data: HealthInfo;
};

const LegendDiv = styled.div`
  display: flex;
  justify-content: center;
  fontsize: 1rem;
`;

const defaultMargin = { top: 20, right: 30, bottom: 30, left: 50 };

function parseY(y: string) {
  switch (y) {
    case 'morning':
      return '아침';

    case 'noon':
      return '점심';

    case 'dinner':
      return '저녁';

    default:
      return;
  }
}

function labelFormat(format: string) {
  switch (format) {
    case 'low':
      return '이완기';

    case 'high':
      return '수축기';

    case 'before':
      return '식전';

    case 'after':
      return '식후';

    default:
      return;
  }
}

function BarChart({
  width,
  height,
  data,
  keys,
  type,
  margin = { ...defaultMargin },
}: Props) {
  function createChartData(): Array<ChartData> {
    if (type === 'bp') {
      return [
        {
          type: 'morning',
          low: data.low.morning as number,
          high: data.high.morning as number,
        },
        {
          type: 'noon',
          low: data.low.noon as number,
          high: data.high.noon as number,
        },
        {
          type: 'dinner',
          low: data.low.dinner as number,
          high: data.high.dinner as number,
        },
      ];
    } else {
      return [
        {
          type: 'morning',
          before: data.before.noon as number,
          after: data.after.noon as number,
        },
        {
          type: 'noon',
          before: data.before.noon as number,
          after: data.after.noon as number,
        },
        {
          type: 'dinner',
          before: data.before.dinner as number,
          after: data.after.dinner as number,
        },
      ];
    }
  }
  const ChartData = createChartData();
  const xMax = Math.max(0, width - margin.left - margin.right);
  const yMax = Math.max(0, height - margin.top - margin.bottom);
  const xScale = scaleLinear<number>({
    domain: type === 'bp' ? [0, 200] : [0, 300],
  });
  const y1Scale = scaleBand({
    domain: keys,
    padding: 0.1,
  });
  const colorScale = scaleOrdinal<string, string>({
    domain: keys,
    range: [main3, medium],
  });
  const y0Scale = scaleBand({
    domain: ['morning', 'noon', 'dinner'],
    padding: 0.4,
  });

  y0Scale.rangeRound([0, yMax]);
  y1Scale.rangeRound([0, y0Scale.bandwidth()]);
  xScale.rangeRound([0, xMax]);

  const [{ scale }] = useSpring(() => {
    return {
      from: { scale: 0 },
      to: { scale: 1 },
      reset: true,
    };
  }, [data]);
  const AnimatedBar = animated(Bar);

  return (
    <div>
      <LegendDiv>
        <LegendOrdinal
          scale={colorScale}
          labelFormat={labelFormat}
          direction="row"
          labelMargin="0 15px 0 0"
        />
      </LegendDiv>
      <svg width={width} height={height}>
        <rect x={0} y={0} width={width} height={height} fill={white} rx={14} />
        <Grid
          top={margin.top}
          left={margin.left}
          xScale={xScale}
          yScale={y0Scale}
          width={xMax}
          height={yMax}
          stroke={black}
          strokeOpacity={0.1}
          numTicksColumns={5}
        />
        <Group top={margin.top} left={margin.left}>
          <BarGroupHorizontal
            data={ChartData}
            keys={keys}
            width={xMax}
            y0={(d) => d.type}
            y0Scale={y0Scale}
            y1Scale={y1Scale}
            xScale={xScale}
            color={colorScale}
          >
            {(barGroups) =>
              barGroups.map((barGroup) => (
                <Group
                  key={`bar-group-horizontal-${barGroup.index}-${barGroup.y0}`}
                  top={barGroup.y0}
                >
                  {barGroup.bars.map((bar) => {
                    return (
                    <g key={`${barGroup.index}-${bar.index}-${bar.key}`}>
                      <AnimatedBar
                        x={bar.x}
                        y={bar.y}
                        width={scale.to((s) => s * bar.width)}
                        height={bar.height}
                        fill={bar.color}
                        rx={bar.height / 2}
                      />
                      <text
                        x={bar.x}
                        y={bar.y}
                        dx={bar.width+4}
                        dy={bar.height/2}
                        fontSize={14}
                        alignmentBaseline="middle" 
                      >{bar.value}</text>
                    </g>
                  )})}
                </Group>
              ))
            }
          </BarGroupHorizontal>
          <AxisLeft
            scale={y0Scale}
            stroke={black}
            tickStroke={black}
            tickFormat={parseY}
            tickLabelProps={{
              fill: black,
              fontSize: '0.75rem',
              textAnchor: 'end',
              dy: '0.33em',
            }}
          />
          <AxisBottom
            top={yMax}
            scale={xScale}
            numTicks={5}
            stroke={black}
            tickStroke={black}
            tickLabelProps={{
              fill: black,
              fontSize: '0.66rem',
              textAnchor: 'middle',
            }}
          />
        </Group>
      </svg>
    </div>
  );
}

export default BarChart;
