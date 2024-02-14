import { create } from 'zustand';
import getCondition from '../services/health/getCondition';
import noImage from '../assets/images/noImage.jpg';

export type WeekInfo = {
  date: Date;
  condition: ConditionInfo;
};

type State = {
  today: Date | null;
  tab: Page;
  selectedDate: Date | null;
  startOfWeek: Date | null;
  week: Array<WeekInfo>;
  healthInfo: HealthInfo;
};

type Action = {
  setToday: (date: Date) => void;
  setSelectedDate: (date: Date) => void;
  setTab: (tab: Page) => void;
  setStartOfWeek: (newStart: Date) => void;
  setWeek: (week: Array<WeekInfo>) => void;
  setHealthInfo: (healthInfo: HealthInfo) => void;
  reset: () => void;
  resetHealthInfo: () => void;
  refreshWeek: () => void;
};

export const initialHealth: HealthInfoData = {
  morning: null,
  noon: null,
  dinner: null,
};

export const initialImage: HealthInfoData = {
  morning: noImage,
  noon: noImage,
  dinner: noImage,
};

export const initialState: State = {
  today: null,
  tab: 'sum',
  selectedDate: null,
  startOfWeek: null,
  week: [],
  healthInfo: {
    low: { ...initialHealth },
    high: { ...initialHealth },
    before: { ...initialHealth },
    after: { ...initialHealth },
    medicine: { ...initialHealth },
    meal: { ...initialImage },
  },
};

export function isSameDate(date1: Date | null, date2: Date | null) {
  if (!date1 || !date2) {
    return null;
  }
  return (
    date1.getFullYear() === date2.getFullYear() &&
    date1.getMonth() === date2.getMonth() &&
    date1.getDate() === date2.getDate()
  );
}

export function dateToString(date: Date): string {
  const year: number = date.getFullYear();
  const month: string = (date.getMonth() + 1).toString().padStart(2, '0');
  const day: string = date.getDate().toString().padStart(2, '0');

  return `${year}${month}${day}`;
}

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

  setStartOfWeek: async (newStart: Date) => {
    set({ startOfWeek: newStart });
    const endOfWeek = new Date(newStart);
    endOfWeek.setDate(endOfWeek.getDate() + 6);
    const response = await getCondition(
      dateToString(newStart),
      dateToString(endOfWeek),
    );
    const newWeek = [];
    for (let i = 0; i < 7; i++) {
      const date = new Date(newStart);
      date.setDate(date.getDate() + i);
      const newInfo = {
        date,
        condition: response[i],
      };
      newWeek.push(newInfo);
    }
    set({ week: newWeek });
  },

  refreshWeek: async () => {
    const start = get().startOfWeek
    if (start !== null) {
      const end = new Date(start);
      end.setDate(end.getDate() + 6);
      const response = await getCondition(
        dateToString(start),
        dateToString(end),
      )
      const newWeek = [];
      for (let i = 0; i < 7; i++) {
        const date = new Date(start);
        date.setDate(date.getDate() + i);
        const newInfo = {
          date,
          condition: response[i],
        };
        newWeek.push(newInfo);
      }
      set({ week: newWeek });
    }
  },

  setWeek: (newWeek: Array<WeekInfo>) => {
    set({ week: newWeek });
  },

  setHealthInfo: (newHealthInfo: HealthInfo) => {
    set({ healthInfo: newHealthInfo });
  },

  reset: () => {
    set(initialState);
    get().resetHealthInfo();
  },

  resetHealthInfo: () => {
    set({
      healthInfo: {
        low: { ...initialHealth },
        high: { ...initialHealth },
        before: { ...initialHealth },
        after: { ...initialHealth },
        medicine: { ...initialHealth },
        meal: { ...initialImage },
      },
    });
  },
}));

export default useHealthStore;
