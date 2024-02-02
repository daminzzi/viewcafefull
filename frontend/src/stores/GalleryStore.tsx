import { create } from 'zustand';

type State = {
  galleryInfo: Array<Data>;
  page: number;
  isLoading: boolean;
  isCallable: boolean;
};

type Action = {
  switchIsLoading: () => void;
  switchIsCallable: () => void;
  addPage: () => void;
  addInfo: (newInfo: Array<Data>) => void;
  reset: () => void;
};

const initialState: State = {
  galleryInfo: [],
  page: 1,
  // target: null,
  isLoading: false,
  isCallable: true,
};

const useGalleryStore = create<State & Action>((set) => ({
  ...initialState,

  switchIsLoading: () => {
    set((state) => ({ isLoading: !state.isLoading }));
  },

  switchIsCallable: () => {
    set((state) => ({ isCallable: !state.isCallable }));
  },

  addPage: () => {
    set((state) => ({page: state.page + 1}));
  },

  addInfo: (newInfo) => {
    set((state) => ({ galleryInfo: state.galleryInfo.concat(newInfo)}))
  },

  reset: () => {
    set(initialState);
  },
}));

export default useGalleryStore;
