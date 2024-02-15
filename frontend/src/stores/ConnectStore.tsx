import { create } from 'zustand';
import getConnectInfo from '../services/connect/getConnectInfo';
import { persist } from 'zustand/middleware';

const useConnectStore = create<ConnectState>()(
  persist(
    (set): ConnectState => ({
      connectArr: [],
      currConnect: {
        applicationId: '0',
        appDomainId: '',
        appName: '',
        targetId: '0',
        tarDomainId: '',
        tarName: '',
        permissionId: '0',
        perDomainId: '',
        perName: '',
        agreement: '',
        relationship: '',
      },

      setCurr(index) {
        set((state) => ({ currConnect: state.connectArr[index] }));
      },

      async updateConnect(type, domainId) {
        const newConnectArr = await getConnectInfo(type, domainId);
        set({ connectArr: newConnectArr });
        set((state) => ({ currConnect: state.connectArr[0] }));
        return newConnectArr;
      },

      reset() {
        set({
          connectArr: [],
          currConnect: {
            applicationId: '0',
            appDomainId: '',
            appName: '',
            targetId: '0',
            tarDomainId: '',
            tarName: '',
            permissionId: '0',
            perDomainId: '',
            perName: '',
            agreement: '',
            relationship: '',
          },
        });
      }
    }),
    {
      name: 'connect-storage',
    },
  ),
);

export default useConnectStore;
