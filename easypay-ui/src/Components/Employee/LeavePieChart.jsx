import { Chart } from 'primereact/chart';

function LeavePieChart({ data }) {
  const chartData = {
    labels: data.map(d => d.type),
    datasets: [
      {
        data: data.map(d => d.count),
        backgroundColor: ['#42A5F5', '#66BB6A', '#FFA726', '#EF5350'],
        hoverBackgroundColor: ['#64B5F6', '#81C784', '#FFB74D', '#E57373']
      }
    ]
  };

  const options = {
    plugins: {
      legend: { position: 'bottom' }
    },
    responsive: true
  };

  return <Chart type="pie" data={chartData} options={options} className="w-full" />;
}

export default LeavePieChart;
