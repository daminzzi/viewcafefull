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
  xScale.rangeRound([0, width]);
  const [{ scale }] = useSpring(() => ({
    from: { scale: 0 },
    to: { scale: 1 },
    reset: true
  }), [value]);
  const AnimatedBar = animated(Bar);
  const AnimatedCircle = animated.circle;

  function colorState() {
    if (value === null) {
      return white;
    }
    if (value < range[0]) {
      return success;
    } else if (value >= range[0] && value < range[1]) {
      return warning;
    } else if (value >= range[1]) {
      return failed;
    }
  }

  const color = colorState();

  return value !== null ? (
    <div>
      <svg width={width} height={45}>
        <rect x={0} y={0} width={width} height={40} fill={white} />
        <rect x={0} y={25} width={width} height={15} fill={gray} rx={15 / 2} />
        <AnimatedBar
          x={0}
          y={25}
          fill={color}
          width={scale.to((s) => s * xScale(value))}
          height={15}
          rx={7.5}
        />
        <AnimatedCircle
          cx={scale.to((s) => s * xScale(value))}
          cy={25 + 7.5}
          r={8}
          stroke={black}
          strokeWidth={3}
          fill={white}
        />
        <rect
          key="rect"
          x={xScale(value) - 20}
          height={20}
          width={40}
          fill={color}
        />
        <polygon
          key="polygon"
          points={`${xScale(value) - 7}, 19 ${xScale(value) + 7}, 19 ${xScale(value)}, 26`}
          fill={color}
        />
        <text
          key="text"
          x={xScale(value)}
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
