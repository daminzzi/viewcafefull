import React from "react";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Bar } from "react-chartjs-2";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
);

type Props = {
  lowArr: Array<number>;
  highArr: Array<number>;
};

const options = {
  indexAxis: "y" as const,
  elements: {
    bar: {
      borderWidth: 2,
    },
  },
  responsive: true,
  plugins: {
    legend: {
      position: "top" as const,
    },
    title: {
      display: true,
      text: "혈압",
    },
  },
};

const label: string[] = ["아침", "점심", "저녁"];

function BloodPressure({ lowArr, highArr }: Props) {
  const data = {
    labels: label,
    datasets: [
      {
        label: "수축 혈압",
        data: lowArr,
        borderWidth: 1,
        backgroundColor: "#D2B48C",
      },
      {
        label: "이완 혈압",
        data: highArr,
        borderWidth: 1,
        backgroundColor: "#6EE7B7",
      },
    ],
  };

  return (
    <div>
      <p>혈압</p>
      <Bar options={options} data={data} />
    </div>
  );
}

export default BloodPressure;
