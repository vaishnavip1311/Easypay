import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import { useEffect, useState } from 'react';
import axios from 'axios';
import './PayrollProcessorDashboard.css';

function PayrollProcessorDashboard() {
  const [stats, setStats] = useState(null);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const response = await axios.get('http://localhost:8081/api/payroll-processor/dashboard', {
          headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
        });
        setStats(response.data);
      } catch (error) {
        console.error('Dashboard error:', error);
      }
    };

    fetchStats();
  }, []);

  if (!stats) return <p>Loading...</p>;

  const barData = {
    labels: stats.months,
    datasets: [
      {
        label: 'Payroll Amount',
        data: stats.monthlyPayrolls,
        backgroundColor: '#64b5f6',
      },
    ],
  };

  const pieData = {
    labels: Object.keys(stats.benefitBreakdown),
    datasets: [
      {
        data: Object.values(stats.benefitBreakdown),
        backgroundColor: ['#66bb6a', '#ffa726', '#ab47bc', '#29b6f6'],
      },
    ],
  };

  return (
    <div className="processor-dashboard-container">
      <h2 className="processor-dashboard-header">Payroll Processor Dashboard</h2>

      <div className="processor-stats">
        <Card className="processor-card">
          <div className="stat-inline">
            <span>🧮 Payrolls Calculated</span>
            <span>{stats.payrollsCalculated}</span>
          </div>
        </Card>

        <Card className="processor-card">
          <div className="stat-inline">
            <span>✅ Payrolls Verified</span>
            <span>{stats.verifiedPayrolls}</span>
          </div>
        </Card>

        <Card className="processor-card">
          <div className="stat-inline">
            <span>💼 Total Benefits</span>
            <span>{stats.totalBenefits}</span>
          </div>
        </Card>
      </div>

      <div className="processor-charts">
        <Card className="chart-card">
          <h3>📊 Monthly Payroll Trends</h3>
          <Chart type="bar" data={barData} />
        </Card>

        <Card className="chart-card">
          <h3>📈 Benefits Cost Breakdown</h3>
          <Chart type="pie" data={pieData} />
        </Card>
      </div>
    </div>
  );
}

export default PayrollProcessorDashboard;
