// import api from '../api';
import page1 from './tGallery1.json';
import page2 from './tGallery2.json';
import nopage from './tGallery3.json'

// async function getGallery(page: number): Gallery{
//   try {
//     const response = await api.get('/gallery', { params: { page: page } });
//     if (response.status !== 200) {
//       throw new Error(`오류: ${response.status}`);
//     }
//     return response.data;
//   } catch (err) {
//     console.log(err);
//     throw err;
//   }
// }

function getGallery(page: number): Gallery{
  if (page === 1) {
    return page1;
  } else if (page === 2) {
    return page2;
  }
  return nopage;
}

export default getGallery;
