import React from 'react';
import useHealthStore from '../../stores/HealthStore';
import { RoundedButton } from '../common/Buttons';
import { white, deep } from '../../assets/styles/palettes';
import FlexRowContainer from '../common/FlexRowContainer';
import styled, { css } from 'styled-components';

const TabButton = styled(RoundedButton)<{ $isSelected: boolean }>`
  color: ${deep};
  font-size: 0.9rem;
  width: 4rem;
  height: 1.75rem;
  border-radius: 2rem;
  background-color: ${white};
  box-shadow: 0.1rem 0.1rem 0.2rem ${deep};
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

function TabButtonGroup() {
  const { tab, setTab } = useHealthStore();
  return (
    <FlexRowContainer $justifyContent="space-evenly">
      <TabButton
        $isSelected={isSelected(tab, 'sum')}
        onClick={() => setTab('sum')}
      >
        요약
      </TabButton>
      <TabButton
        $isSelected={isSelected(tab, 'bs')}
        onClick={() => setTab('bs')}
      >
        혈당
      </TabButton>
      <TabButton
        $isSelected={isSelected(tab, 'bp')}
        onClick={() => setTab('bp')}
      >
        혈압
      </TabButton>
      <TabButton
        $isSelected={isSelected(tab, 'mm')}
        onClick={() => setTab('mm')}
      >
        식단
      </TabButton>
    </FlexRowContainer>
  );
}

export default TabButtonGroup;
