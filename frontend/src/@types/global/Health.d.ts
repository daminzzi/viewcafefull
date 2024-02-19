type Page = 'sum' | 'bs' | 'bp' | 'mm';

type HealthData = {
  level: string;
  time: string;
};

type Health = {
  healthType: string;
  data: Data;
};

type Medicine = {
  morning: boolean;
  noon: boolean;
  dinner: boolean;
};

type ImagesData = {
  url: string;
  time: string;
};

type Meal = {
  images: Array<ImagesData>;
};

type HealthResponse = {
  id: string;
  health: Array<Health>;
  medicine: Medicine;
  meal: meal;
};

type HealthInfoData = {
  morning: string | boolean | number | null;
  noon: string | boolean | number | null;
  dinner: string | boolean | number | null;
};

type HealthInfo = {
  low: HealthInfoData;
  high: HealthInfoData;
  before: HealthInfoData;
  after: HealthInfoData;
  medicine: HealthInfoData;
  meal: HealthInfoData;
};

type ConditionInfo = {
  date: string;
  data: '좋음' | '보통' | '나쁨' | null;
}
