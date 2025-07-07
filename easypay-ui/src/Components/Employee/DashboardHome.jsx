import React from 'react';
import PrimeEmployeeDashboard from './EmployeeDashboard';
import EmployeeDashboardPrime from './EmployeeDashboardPrime';

function DashboardHome() {
  const employeeId = 1; 

  return <EmployeeDashboardPrime employeeId={employeeId} />;
}

export default DashboardHome;
