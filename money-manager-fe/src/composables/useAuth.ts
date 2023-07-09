import type { ILoginBody, IRegisterBody } from '@/types/auth.types';
import { login, register, logout, resetPassword } from '@api/auth';
import { useAuthStore } from '@/stores/auth';

export const useAuth = () => {
  const { setAccessToken, clearAccsessToken } = useAuthStore();

  return {
    async login(body: ILoginBody): Promise<void> {
      try {
        const { data } = await login(body);
        setAccessToken(data.data.accessToken);
        return Promise.resolve(data);
      } catch (error: any) {
        return Promise.reject(error.response.data);
      }
    },
    async register(body: IRegisterBody) {
      try {
        const { data } = await register(body);
        return Promise.resolve(data);
      } catch (error: any) {
        return Promise.reject(error.response.data);
      }
    },
    async logout() {
      try {
        const { data } = await logout();
        clearAccsessToken();
        return Promise.resolve(data);
      } catch (error) {
        return Promise.reject(error);
      }
    },
    async resetPassword(email: string) {
      try {
        const { data } = await resetPassword(email);
        return Promise.resolve(data);
      } catch (error: any) {
        console.log(error);
        return Promise.reject(error.response.data);
      }
    }
  };
};
