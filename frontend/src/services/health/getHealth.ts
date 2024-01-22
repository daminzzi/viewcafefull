// 건강 정보 조회

// import api from '../api';

// function getHealth (doainId: string, date: string) {
//   api.get(`/health/${doainId}`{
//     date
//   })
//     .then((res) => {
//         if (res.status === 200) {
//           return res.data;
//         } else if (res.status === 403) {
//           throw new Error(`${res.status}`);
//         }
//       })
//     .catch((err) => {
//        console.log(err);
//     })
// }

// 더미데이터 return

import healthData from './healthData.json';

function getHealth (domainId:string, date: string) {
  console.log(domainId);
  console.log(date);
  return healthData;
}

export default getHealth;