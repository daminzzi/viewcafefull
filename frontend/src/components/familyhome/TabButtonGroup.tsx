import React from 'react';
import { RoundedButton } from '../common/Buttons';
import { white, medium, deep } from '../../assets/styles/palettes';
import FlexRowContainer from '../common/FlexRowContainer';
import styled, { css } from 'styled-components';

type Props = {
  tab: Page;
  handleChangeTab: (tab: Page) => void;
};

const TabButton = styled(RoundedButton)<{ $isSelected: boolean }>`
font-size: 0.8rem;
width: 4.5rem;
height: 1.75rem;
border-radius: 2rem;
background-color: ${white};
box-shadow: 0.1rem 0.1rem 0.2rem ${medium};
-ms-user-select: none;
-moz-user-select: -moz-none;
-webkit-user-select: none;
-khtml-user-select: none;
user-select: none;

${(props) =>
  props.$isSelected &&
  css`
    box-shadow: 0 0 0 0.1rem ${deep};
  `}
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
