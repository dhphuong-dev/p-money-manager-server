import type { ILoginBody, IRegisterBody } from '@/types/auth.types';
import axios from '../axios';

const auth = () => ({
  async login(body: ILoginBody) {
    return axios.post('/auth/login', body);
  },
  async register(body: IRegisterBody) {
    return axios.post('/auth/register', {
      fullName: body.fullName,
      email: body.email,
      password: body.password
    });
  },
  async logout() {
    return axios.get('/auth/logout');
  }
});

export const { login, register, logout } = auth();
