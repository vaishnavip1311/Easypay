import { Card } from 'primereact/card';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { Chart } from 'primereact/chart';
import './HrDashboard.css';

function HrDashboard() {
    const [stats, setStats] = useState(null);

    useEffect(() => {
        const getStats = async () => {

            try {
                let response = await axios.get(`http://localhost:8081/api/hr-manager/dashboard/summary`,
                    { headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') } }
                )
                setStats(response.data);
            }
            catch (err) {
                console.log(err)
            }
        }

        getStats();
    }, []);

    if (!stats) return <p>Loading...</p>;

    const formatter = new Intl.NumberFormat('en-IN', {
        style: 'currency',
        currency: 'INR',
        minimumFractionDigits: 2
    });

    const pieData = {
        labels: Object.keys(stats.employeeTypeDistribution),
        datasets: [{
            data: Object.values(stats.employeeTypeDistribution),
            backgroundColor: ['#42A5F5', '#66BB6A', '#FFA726', '#AB47BC']
        }]
    };

    return (
        <div className="hr-dashboard-container">
            <h2 className="hr-dashboard-header">HR Manager Dashboard</h2>

            <div className="hr-stats">
                <Card className="hr-stat-card">
                    <div className="stat-inline">
                        <span className="stat-title">👥 Total Employees</span>
                        <span className="stat-value">{stats.totalEmployees}</span>
                    </div>
                </Card>

                <Card className="hr-stat-card">
                    <div className="stat-inline">
                        <span className="stat-title">📚 Active Policies</span>
                        <span className="stat-value">{stats.activePolicies}</span>
                    </div>
                </Card>

                <Card className="hr-stat-card">
                    <div className="stat-inline">
                        <span className="stat-title">💸 Payrolls Processed <br></br>(This Month)</span>
                        <span className="stat-value">₹{formatter.format(stats.monthlyPayrollProcessed || 0)}</span>
                    </div>
                </Card>
            </div>

            <div className="employee-type-chart-wrapper">
                <Card className="chart-card">
                    <h3>📄 Employee Type Breakdown</h3>
                    <Chart type="pie" data={pieData} />
                </Card>
            </div>

        </div>
    );
}

export default HrDashboard;
