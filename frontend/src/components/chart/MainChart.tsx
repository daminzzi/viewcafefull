import React from 'react';
import { Bar } from '@visx/shape';
import {
  success,
  warning,
  failed,
  gray,
  white,
  black,
} from '../../assets/styles/palettes';
import { scaleLinear } from '@visx/scale';
import { useSpring, animated } from '@react-spring/web';

type Props = {
  width: number;
  maxValue: number;
  value: number | null;
  range: [number, number];
};

function MainChart({ width, maxValue, value, range }: Props) {
  const xScale = scaleLinear<number>({
    domain: [0, maxValue],
  });
  xScale.rangeRound([0, width-40]);
  const [{ scale }] = useSpring(() => ({
    from: { scale: 0 },
    to: { scale: 1 },
    reset: true
  }), [value]);
  const AnimatedBar = animated(Bar);
  const AnimatedCircle = animated.circle;

  function calValue() {
    if (value === null) {
      return null;
    }

    if (value > maxValue) {
      return Math.min(maxValue, value);
    }

    return value;
  }
  const plotValue = calValue()

  function colorState() {
    if (value === null) {
      return white;
    }
    if (value < range[0]) {
      return success;
    } else if (value < range[1]) {
      return warning;
    } else if (value >= range[1]) {
      return failed;
    }
  }

  const color = colorState();

  return plotValue !== null && width > 0 ? (
    <div>
      <svg width={width} height={45}>
        <rect x={0} y={0} width={width} height={40} fill={white} />
        <rect x={20} y={25} width={width-40} height={15} fill={gray} rx={15 / 2} />
        <AnimatedBar
          x={20}
          y={25}
          fill={color}
          width={scale.to((s) => s * xScale(plotValue))}
          height={15}
          rx={7.5}
        />
        <AnimatedCircle
          cx={scale.to((s) =>  s * xScale(plotValue) + 20)}
          cy={25 + 7.5}
          r={8}
          stroke={black}
          strokeWidth={3}
          fill={white}
        />
        <rect
          key="rect"
          x={xScale(plotValue)}
          height={20}
          width={40}
          fill={color}
        />
        <polygon
          key="polygon"
          points={`${xScale(plotValue) + 13}, 19 ${xScale(plotValue) + 27}, 19 ${xScale(plotValue) + 20}, 26`}
          fill={color}
        />
        <text
          key="text"
          x={xScale(plotValue)+20}
          y={16}
          fill={white}
          textAnchor="middle"
        >
          {value}
        </text>
      </svg>
    </div>
  ) : null;
}

export default MainChart;
