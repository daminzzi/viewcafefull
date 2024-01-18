import { create } from 'zustand';
import getConnectInfo, { PathType } from '../services/connect/getConnectInfo';
// import { persist } from 'zustand/middleware';

interface Response {
  applicationId: number,
  appDomainId: string,
  appName: string,
  targerId: number,
  tarDomainId: string,
  tarName: string,
  permissionId: number,
  perDomainId: string,
  perName: string,
  agreement: string,
  relationship: string,
}

type ConnectState = {
  connectArr: Array<Response>,
  currConnect: Response,
  setCurr: (index: number) => void,
  updateConnect: (type: PathType, domainId: string) => void,  
}

const useConnectStore = create<ConnectState>((set) => ({
  connectArr: [],
  currConnect: {
    applicationId: 0,
    appDomainId: '',
    appName: '',
    targerId: 0,
    tarDomainId: '',
    tarName: '',
    permissionId: 0,
    perDomainId: '',
    perName: '',
    agreement: '',
    relationship: '',
  },

  setCurr (index) { 
    set((state: any) => ({ currConnect: state.connectArr[index] }));
  },

  updateConnect (type, domainId) {
    set(() => ({ connectArr: getConnectInfo(type, domainId) }));
    set((state: any) => ({ currConnect: state.connectArr[0] }));
  },
}))

export default useConnectStore;