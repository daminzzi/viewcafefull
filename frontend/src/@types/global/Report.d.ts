type ReportInfo = {
  year: number;
  month: number;
  permission: string;
  target: string;
  lifeInfo: string;
  movie: string;
  message: string;
  pressure: {
    insights: {
      early: string;
      mid: string;
      late: string;
    };
    data: {
      day: string;
      low: number;
      high: number;
    }[];
  };
  sugar: {
    insights: {
      early: string;
      mid: string;
      late: string;
    };
    data: {
      day: string;
      low: number;
      high: number;
    }[];
  };
  condition: {
    good: number;
    normal: number;
    bad: number;
  };
};
