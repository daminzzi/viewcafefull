/* eslint-disable operator-linebreak */
/* eslint-disable object-curly-newline */
/* eslint-disable react/jsx-one-expression-per-line */
import React, { ReactElement } from 'react';
import { HealthInfo, MealObj, MedicineObj } from '../../pages/family/Types';

type Props = {
  healthInfo: HealthInfo;
};

function avg(arr: Array<number>): number | null {
  return arr.length === 0
    ? null
    : Math.round(arr.reduce((total, value) => total + value) / arr.length);
}

function renderImage(urls: MealObj | null): ReactElement | null {
  return urls === null ? null : (
    <div>
      <p>아침</p>
      <img
        src={urls.breakfast}
        alt="breakfast"
      />
      <p>점심</p>
      <img
        src={urls.lunch}
        alt="lunch"
      />
      <p>저녁</p>
      <img
        src={urls.dinner}
        alt="dinner"
      />
    </div>
  );
}

function checkMedicine(medicineObj: MedicineObj | null): ReactElement | null {
  return medicineObj === null ? null : (
    <div>
      <span>아침: </span>
      <span>{medicineObj.breakfast ? 'O' : 'X'}</span>
      <span>점심: </span>
      <span>{medicineObj.lunch ? 'O' : 'X'}</span>
      <span>저녁: </span>
      <span>{medicineObj.dinner ? 'O' : 'X'}</span>
    </div>
  );
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
      {renderImage(mealObj)}
      {checkMedicine(medicineObj)}
    </div>
  );
}

export default Summary;
