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
      text: '혈당',
    },
  },
};

const labels = ['아침', '점심', '저녁'];

function BloodSugar() {
  const { healthInfo } = useHealthStore();
  const data = {
    labels: labels,
    datasets: [
      {
        label: '식전 혈당',
        data: Object.values(healthInfo.before),
        borderWidth: 1,
        backgroundColor: '#D2B48C',
      },
      {
        label: '식후 혈당',
        data: Object.values(healthInfo.after),
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

export default BloodSugar;
