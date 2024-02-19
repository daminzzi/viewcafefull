import React from 'react';
import { Axis, Grid, LineSeries, GlyphSeries, XYChart } from '@visx/xychart';
import { LegendOrdinal } from '@visx/legend';
import { scaleOrdinal } from '@visx/scale';
import styled from 'styled-components';
import { black, gray } from '../../assets/styles/palettes';

export type DataType = Array<{
  x: string;
  y: number;
}>;

type Props = {
  data: Array<DataType>;
  width: number;
  height: number;
  keys: Array<string>;
  colors: Array<string>;
  domain: [a: number, b: number];
};

const LegendDiv = styled.div`
  display: flex;
  justify-content: center;
  fontsize: 1rem;
`;

const Canvas = styled.div`
 margin-bottom: 3vh;
`

function xAccessor(d: { x: string; y: number }) {
  return d.x;
}
function yAccessor(d: { x: string; y: number }) {
  return d.y;
}

function LineChart({ data, width, height, keys, colors, domain }: Props) {
  const colorScale = scaleOrdinal<string, string>({
    domain: keys,
    range: colors,
  });

  return (
    <Canvas>
      <XYChart
        width={width}
        height={height}
        xScale={{ type: 'band' }}
        yScale={{ type: 'linear', domain: domain }}
      >
        <Grid
          columns={false}
          numTicks={4}
          lineStyle={{
            stroke: gray,
            strokeLinecap: 'round',
            strokeWidth: 2,
          }}
          strokeDasharray="0, 4"
        />
        <Axis
          hideAxisLine
          orientation="bottom"
          tickLabelProps={() => ({
            dy: 10,
            fill: black,
            fontSize: '0.75rem',
            textAnchor: 'middle',
          })}
          numTicks={4}
          tickFormat={(d) => {
            const arr = d.split('-');
            return `${arr[1]}.${arr[2]}`;
          }}
          tickStroke={black}
        />
        <Axis
          hideAxisLine
          hideTicks
          orientation="left"
          tickStroke={black}
          numTicks={4}
          tickLabelProps={() => ({ dx: -10, fill: black, fontSize: '0.75rem' })}
        />
        {data.map((d, i) => {
          return [
            <LineSeries
              key={`line-series-${i}`}
              dataKey={`line-series-${i}`}
              data={d}
              xAccessor={xAccessor}
              yAccessor={yAccessor}
              colorAccessor={() => colorScale(keys[i])}
            />,
            <GlyphSeries
              key={i}
              dataKey={`glyph-series-${i}`}
              data={d}
              xAccessor={xAccessor}
              yAccessor={yAccessor}
              colorAccessor={() => colorScale(keys[i])}
            />,
          ];
        })}
      </XYChart>
      <LegendDiv>
        <LegendOrdinal
          scale={colorScale}
          direction="row"
          labelMargin="0 15px 0 0"
        />
      </LegendDiv>
    </Canvas>
  );
}

export default LineChart;
