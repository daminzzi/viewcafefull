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
}

function TabView(props: Props) {
  function view () {
    switch (props.tab) {
      case 'sum':
        return <Summary healthInfo={props.healthInfo}/>;
      case 'bp':
        return <BloodPressure />;
      case 'bs':
        return <BloodSugar />;
      case'mm':
        return <MealMedicine />;
      default:
        return null;
    }
  }

  return (
    <div>
      { view() }
    </div>
  )
}

export default TabView