import React from 'react';
import useConnectStore from '../../stores/ConnectStore';
import { ReactComponent as UserImgIcon } from '../../assets/icons/user-image.svg';
import { ReactComponent as ChevronDownIcon } from '../../assets/icons/chevron-down.svg';

function FamilyHeader() {
  const { connectArr, currConnect, setCurr, updateConnect } = useConnectStore();
  
  if (connectArr.length === 0) {
    updateConnect('app','asdf');
  }

  return (
    <div className="family-header">
      <UserImgIcon className="user-img-icon" />
      <span>
        {currConnect.tarName}
      </span>
      <ChevronDownIcon className="chevron-down-icon"/>
    </div>
  )
}

export default FamilyHeader