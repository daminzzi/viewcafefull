export type Medicine = {
  id: string;
  medicineId: string;
  medicineName: string;
  information: string;
  medicineType: string;
  medicineDate: string;
};

export type MedicineObj = {
  breakfast: Medicine | null;
  lunch: Medicine | null;
  dinner: Medicine | null;
};

export type MealObj = {
  breakfast: string;
  lunch: string;
  dinner: string;
};

export type HealthInfo = {
  beforeArr: Array<number>;
  afterArr: Array<number>;
  lowArr: Array<number>;
  highArr: Array<number>;
  medicineObj: MedicineObj;
  mealObj: MealObj;
};

export type VisitData = {
  applicationId: string;
  targetName: string;
  targetRoom: string;
  permissionId: string;
  createdDatetime: string;
  conferenceDate: string;
  conferenceTime: string;
  conferenceState: string;
  conferenceLink: string | null;
  startDatetime: string | null;
  endDatetime: string | null;
};
