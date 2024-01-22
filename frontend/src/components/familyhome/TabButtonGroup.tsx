import React from 'react';
import TabButton, { Page } from './TabButton';

type Props = {
  handleChangeTab: (tab: Page) => void;
};

function TabButtonGroup({ handleChangeTab }: Props) {
  return (
    <div>
      <TabButton handleChangeTab={handleChangeTab} tab="sum">
        요약
      </TabButton>
      <TabButton handleChangeTab={handleChangeTab} tab="bs">
        혈당
      </TabButton>
      <TabButton handleChangeTab={handleChangeTab} tab="bp">
        혈압
      </TabButton>
      <TabButton handleChangeTab={handleChangeTab} tab="mm">
        식단/복약
      </TabButton>
    </div>
  );
}

export default TabButtonGroup;
