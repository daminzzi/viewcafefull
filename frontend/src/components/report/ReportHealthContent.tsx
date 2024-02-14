import React from 'react';
import FlexColContainer from '../common/FlexColContainer';
import styled from 'styled-components';
type Props = {
  children: React.ReactNode;
  title: string;
  content: {
    early: string;
    mid: string;
    late: string;
  };
};

function ReportHealthContent({ children, title, content }: Props) {
  return (
    <FlexColContainer $alignItems="start" $margin="10px 30px" $width="90%">
      <Title>{title}</Title>
      {children}
      <Content>
        <ContentLabel>초반: </ContentLabel>
        {content.early}
      </Content>
      <Content>
        <ContentLabel>중반: </ContentLabel>
        {content.mid}
      </Content>
      <Content>
        <ContentLabel>후반: </ContentLabel>
        {content.late}
      </Content>
    </FlexColContainer>
  );
}

const Title = styled.h4`
  margin: 0.5rem;
`;

const Content = styled.div`
  margin: 0.3rem;
  word-break: keep-all;
`;

const ContentLabel = styled.span`
  font-weight: 600;
`;

export default ReportHealthContent;
