import { ACCESS_TOKEN } from '@/constants';

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
    setAccessToken(token: string) {
      this.acccesToken = token;
      localStorage.setItem(ACCESS_TOKEN, this.acccesToken);
    },
    clearAccsessToken() {
      this.acccesToken = '';
      localStorage.removeItem(ACCESS_TOKEN);
    }
  }
});
