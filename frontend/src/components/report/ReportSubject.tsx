import React from 'react';
import { styled } from 'styled-components';
type Props = {
  subject: string;
};

function ReportSubject({ subject }: Props) {
  return <Title>{subject}</Title>;
}

const Title = styled.h2`
  margin: 1.5rem 0 0 0;
`;

export default ReportSubject;
