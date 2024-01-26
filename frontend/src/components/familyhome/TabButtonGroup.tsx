import React from 'react';
// import Button from '../common/Button';
import Button from '../common/Button';
import FlexRowContainer from '../common/FlexRowContainer';
import styled from 'styled-components';
// import { deep } from '../../assets/styles/palettes';

type Props = {
  tab: Page;
  handleChangeTab: (tab: Page) => void;
};

const TabButton = styled(Button)`
  border-radius: 20px;
  width: 20%;
`;

function isSelected(curr: Page, state: Page) {
  return curr === state;
}

function TabButtonGroup({ handleChangeTab, tab }: Props) {
  return (
    <FlexRowContainer>
      <TabButton
        $isSelected={isSelected(tab, 'sum')}
        onClick={() => handleChangeTab('sum')}
      >
        요약
      </TabButton>
      <TabButton
        $isSelected={isSelected(tab, 'bs')}
        onClick={() => handleChangeTab('bs')}
      >
        혈당
      </TabButton>
      <TabButton
        $isSelected={isSelected(tab, 'bp')}
        onClick={() => handleChangeTab('bp')}
      >
        혈압
      </TabButton>
      <TabButton
        $isSelected={isSelected(tab, 'mm')}
        onClick={() => handleChangeTab('mm')}
      >
        식단/복약
      </TabButton>
    </FlexRowContainer>
  );
}

export default TabButtonGroup;
