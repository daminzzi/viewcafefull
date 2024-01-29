import React from 'react';

type Props = {
  subject: string;
};

function ReportSubject({ subject }: Props) {
  return (
    <div>
      <h1>{subject}</h1>
    </div>
  );
}

export default ReportSubject;
