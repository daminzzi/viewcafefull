// import api from '../api';
import tVisitData from './tVisitData.json';

export type PathType = 'app' | 'tar';

async function getVisitList(params: { type: PathType; domainId: string }) {
  try {
    // const response = await api.get(`/conference/${params.type}?domain-id=${params.domainId}`);
    // return response.data;
    console.log(params);
    return tVisitData;
  } catch (error) {
    console.log(error);
    throw error;
  }
}

export default getVisitList;
