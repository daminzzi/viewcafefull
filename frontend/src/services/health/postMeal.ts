import apiMultipart from '../apiMultipart';

async function postMeal(body: FormData) {
  try {
    const response = await apiMultipart.post('/meal', body);
    if (response.status !== 200) {
      throw new Error(`오류: ${response.status}`);
    }
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export default postMeal;
