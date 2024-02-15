type ConnectResponse = {
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
  updateConnect: (type: PathType, domainId: string) => Promise<Array<ConnectResponse>>;
  reset: () => void;
};
