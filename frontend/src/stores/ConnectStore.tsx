import { create } from 'zustand';
import getConnectInfo from '../services/connect/getConnectInfo';
import { persist } from 'zustand/middleware';

export interface ConnectResponse {
  applicationId: string;
  appDomainId: string;
  appName: string;
  targetId: string;
  tarDomainId: string;
  tarName: string;
  permissionId: string;
  perDomainId: string;
  perName: string;
  agreement: string;
  relationship: string;
}

type ConnectState = {
  connectArr: Array<ConnectResponse>;
  currConnect: ConnectResponse;
  setCurr: (index: number) => void;
  updateConnect: (type: PathType, domainId: string) => void;
};

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
      },
    }),
    {
      name: 'connect-storage',
    },
  ),
);

export default useConnectStore;
