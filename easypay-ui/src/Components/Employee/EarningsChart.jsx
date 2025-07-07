import { Chart } from 'primereact/chart';

function EarningsChart({ data }) {
  const chartData = {
    labels: data.map(e => e.month),
    datasets: [
      {
        label: 'Earnings',
        data: data.map(e => e.earnings),
        fill: false,
        borderColor: '#42A5F5',
        tension: 0.4
      }
    ]
  };

  const options = {
    plugins: {
      legend: { display: true }
    },
    responsive: true
  };

  return <Chart type="line" data={chartData} options={options} className="w-full" />;
}

export default EarningsChart;
