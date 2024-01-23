import React from 'react';
import Summary from './Summary';
import BloodPressure from './BloodPressure';
import BloodSugar from './BloodSugar';
import MealMedicine from './MealMedicine';
import { Page } from './TabButton';
import { HealthInfo } from '../../pages/family/Types';

type Props = {
  tab: Page;
  healthInfo: HealthInfo;
};

function TabView({ tab, healthInfo }: Props) {
  function view() {
    switch (tab) {
      case 'sum':
        return <Summary healthInfo={healthInfo} />;
      case 'bp':
        return (
          <BloodPressure
            lowArr={healthInfo.lowArr}
            highArr={healthInfo.highArr}
          />
        );
      case 'bs':
        return (
          <BloodSugar
            beforeArr={healthInfo.beforeArr}
            afterArr={healthInfo.afterArr}
          />
        );
        case "mm":
          return (
            <MealMedicine
              mealObj={healthInfo.mealObj}
              medicineObj={healthInfo.medicineObj}
            />
          );
      default:
        return null;
    }
  }

  return (
    <div>
      {view()}
    </div>
  );
}

export default TabView;
