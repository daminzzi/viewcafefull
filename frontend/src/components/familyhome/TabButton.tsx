import React from "react";

export type Page = "sum" | "bs" | "bp" | "mm"; // 요약, 혈당, 혈압, 식단/복약

type Props = {
  children: React.ReactNode;
  handleChangeTab: (tab: Page) => void;
  tab: Page;
};

function TabButton(props: Props) {
  return (
    <button
      onClick={() => {
        props.handleChangeTab(props.tab);
      }}
    >
      {props.children}
    </button>
  );
}

export default TabButton;
