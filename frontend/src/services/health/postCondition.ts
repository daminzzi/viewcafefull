import apiApplicationJson from '../apiApplicationJson';

async function postCondition(date: string, condition: string) {
  try{
    const form = new FormData()
    form.append('date', date)
    form.append('condition', condition)
    const response = await apiApplicationJson.post('/condition', form);
    if ((response.status !== 200) && (response.status !== 201)) {
      throw new Error(`오류: ${response.status}`);
    }
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export default postCondition;