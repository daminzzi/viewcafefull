import { create } from 'zustand';
import { Subscriber } from 'openvidu-browser';

type StreamState = {
  subscriberList: Subscriber[];
  addSubscriber: (subscriber: Subscriber) => void;
  delSubscriber: (subscriber: Subscriber) => void;
  resetSubscriberList: () => void;
};

const useStreamStore = create<StreamState>((set, get) => ({
  subscriberList: [],
  addSubscriber: (subscriber: Subscriber) => {
    set({
      subscriberList: [...get().subscriberList, subscriber],
    });
  },
  delSubscriber: (subscriber: Subscriber) => {
    const subscribers = get().subscriberList;
    const index = subscribers.indexOf(subscriber, 0);
    if (index > -1) {
      subscribers.splice(index, 1);
      set({ subscriberList: subscribers });
    }
  },
  resetSubscriberList: () => {
    set({ subscriberList: [] });
  },
}));

export default useStreamStore;
