import { create } from 'zustand';

type State = {
  galleryInfo: Array<GalleryData>;
  page: number;
  isLoading: boolean;
  isCallable: boolean;
};

type Action = {
  switchIsLoading: () => void;
  switchIsCallable: () => void;
  addPage: () => void;
  addInfo: (newInfo: Array<GalleryData>) => void;
  reset: () => void;
};

const initialState: State = {
  galleryInfo: [],
  page: 1,
  isLoading: false,
  isCallable: true,
};

const useGalleryStore = create<State & Action>((set, get) => ({
  ...initialState,

  switchIsLoading: () => {
    set((state) => ({ isLoading: !state.isLoading }));
  },

  switchIsCallable: () => {
    set((state) => ({ isCallable: !state.isCallable }));
  },

  addPage: () => {
    set((state) => ({ page: state.page + 1 }));
  },

  addInfo: (newInfo) => {
    const newGallery = [...get().galleryInfo]
    if (newGallery.length > 0 && newGallery.at(-1)?.date === newInfo[0].date){
      const last = newGallery.length - 1;
      newGallery[last].images = newGallery[last].images.concat(newInfo[0].images)
      newGallery[last].thumnail = newGallery[last].thumnail.concat(newInfo[0].thumnail)
      set((state) => ({galleryInfo: state.galleryInfo.concat(newInfo.slice(1))}))
    } else {
      set((state) => ({ galleryInfo: state.galleryInfo.concat(newInfo) }));
    }
  },

  reset: () => {
    set(initialState);
  },
}));

export default useGalleryStore;
