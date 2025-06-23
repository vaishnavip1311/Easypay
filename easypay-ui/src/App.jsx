import { useState } from 'react'
import Login from './Components/Login.jsx'
//import logo from './assets/logo.png';

import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import EmployeeDashboard from './Components/Employee/EmployeeDashboard.jsx';
import HRManagerDashboard from './Components/HR_Manager/HRManagerDashboard.jsx';
import PayrollProcessorDashboard from './Components/Payroll_Processor/PayrollProcessorDashboard.jsx';
import SupervisorDashboard from './Components/Supervisor/SupervisorDashboard.jsx';
import ViewProfile from './Components/Employee/ViewProfile.jsx';
import UpdateProfile from './Components/Employee/UpdateProfile.jsx';
import ManageLeave from './Components/Employee/ManageLeave.jsx';
import SubmitTimesheets from './Components/Employee/SubmitTimesheets.jsx';
import Stats from './Components/Employee/Stats.jsx';
import ViewSalary from './Components/Employee/ViewSalary.jsx';
import LeaveDetails from './Components/Employee/LeaveDeatails.jsx';
import ApplyLeave from './Components/Employee/ApplyLeave.jsx';
import ViewTimesheets from './Components/Employee/ViewTimesheets.jsx';
import Profile from './Components/Employee/Profile.jsx';

function App() {

  return (
    <div>
      <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />}></Route>
        <Route path="/employee" element={<EmployeeDashboard />}>
        <Route index element={<Stats />}></Route>
        <Route path='profile' element={<Profile />}></Route>
        <Route path='details' element={<ViewProfile />}></Route>
        <Route path='edit-details' element={<UpdateProfile />}></Route>
        <Route path='manage-leave' element={<ManageLeave />}></Route>
        <Route path='leave-details/:lid' element={<LeaveDetails />}></Route>
        <Route path='apply-leave' element={<ApplyLeave />}></Route>
        <Route path='submit-timesheets' element={<SubmitTimesheets />}></Route>
        <Route path='view-timesheets' element={<ViewTimesheets />}></Route>
        <Route path='view-salary' element={<ViewSalary />}></Route>
        </Route>
        <Route path="/hr-manager" element={<HRManagerDashboard />}></Route>
        <Route path="/payroll-processor" element={<PayrollProcessorDashboard />}></Route>
        <Route path="/supervisor" element={<SupervisorDashboard />}></Route>
      </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App
