import React from "react";
import TabButton, { Page } from "./TabButton";

type Props = {
  handleChangeTab: (tab: Page) => void;
};

function TabButtonGroup(props: Props) {
  return (
    <div>
      <TabButton handleChangeTab={props.handleChangeTab} tab="sum">
        요약
      </TabButton>
      <TabButton handleChangeTab={props.handleChangeTab} tab="bs">
        혈당
      </TabButton>
      <TabButton handleChangeTab={props.handleChangeTab} tab="bp">
        혈압
      </TabButton>
      <TabButton handleChangeTab={props.handleChangeTab} tab="mm">
        식단/복약
      </TabButton>
    </div>
  );
}

export default TabButtonGroup;
