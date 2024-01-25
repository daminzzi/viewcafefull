interface User {
  id: string;
  name: string;
  phoneNumber: number;
  birth: number;
  role: string;
}

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

interface UserInfo {
  parentId: number[] | null;
  parentName: string[] | null;
  residentId: number | null;
  residentName: string | null;
  hospitalId: number;
  hospitalName: string;
  phoneNumber: string;
  birth: string;
  userRole: string;
}

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
