import axios from '../axios';

const user = () => ({
  async getCurrentUser() {
    return axios.get('/user/me');
  }
});

export const { getCurrentUser } = user();
