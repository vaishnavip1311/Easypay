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
import EditProfile from './Components/Employee/EditProfile.jsx';
import ManageEmployees from './Components/HR_Manager/ManageEmployees.jsx';
import ManageJobtitles from './Components/HR_Manager/ManageJobtitles.jsx';
import ManagePayrollPolicy from './Components/HR_Manager/ManagePayrollPolicy.jsx';
import HRStats from './Components/HR_Manager/HRStats.jsx';
import UpdateHRProfile from './Components/HR_Manager/UpdateHRProfile.jsx';
import OnboardingForm from './Components/HR_Manager/OnboardingForm.jsx';
import AddJobTitle from './Components/HR_Manager/AddJobTitle.jsx';
import AddPayrollPolicy from './Components/HR_Manager/AddPayrollPolicy.jsx';
import DashboardHome from './Components/Employee/DashboardHome.jsx';
import EmployeeDashboardPrime from './Components/Employee/EmployeeDashboardPrime.jsx';
import SalaryDetails from './Components/Employee/SalaryDetails.jsx';
import HrDashboard from './Components/HR_Manager/HrDashboard.jsx';
import HrProfileCard from './Components/HR_Manager/HrProfileCard.jsx';
import ProcessorDashboard from './Components/Payroll_Processor/ProcessorDashboard.jsx';
import ProcessorProfileCard from './Components/Payroll_Processor/ProcessorProfilecard.jsx';
import ManageBenefits from './Components/Payroll_Processor/ManageBenefits.jsx';
import AddBenefit from './Components/Payroll_Processor/AddBenefit.jsx';
import ViewAllPayrolls from './Components/Payroll_Processor/ViewAllPayrolls.jsx';
import PayrollDetail from './Components/Payroll_Processor/PayrollDetail.jsx';
import EditBenefit from './Components/Payroll_Processor/EditBenefit.jsx';
import GeneratePayroll from './Components/Payroll_Processor/GeneratePayroll.jsx';
import EditJobTitle from './Components/HR_Manager/EditJobTitle.jsx';

function App() {

  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Login />}></Route>
          <Route path="/employee" element={<EmployeeDashboard />}>
            <Route index element={<DashboardHome />}></Route>
            <Route path='profile' element={<Profile />}></Route>
            <Route path='edit-profile' element={<EditProfile />}></Route>
            <Route path='details' element={<ViewProfile />}></Route>
            <Route path='edit-details' element={<UpdateProfile />}></Route>
            <Route path='manage-leave' element={<ManageLeave />}></Route>
            <Route path='leave-details/:lid' element={<LeaveDetails />}></Route>
            <Route path='apply-leave' element={<ApplyLeave />}></Route>
            <Route path='submit-timesheets' element={<SubmitTimesheets />}></Route>
            <Route path='view-timesheets' element={<ViewTimesheets />}></Route>
            <Route path='view-salary' element={<ViewSalary />}></Route>
            <Route path='salary-details/:sid' element={<SalaryDetails />}></Route>
          </Route>
          <Route path="/hr-manager" element={<HRManagerDashboard />}>
          <Route index element={<HrDashboard />}></Route>
          <Route path="update-profile" element={<UpdateHRProfile />}></Route>
          <Route path="hr-profile" element={<HrProfileCard />}></Route>
          <Route path="add-employee" element={<OnboardingForm />}></Route>
          <Route path="add-jobtitle" element={<AddJobTitle />}></Route>
          <Route path="edit-jobtitle/:id" element={<EditJobTitle />}></Route>
          <Route path="manage-employees" element={<ManageEmployees />}></Route>
          <Route path="manage-jobtitles" element={<ManageJobtitles />}></Route>
          <Route path="manage-payroll-policy" element={<ManagePayrollPolicy />}></Route>
          <Route path="add-payroll-policy" element={<AddPayrollPolicy />}></Route>
          </Route>
          <Route path="/payroll-processor" element={<ProcessorDashboard />}>
          <Route index element={<PayrollProcessorDashboard />}></Route>
          <Route path="processor-profile" element={<ProcessorProfileCard />}></Route>
          <Route path="manage-benefits" element={<ManageBenefits />}></Route>
          <Route path="add-benefit" element={<AddBenefit />}></Route>
          <Route path="view-payrolls" element={<ViewAllPayrolls />}></Route>
          <Route path="generate-payroll" element={<GeneratePayroll />}></Route>
          <Route path="payroll/:id" element={<PayrollDetail />} />
          <Route path="benefit/edit/:id" element={<EditBenefit />} />
          </Route>
          <Route path="/supervisor" element={<SupervisorDashboard />}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App
