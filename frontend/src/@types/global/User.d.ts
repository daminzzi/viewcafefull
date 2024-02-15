type User = {
  id: string;
  name: string;
  phoneNumber: string;
  birth: stirng;
  role: string;
};

type UserState = {
  user: User | null;
  role: 'Guardian' | 'Caregiver' | 'Hospital' | null;
  isAuthenticated: boolean;
  isLogin: boolean;
  accessToken: string | null;
  setAccessToken: (token: string | null) => void;
  setUser: (user: User | null) => void;
  login: (form: { id: string; password: string }) => void;
  logout: () => Promise<void>;
  deleteUser: () => void;
};

type UserConnectInfo = {
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
};

type PathType = 'app' | 'tar';
