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
        return <BloodPressure />;
      case 'bs':
        return (
          <BloodSugar
            beforeArr={healthInfo.beforeArr}
            afterArr={healthInfo.afterArr}
          />
        );
      case 'mm':
        return <MealMedicine />;
      default:
        return null;
    }
  }

  return <div>{view()}</div>;
}

export default TabView;
