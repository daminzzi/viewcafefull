// import { create } from 'zustand';
// import { WeekInfo, isSameDate } from './HealthStore';

// type WeekInfo = {
//   date: Date;
//   condition: ConditionInfo;
// };

// type MealImg = {
//   morning: string | null;
//   noon: string | null;
//   dinner: string | null;
// };

// type State = {
//   today: Date | null;
//   selectedDate: Date | null;
//   startOfWeek: Date | null;
//   week: Array<WeekInfo>;
//   mealImg: MealImg;
// };

// type Action = {
//   reset:
// };



// const useCaregiverMainStore = create<State & Action>((set) => ({

//   reset: () => {
//     set({
//       today: null,
//       selectedDate: null,
//       startOfWeek: null,
//       week: [],
//       mealImg: {
//         morning: null,
//         noon: null,
//         dinner: null,
//       },
//     })
//   }
// }));

// export default useCaregiverMainStore;
