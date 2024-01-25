import React, { useState, useEffect, ReactElement } from 'react';
import getVisitList from '../../services/visit/getVisitList';
import VisitRow from '../../components/visit/VisitRow';
import { Link } from 'react-router-dom';

function FamilyVisit() {
  const [visitList, setVisitList] = useState<{
    conferenceList: VisitData[] | null;
  }>({ conferenceList: null });

  useEffect(() => {
    const fetchVisitList = async () => {
      try {
        const params = {
          type: 'app' as PathType,
          domainId: 'temp',
        };
        const data = await getVisitList(params);
        setVisitList({ conferenceList: data.conferenceList });
      } catch (error) {
        console.error('면회 일정 조회 에러:', error);
      }
    };
    fetchVisitList();
  }, []);

  function repeatVisitRow(list: VisitData[]) {
    const arr: ReactElement[] = [];
    for (let i: number = 0; i < list.length; i += 1) {
      arr.push(<VisitRow key={i} visit={list[i]} />);
    }
    return arr;
  }

  return (
    <div>
      <h1>면회 일정 조회</h1>
      <button type="button">
        <Link to="/family/visit/register">면회 신청</Link>
      </button>
      <div>
        {visitList?.conferenceList && repeatVisitRow(visitList.conferenceList)}
      </div>
    </div>
  );
}

export default FamilyVisit;
