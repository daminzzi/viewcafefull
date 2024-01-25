// import api from '../api';
import tVisitData from './tVisitData.json';

export type PathType = 'app' | 'tar';

async function getVisitList(params: { type: PathType; domainId: string }) {
  try {
    // const response = await api.get(`/conference/${params.type}?domain-id=${params.domainId}`);
    // if (response.status !== 200) {
    //   throw new Error(`오류: ${response.status}`);
    // }
    // return response.data;
    console.log(params);
    return tVisitData;
  } catch (error) {
    // console.error(error);
    // return null;
    console.log(error);
    throw error;
  }
}

export default getVisitList;
