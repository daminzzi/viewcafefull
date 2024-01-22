import { useEffect, useState } from "react";
import getUserInfo, { UserInfo } from '../../services/user/getUserInfo';
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
    <>
      <div>FamilyProfile</div>
      <div>이름</div>
      <div>{userInfo.parentName}</div>
      <div>생년월일</div>
      <div>{userInfo.birth}</div>
      <div>휴대전화</div>
      <div>{userInfo.phoneNumber}</div>
      <br />
      <div>연결된 입소자 목록</div>
          <p>{currConnect.perName}요양원</p>
          <p>입소자 이름: {currConnect.tarName} 님</p>
    </>
  )
}

export default FamilyProfile;