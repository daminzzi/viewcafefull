import React from 'react';
import { RoundedButton } from '../common/Buttons';
import { success, warning, failed, white } from '../../assets/styles/palettes';
type Props = {
  conferenceState: string;
};

function VisitStateTag({ conferenceState }: Props) {
  if (conferenceState === 'S') {
    return (
      <RoundedButton
        $height="30px"
        $width="60px"
        $padding="2px 2px"
        $borderRadius="15px"
        $bgColor={warning}
        $color={white}
      >
        대기
      </RoundedButton>
    );
  }
  if (conferenceState === 'A') {
    return (
      <RoundedButton
        $height="30px"
        $width="60px"
        $padding="2px 2px"
        $borderRadius="15px"
        $bgColor={success}
        $color={white}
      >
        승인
      </RoundedButton>
    );
  }
  if (conferenceState === 'D') {
    return (
      <RoundedButton
        $height="30px"
        $width="60px"
        $padding="2px 2px"
        $borderRadius="15px"
        $bgColor={failed}
        $color={white}
      >
        거부
      </RoundedButton>
    );
  }
  return <RoundedButton>VisitStateTag</RoundedButton>;
}

export default VisitStateTag;
