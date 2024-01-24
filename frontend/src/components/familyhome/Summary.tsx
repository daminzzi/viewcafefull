import React from 'react';

type Props = {
  healthInfo: HealthInfo;
};

function avg(arr: Array<number>): number | null {
  return arr.length === 0
    ? null
    : Math.round(arr.reduce((total, value) => total + value) / arr.length);
}

export function renderImage(url: string | null, mealType: string) {
  return url === null ? null : <img src={url} alt={mealType} />;
}

function checkMedicine(medicine: Medicine | null) {
  return medicine === null ? <span>X</span> : <span>O</span>;
}

function Summary({ healthInfo }: Props) {
  const { beforeArr, afterArr, lowArr, highArr, medicineObj, mealObj } =
    healthInfo;

  return (
    <div>
      <p>혈당</p>
      <p>공복: {avg(beforeArr)}</p>
      <p>식후: {avg(afterArr)}</p>
      <hr />
      <p>혈압</p>
      <p>이완: {avg(lowArr)}</p>
      <p>수축: {avg(highArr)}</p>
      <hr />
      <p>식단/복약</p>
      <p>아침</p>
      {renderImage(mealObj.breakfast, 'breakfast')}
      {checkMedicine(medicineObj.breakfast)}
      <p>점심</p>
      {renderImage(mealObj.lunch, 'lunch')}
      {checkMedicine(medicineObj.lunch)}
      <p>저녁</p>
      {renderImage(mealObj.dinner, 'dinner')}
      {checkMedicine(medicineObj.dinner)}
    </div>
  );
}

export default Summary;
