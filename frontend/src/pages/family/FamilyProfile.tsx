import React from "react";
import { useEffect, useState } from "react";
import getUserInfo, { UserInfo } from "../../services/user/getUserInfo";
import useConnectStore from "../../stores/ConnectStore";

function FamilyProfile() {
  const [userInfo, setUserInfo] = useState<UserInfo | null>(null);
  const { currConnect } = useConnectStore();

  useEffect(() => {
    const fetchData = async () => {
      const res = await getUserInfo();
      setUserInfo(res);
    };
    fetchData();
  }, []);

  if (!userInfo) {
    return <div>Now Loading...</div>;
  }

  return (
    <div>
      <h2>개인정보</h2>
      <div>이름</div>
      <div>{userInfo.parentName}</div>
      <div>생년월일</div>
      <div>{userInfo.birth}</div>
      <div>휴대전화</div>
      <div>{userInfo.phoneNumber}</div>
      <br />
      <h2>입소자</h2>
      <p>{currConnect.perName}</p>
      <p>{currConnect.tarName} 님</p>
      <p>{currConnect.relationship}</p>
    </div>
  );
}

export default FamilyProfile;
