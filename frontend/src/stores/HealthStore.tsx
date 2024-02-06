import { create } from 'zustand';
import noImage from '../assets/images/noImage.jpg';

type State = {
  today: Date | null;
  tab: Page;
  selectedDate: Date | null;
  startOfWeek: Date | null;
  week: Array<Date>;
  healthInfo: HealthInfo;
};

type Action = {
  setToday: (date: Date) => void;
  setSelectedDate: (date: Date) => void;
  setTab: (tab: Page) => void;
  setStartOfWeek: (newStart: Date) => void;
  setWeek: (week: Array<Date>) => void;
  setHealthInfo: (healthInfo: HealthInfo) => void;
  reset: () => void;
};

const intialHealth: HealthInfoData = {
  morning: null,
  noon: null,
  dinner: null,
};

export const initialState: State = {
  today: null,
  tab: 'sum',
  selectedDate: null,
  startOfWeek: null,
  week: [],
  healthInfo: {
    low: { ...intialHealth },
    high: { ...intialHealth },
    before: { ...intialHealth },
    after: { ...intialHealth },
    medicine: { ...intialHealth },
    meal: { morning: noImage, noon: noImage, dinner: noImage },
  },
};

export const isSameDate = (date1: Date | null, date2: Date | null) => {
  if (!date1 || !date2) {
    return null;
  }
  return (
    date1.getFullYear() === date2.getFullYear() &&
    date1.getMonth() === date2.getMonth() &&
    date1.getDate() === date2.getDate()
  );
};

const useHealthStore = create<State & Action>((set, get) => ({
  ...initialState,

  setToday: (date: Date) => {
    set({ today: date });
  },

  setSelectedDate: (date: Date) => {
    set({ selectedDate: date });
    const start = new Date(date);
    start.setDate(start.getDate() - start.getDay());
    
    if (get().startOfWeek === null) {
      get().setStartOfWeek(start);
    } else if (get().startOfWeek !== null) {
      if (isSameDate(get().startOfWeek, start) === false) {
        get().setStartOfWeek(start);
      }
    }
  },

  setTab: (newTab: Page) => {
    set({ tab: newTab });
  },

  setStartOfWeek: (newStart: Date) => {
    set({ startOfWeek: newStart });
    const newWeek = [];
    for (let i = 0; i < 7; i++) {
      newWeek.push(new Date(newStart));
      newWeek[i].setDate(newWeek[i].getDate() + i);
    }
    set({ week: newWeek });
  },

  setWeek: (newWeek: Array<Date>) => {
    set({ week: newWeek });
  },

  setHealthInfo: (newHealthInfo: HealthInfo) => {
    set({ healthInfo: newHealthInfo });
  },

  reset: () => {
    set(initialState);
  },
}));

export default useHealthStore;
