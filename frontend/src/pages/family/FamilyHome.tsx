import React, { useState } from 'react'
import { Page } from '../../components/familyhome/TabButton'
import TabButtonGroup from '../../components/familyhome/TabButtonGroup';
import Comment from '../../components/familyhome/Comment';

function FamilyHome() {
  const [tab, setTab] = useState<Page>('sum');

  function handleChangeTab (tab: Page): void {
    setTab(tab);
  }

  return (
    <div>
      <h1>Family Home</h1>
      <hr />
      <TabButtonGroup handleChangeTab={handleChangeTab} />
      <hr />
      <Comment />
    </div>
  )
}

export default FamilyHome