// import api from '../api';

async function getVisitList(form: {
  applicationList: { applicationId: string }[];
  permissionId: string;
  conferenceDate: string;
  conferenceTime: string;
}) {
  // try {
  //   const response = await api.post('/conference', form);
  //   if (response.status !== 201) {
  //     throw new Error(`오류: ${response.status}`);
  //   }
  //   return;
  // } catch (error) {
  //   throw error;
  // }
  console.log(form);
}

export default getVisitList;
