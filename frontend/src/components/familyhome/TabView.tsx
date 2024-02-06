import React from 'react';
import Summary from './Summary';
import BloodPressure from './BloodPressure';
import BloodSugar from './BloodSugar';
import MealMedicine from './MealMedicine';
import useHealthStore from '../../stores/HealthStore';
import styled from 'styled-components';

const TempDiv = styled.div`
  height: 410px;
  width: 90%;
`;

function TabView() {
  const { tab } = useHealthStore();

  function view() {
    switch (tab) {
      case 'sum':
        return <Summary />;
      case 'bp':
        return <BloodPressure />;
      case 'bs':
        return <BloodSugar />;
      case 'mm':
        return <MealMedicine />;
      default:
        return null;
    }
  }

  return <TempDiv>{view()}</TempDiv>;
}

export default TabView;
