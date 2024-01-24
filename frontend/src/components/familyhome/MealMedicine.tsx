import React from 'react';
import { MealObj, Medicine, MedicineObj } from '../../@types/HealthTypes';
import { renderImage } from './Summary';

type Props = {
  mealObj: MealObj;
  medicineObj: MedicineObj;
};

function medicineInfo(medicine: Medicine | null) {
  return medicine === null ? null : (
    <div>
      <p>약 이름 : {medicine.medicineName}</p>
      <p>약 효능 : {medicine.information}</p>
    </div>
  );
}

function MealMedicine(props: Props) {
  return (
    <div>
      <p>MealMedicine</p>
      <p>아침</p>
      {renderImage(props.mealObj.breakfast, 'breakfast')}
      {medicineInfo(props.medicineObj.breakfast)}
      <p>점심</p>
      {renderImage(props.mealObj.lunch, 'lunch')}
      {medicineInfo(props.medicineObj.lunch)}
      <p>저녁</p>
      {renderImage(props.mealObj.dinner, 'dinner')}
      {medicineInfo(props.medicineObj.dinner)}
    </div>
  );
}

export default MealMedicine;
