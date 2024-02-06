import React from 'react';
import ImageFrame from '../common/ImageFrame';
import useHealthStore from '../../stores/HealthStore';

export function renderImage(url: string | null, mealType: string) {
  return url === null ? null : (
    <ImageFrame src={url} alt={mealType} $size="30%" />
  );
}

function avg(obj: HealthInfoData) {
  let total = 0;
  let count = 0;
  if (obj.morning !== null) {
    total += Number(obj.morning);
    count += 1;
  }
  if (obj.noon !== null) {
    total += Number(obj.noon);
    count += 1;
  }
  if (obj.dinner !== null) {
    total += Number(obj.dinner);
    count += 1;
  }
  return count === 0 ? null : Math.round(total / count);
}

function Summary() {
  const { healthInfo } = useHealthStore();

  return (
    <div>
      <p>혈당</p>
      <p>공복: {avg(healthInfo.before)}</p>
      <p>식후: {avg(healthInfo.after)}</p>
      <hr />
      <p>혈압</p>
      <p>이완: {avg(healthInfo.low)}</p>
      <p>수축: {avg(healthInfo.high)}</p>
      <hr />
      <p>식단/복약</p>
      <p>아침</p>
      <p>점심</p>
      <p>저녁</p>
    </div>
  );
}

export default Summary;
