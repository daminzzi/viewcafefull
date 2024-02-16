import React from 'react';
import FlexRowContainer from '../common/FlexRowContainer';
import { HiddenCheckBox, ShowCheckBox } from '../common/CheckBox2';

type Props = {
  handleCheckBoxChange: (id: string) => void;
  isChecked: boolean;
  connectFamily: ConnectResponse;
};

function FamilyRow({ isChecked, handleCheckBoxChange, connectFamily }: Props) {
  return (
    <FlexRowContainer
      onClick={() => handleCheckBoxChange(connectFamily.appDomainId)}
      $position="relative"
      $justifyContent="start"
      $margin="5px"
      $gap="5px"
    >
      <HiddenCheckBox
        id={`family-checkbox-${connectFamily.appDomainId}`}
        checked={isChecked}
        onChange={() => {
          // console.log('check');
          handleCheckBoxChange(connectFamily.appDomainId);
        }}
      />
      <ShowCheckBox
        htmlFor={`family-checkbox-${connectFamily.appDomainId}`}
        checked={isChecked}
      />

      <FlexRowContainer $justifyContent="start" $margin="5px" $gap="5px">
        <span>{connectFamily.appName}</span>
        <span>{connectFamily.relationship}</span>
      </FlexRowContainer>
    </FlexRowContainer>
  );
}

export default FamilyRow;
