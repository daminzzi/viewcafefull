import api from '../api';

async function postConnection(form: {
  domainId: string;
  nursingHome: string;
  targetCode: string;
  targetName: string;
  relationship: string;
}) {
  try {
    const body = {
      targetCode: form.targetCode,
      relationship: form.relationship,
    };
    const response = await api.post(`/user-link/${form.domainId}`, body);
    if (response.status !== 201) {
      throw new Error(`오류: ${response.status}`);
    }
    return;
  } catch (error) {
    console.log(error);
  }
}

export default postConnection;
