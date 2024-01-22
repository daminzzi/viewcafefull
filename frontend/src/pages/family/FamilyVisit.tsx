import React, { useState, useEffect } from "react";
import VisitRow from "../../components/visit/VisitRow";
import Header from "../../components/common/Header";
import getVisitList from "../../services/visit/getVisitList";
import { PathType } from "../../services/visit/getVisitList";

export type VisitData = {
  applicationId: string;
  targetName: string;
  targetRoom: string;
  permissionId: string;
  createdDatetime: string;
  conferenceDate: string;
  conferenceTime: string;
  conferenceState: string;
  conferenceLink: string | null;
  startDatetime: string | null;
  endDatetime: string | null;
};

function FamilyVisit() {
  const [visitList, setVisitList] = useState<{
    conferenceList: VisitData[] | null;
  }>({
    conferenceList: null,
  });

  useEffect(() => {
    const fetchVisitList = async () => {
      try {
        const params = {
          type: "app" as PathType,
          domainId: "temp",
        };
        const data = await getVisitList(params);
        setVisitList({ conferenceList: data.conferenceList });
      } catch (error) {
        console.error("면회 일정 조회 에러:", error);
      }
    };
    fetchVisitList();
  }, []);

  function repeatVisitRow(visitList: VisitData[]) {
    const arr = [];
    for (let i = 0; i < visitList.length; i++) {
      arr.push(<VisitRow key={i} visit={visitList[i]} />);
    }
    return arr;
  }

  return (
    <div>
      <Header />
      <h1>면회 일정 조회</h1>
      <button onClick={() => console.log("면회 신청")}>면회 신청</button>
      <div>
        {visitList?.conferenceList && repeatVisitRow(visitList.conferenceList)}
      </div>
    </div>
  );
}

export default FamilyVisit;
