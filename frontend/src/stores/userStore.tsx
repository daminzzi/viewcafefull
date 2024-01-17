import create from 'zustand';
import { deleteUserAPI } from '../services/deleteUser';
import { loginAPI } from '../services/postLogin';
import { logoutAPI } from '../services/getLogout';

type User = {
  id: string;
  name: string;
  phone: number;
  birth: number;
};

type UserState = {
  user: User | null;
  isAuthenticated: boolean;
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
  accessToken: null,
  setAccessToken: (token) => set({ accessToken: token }),
  setUser: (user) => set({ user }),

  login: async (form: { id: string; password: string }) => {
    try {
      const response = await loginAPI(form);
      set({ isAuthenticated: true, user: response.data.user, accessToken: response.data.accessToken});
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
      await logoutAPI(user.id);
      set({ isAuthenticated: false, user: null, accessToken: null });
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
      await deleteUserAPI();
      set({ isAuthenticated: false, user: null, accessToken: null });
    } catch (error) {
      console.error(error);
    }
  },
}));

export default useUserStore;
