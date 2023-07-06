import type { ILoginBody, IRegisterBody } from '@/types/auth.types';
import { ACCESS_TOKEN } from '@/enums/auth';
import { login, register, logout } from '@api/auth';

interface IAuthState {
  acccesToken: string;
  returnUrl?: URL;
}

export const useAuthStore = defineStore('authStore', {
  state: (): IAuthState => {
    return {
      acccesToken: localStorage.getItem(ACCESS_TOKEN) || ''
    };
  },
  getters: {
    loggedIn: ({ acccesToken }) => !!acccesToken
  },
  actions: {
    async login(body: ILoginBody): Promise<void> {
      try {
        const { data } = await login(body);
        this.acccesToken = data.token;
        localStorage.setItem(ACCESS_TOKEN, JSON.stringify(data.token));
        return Promise.resolve(data);
      } catch (error) {
        return Promise.reject(error);
      }
    },
    async register(body: IRegisterBody) {
      try {
        const { data } = await register(body);
        this.acccesToken = '';
        localStorage.removeItem(ACCESS_TOKEN);
        return Promise.reject(data);
      } catch (error) {
        return Promise.reject(error);
      }
    },
    async logout() {
      try {
        const { data } = await logout();
        return Promise.reject(data);
      } catch (error) {
        return Promise.reject(error);
      }
    }
  }
});