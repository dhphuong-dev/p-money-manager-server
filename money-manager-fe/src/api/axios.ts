import type { AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import axios from 'axios';
import router from '@/router';
import { ACCESS_TOKEN } from '@/constants';

const axiosIns = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

axiosIns.interceptors.request.use(
  function (config: InternalAxiosRequestConfig) {
    const token = `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`;
    config.headers.Authorization = token;
    return config;
  },
  function (error) {
    return Promise.reject(error);
  }
);

axiosIns.interceptors.response.use(
  function (response: AxiosResponse<any>) {
    return response;
  },
  function (error) {
    if (error.response.status == 401) {
      localStorage.removeItem(ACCESS_TOKEN);
      router.push('/login');
    }
    return Promise.reject(error);
  }
);

export default axiosIns;
