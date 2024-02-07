import React from 'react';
import styled from 'styled-components';
import useHealthStore from '../../stores/HealthStore';
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

const Container = styled.div`
  width: 90%;
  height: 50vh;
`;

const options = {
  maintainAspectRatio: false,
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
      text: '혈압',
    },
  },
};

const label: string[] = ['아침', '점심', '저녁'];

function BloodPressure() {
  const { healthInfo } = useHealthStore();
  const data = {
    labels: label,
    datasets: [
      {
        label: '수축 혈압',
        data: Object.values(healthInfo.low),
        borderWidth: 1,
        backgroundColor: '#D2B48C',
      },
      {
        label: '이완 혈압',
        data: Object.values(healthInfo.high),
        borderWidth: 1,
        backgroundColor: '#6EE7B7',
      },
    ],
  };

  return (
    <Container>
      <Bar options={options} data={data} />
    </Container>
  );
}

export default BloodPressure;
