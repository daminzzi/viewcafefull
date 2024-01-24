type Medicine = {
  id: string;
  medicineId: string;
  medicineName: string;
  information: string;
  medicineType: string;
  medicineDate: string;
};

type MedicineObj = {
  breakfast: Medicine | null;
  lunch: Medicine | null;
  dinner: Medicine | null;
};

type MealObj = {
  breakfast: string;
  lunch: string;
  dinner: string;
};

type HealthInfo = {
  beforeArr: Array<number>;
  afterArr: Array<number>;
  lowArr: Array<number>;
  highArr: Array<number>;
  medicineObj: MedicineObj;
  mealObj: MealObj;
};
