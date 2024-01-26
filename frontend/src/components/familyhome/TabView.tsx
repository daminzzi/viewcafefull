import React from 'react';
import Summary from './Summary';
import BloodPressure from './BloodPressure';
import BloodSugar from './BloodSugar';
import MealMedicine from './MealMedicine';
import styled from 'styled-components';

type Props = {
  tab: Page;
  healthInfo: HealthInfo;
};

const TempDiv = styled.div`
  height: 410px;
  width: 90%;
`;

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
      case 'mm':
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

  return <TempDiv>{view()}</TempDiv>;
}

export default TabView;
