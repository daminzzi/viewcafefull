import { create } from 'zustand';
import getConnectInfo, { PathType } from '../services/connect/getConnectInfo';
// import { persist } from 'zustand/middleware';

interface Response {
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
  connectArr: Array<Response>;
  currConnect: Response;
  setCurr: (index: number) => void;
  updateConnect: (type: PathType, domainId: string) => void;
};

const useConnectStore = create<ConnectState>((set) => ({
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

  updateConnect(type, domainId) {
    set(() => ({ connectArr: getConnectInfo(type, domainId) }));
    set((state) => ({ currConnect: state.connectArr[0] }));
  },
}));

export default useConnectStore;
