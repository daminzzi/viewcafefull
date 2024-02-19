import React from 'react';
import FlexColContainer from '../common/FlexColContainer';
import { styled } from 'styled-components';

type Props = {
  children: React.ReactNode;
  title: string;
  content: string;
};

function ReportLifeContent({ children, title, content }: Props) {
  return (
    <FlexColContainer $alignItems="start" $margin="10px 20px" $width="94%">
      <Title>{title}</Title>
      {children}
      <Content>{content}</Content>
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

export default ReportLifeContent;
