// 연결 정보 조회

// api 요청 작성 api 완성시 확인 필요
// import api from '../api';

export type PathType = 'app' | 'tar';

// function getConnectInfo(type: PathType, domainId: string) {
//   api.get(`/user-link/${type}`, {
//     params: {
//       'domain-id' : domainId,
//     },
//   })
//     .then((res) => {
//       if (res.status === 200) {
//         return res.data;
//       } else if (res.status === 401) {
//         throw new Error(`${res.status}`)
//       } else if (res.status === 403) {
//         throw new Error(`${res.status}`)
//       }
//     })
//     .catch((err) => {
//       console.log(err);
//     })
// }

//  임의 데이터 return
const dummy = [
  {
    applicationId: '23',
    appDomainId: 'junghoon1039',
    appName: '박정훈',
    targetId: '14',
    tarDomainId: 'ssafy0101',
    tarName: '김싸피',
    permissionId: '6',
    perDomainId: 'onnuri777',
    perName: '온누리요양원',
    agreement: 'A',
    relationship: '증조할아버지',
  },
];

const dummy2 = [
  {
    applicationId: '23',
    appDomainId: 'junghoon1039',
    appName: '박정훈',
    targetId: '14',
    tarDomainId: 'ssafy0101',
    tarName: '김싸피',
    permissionId: '6',
    perDomainId: 'onnuri777',
    perName: '온누리요양원',
    agreement: 'A',
    relationship: '손자',
  },
  {
    applicationId: '24',
    appDomainId: 'damin1004',
    appName: '조다민',
    targetId: '14',
    tarDomainId: 'ssafy0101',
    tarName: '김싸피',
    permissionId: '6',
    perDomainId: 'onnuri777',
    perName: '온누리요양원',
    agreement: 'A',
    relationship: '증손자',
  },
];

function getConnectInfo(type: PathType, domainId: string) {
  console.log(type, domainId);
  if (type === 'tar') {
    return dummy2;
  }
  return dummy;
}

export default getConnectInfo;
