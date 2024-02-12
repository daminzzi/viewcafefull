import React from 'react';
import FlexColContainer from '../common/FlexColContainer';

type Props = {
  title: string;
  content: string;
};

function ReportContent({ title, content }: Props) {
  return (
    <FlexColContainer>
      <div>{title}</div>
      <div>{content}</div>
    </FlexColContainer>
  );
}

export default ReportContent;
