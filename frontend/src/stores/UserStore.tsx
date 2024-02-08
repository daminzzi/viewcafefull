import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import deleteUser from '../services/user/deleteUser';
import postLogin from '../services/user/postLogin';
import getLogout from '../services/user/getLogout';
import useHealthStore from './HealthStore';
import useGalleryStore from './GalleryStore';

const useUserStore = create<UserState>()(
  persist(
    (set, get): UserState => ({
      user: null,
      isAuthenticated: false,
      isLogin: false,
      accessToken: null,
      setAccessToken: (token) => set({ accessToken: token }),
      setUser: (user) => set({ user }),

      login: async (form: { id: string; password: string }) => {
        try {
          const response = await postLogin(form);
          if (response.data.user && response.data.accessToken) {
            set({
              isLogin: true,
              isAuthenticated: true,
              user: response.data.user,
              accessToken: response.data.accessToken,
            });
            return true;
          } else {
            throw new Error(
              '로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.',
            );
          }
        } catch (error) {
          throw error;
        }
      },

      logout: async () => {
        const { user } = get();
        const healthStore = useHealthStore();
        const galleryStore = useGalleryStore();

        if (!user) {
          console.error('로그인 해주세요.');
          return;
        }
        try {
          await getLogout(user.id);
          set({
            isLogin: false,
            isAuthenticated: false,
            user: null,
            accessToken: null,
          });
          healthStore.reset(); // 로그아웃시 건강 정보 store reset
          galleryStore.reset(); // 로그아웃시 갤러리 store reset
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
          set({
            isLogin: false,
            isAuthenticated: false,
            user: null,
            accessToken: null,
          });
        } catch (error) {
          console.error(error);
        }
      },
    }),
    {
      name: 'user-storage',
    },
  ),
);

export default useUserStore;
