import { create } from 'zustand' 

type MealImg = {
  time: string;
  url: string;
}
type MealType = {
  morning: string | null;
  lunch: string | null;
  dinner: string | null;
}

type State = {
  condition: string | null;
  morning: string | null;
  lunch: string | null;
  dinner: string | null;
}

type Action = {
  setCondition: (newCon: string) => void;
  setMeal: (images: Array<MealImg>) => void;
  reset: () => void;
}

const useCareGiverMainStore = create<State & Action>((set) => ({
  condition: null,
  morning: null,
  lunch: null,
  dinner: null,

  setCondition: (newCon: string) => {
    set({condition: newCon})
  },

  setMeal: (newImages: Array<{time: string, url: string}>) => {
    const newMeal: MealType = {
      morning: null,
      lunch: null,
      dinner: null 
    }
    newImages.forEach((image) => {
      switch (image.time) {
        case '아침':
          newMeal.morning = image.url;
          break;
        case '점심':
          newMeal.lunch = image.url
          break;
        case '저녁':
          newMeal.dinner = image.url
          break;
        default:
          break;
      }
    })
    set({...newMeal})
  },

  reset: () => {
    set({
      condition: null,
      morning: null,
      lunch: null,
      dinner: null,
    });
  },
}))

export default useCareGiverMainStore;