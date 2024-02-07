

// 연결 정보 조회

import api from '../api';
import { ConnectResponse } from '../../stores/ConnectStore';

async function getConnectInfo(
  type: PathType,
  domainId: string,
): Promise<ConnectResponse[]> {
  try {
    const res = await api.get(`/user-link/${type}`, {
      params: {
        'domain-id': domainId,
      },
    });

    if (res.status === 200) {
      return res.data.userLinkList;
    } else if (res.status === 401 || res.status === 403) {
      throw new Error(`${res.status}`);
    }
  } catch (err) {
    console.log(err);
    return [];
  }
  return [];
}

export default getConnectInfo;
