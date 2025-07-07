import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Card } from 'primereact/card';
import { Divider } from 'primereact/divider';
import DashboardCard from './DashboardCard';
import EarningsChart from './EarningsChart';
import LeavePieChart from './LeavePieChart';

function EmployeeDashboardPrime({ employeeId }) {
  const [stats, setStats] = useState(null);

  useEffect(() => {
     axios.get(`http://localhost:8081/api/employee/dashboard/${employeeId}`,
                { headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') } }
     )
      .then(res => setStats(res.data))
      .catch(err => console.error(err));
  }, [employeeId]);

  if (!stats) return <p className="p-4">Loading...</p>;

  return (
    <div className="p-4">
      <h2 className="mb-3">Employee Dashboard</h2>

      <div className="grid">
        <DashboardCard title="Total Earnings" value={`₹${stats.monthlyEarnings.toFixed(2)} / ₹${stats.ytdEarnings.toFixed(2)}`} icon="pi pi-wallet" />
        <DashboardCard title="Leaves" value={`${stats.leavesAvailable} Available / ${stats.leavesTaken} Taken`} icon="pi pi-calendar" />
        <DashboardCard title="Next Pay Date" value={stats.nextPayDate} icon="pi pi-clock" />
        <DashboardCard title="Last Payment" value={`₹${stats.lastPaymentAmount} (${stats.lastPaymentDate})`} icon="pi pi-file" />
        <DashboardCard title="Pending Leave Requests" value={stats.pendingLeaveRequests} icon="pi pi-envelope" />
        <DashboardCard title="Working Days Logged" value={`${stats.workingDaysLogged} / 22`} icon="pi pi-briefcase" />
      </div>

      <Divider />

      <div className="chart-row">
        <div className="chart-card">
          <Card title="📈 Earnings Trend">
            <EarningsChart data={stats.earningsTrend} />
          </Card>
        </div>
        <div className="chart-card">
          <Card title="🍩 Leave Breakdown">
            <LeavePieChart data={stats.leaveBreakdown} />
          </Card>
        </div>
      </div>
    </div>
  );
}

export default EmployeeDashboardPrime;
