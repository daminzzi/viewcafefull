import React from 'react';
// import FlexRowContainer from '../common/FlexRowContainer';
import FlexColContainer from '../common/FlexColContainer';
import useHealthStore from '../../stores/HealthStore';
import MealMedicineImage from './MealMedicineImage';

function MealMedicine() {
  const { healthInfo } = useHealthStore();
  function renderImage() {
    const result = [];
    result.push(
      <MealMedicineImage
        key="0"
        time="아침"
        src={healthInfo.meal.morning as string}
        isMedicine={healthInfo.medicine.morning as boolean}
      />,
    );
    result.push(
      <MealMedicineImage
        key="1"
        time="점심"
        src={healthInfo.meal.noon as string}
        isMedicine={healthInfo.medicine.noon as boolean}
      />,
    );
    result.push(
      <MealMedicineImage
        key="2"
        time="저녁"
        src={healthInfo.meal.dinner as string}
        isMedicine={healthInfo.medicine.dinner as boolean}
      />,
    );

    return result;
  }

  return (
    <FlexColContainer $margin="4vh 0" $gap="3vh">
      {renderImage()}
    </FlexColContainer>
  );
}

export default MealMedicine;
