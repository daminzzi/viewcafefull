import apiMutipart from '../apiMultipart';

async function getMeal(day: string) {
  try {
    const response = await apiMutipart.get('/meal', { params: { day } });
    if (response.status !== 200) {
      throw new Error(`오류: ${response.status}`);
    }
    return response.data;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export default getMeal;
