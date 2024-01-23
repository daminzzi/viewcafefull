import React from 'react';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Bar } from 'react-chartjs-2';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
);

type Props = {
  beforeArr: Array<number>;
  afterArr: Array<number>;
};

const options = {
  indexAxis: 'y' as const,
  elements: {
    bar: {
      borderWidth: 2,
    },
  },
  responsive: true,
  plugins: {
    legend: {
      position: 'top' as const,
    },
    title: {
      display: true,
      text: '혈당',
    },
  },
};

const label: string[] = ['아침', '점심', '저녁'];

function BloodSugar({ beforeArr, afterArr }: Props) {
  const data = {
    labels: label,
    datasets: [
      {
        label: '식전 혈당',
        data: beforeArr,
        borderWidth: 1,
        backgroundColor: '#D2B48C',
      },
      {
        label: '식후 혈당',
        data: afterArr,
        borderWidth: 1,
        backgroundColor: '#6EE7B7',
      },
    ],
  };

  return (
    <div>
      <p>혈당</p>
      <Bar options={options} data={data} />
    </div>
  );
}

export default BloodSugar;
