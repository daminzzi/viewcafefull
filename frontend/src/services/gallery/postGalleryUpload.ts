import apiMultipart from '../apiMultipart';

async function postGalleryUpload(body: FormData) {
  try {
    const response = await apiMultipart.post('/gallery', body);
    if (response.status !== 201) {
      throw new Error(`오류: ${response.status}`);
    }
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export default postGalleryUpload;
