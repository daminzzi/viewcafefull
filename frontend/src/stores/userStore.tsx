import { create } from 'zustand';
import deleteUser  from '../services/user/deleteUser';
import  postLogin  from '../services/user/postLogin';
import getLogout from '../services/user/getLogout';

export interface User{
  id: string;
  name: string;
  phoneNumber: number;
  birth: number;
  role: string;
};

type UserState = {
  user: User | null;
  isAuthenticated: boolean;
  isLogin: boolean;
  accessToken: string | null;
  setAccessToken: (token: string | null) => void;
  setUser: (user: User | null) => void;
  login: (form: { id: string; password: string }) => void;
  logout: () => void;
  deleteUser: () => void;
};

const useUserStore = create<UserState>((set,get) => ({
  user: null,
  isAuthenticated: false,
  isLogin: false,
  accessToken: null,
  setAccessToken: (token) => set({ accessToken: token }),
  setUser: (user) => set({ user }),

  login: async (form: { id: string; password: string }) => {
    try {
      const response = await postLogin(form);
      set({ isLogin: true, isAuthenticated: true, user: response.data.user, accessToken: response.data.accessToken});
    } catch (error) {
      console.error(error);
    }
  },

  logout: async () => {
    const { user } = get();
    if (!user) {
      console.error('로그인 해주세요.');
      return;
    }
    try {
      await getLogout(user.id);
      set({ isLogin: false, isAuthenticated: false, user: null, accessToken: null });
    } catch (error) {
      console.error(error);
    }
  },
  

  deleteUser: async () => {
    const { user } = get();
    if (!user) {
      console.error('로그인 해주세요.');
      return;
    }
    try {
      await deleteUser();
      set({ isLogin: false, isAuthenticated: false, user: null, accessToken: null });
    } catch (error) {
      console.error(error);
    }
  },
}));

export default useUserStore;
