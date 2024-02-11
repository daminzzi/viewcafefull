import api from '../api';
import { useNavigate } from 'react-router-dom';

// access토큰 만료시 재발급
async function refreshAccessToken() {
  const navigate = useNavigate();
  try {
    const response = await api.post('/users/token');
    if (response.status === 200) {
    const newToken = response.data.accessToken;
    return newToken;
    } else {
      navigate('/login');
    }
  } catch (error) {
    console.error(error);
    navigate('/login');
    throw error;
  }
}

export default refreshAccessToken;
