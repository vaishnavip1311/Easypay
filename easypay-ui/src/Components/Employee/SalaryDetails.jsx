import { Link, useParams } from "react-router-dom";
import "./SalaryDetails.css"
import { useEffect, useState } from "react";
import axios from "axios";

function SalaryDetails() {

    const params = useParams();
    const[salary , setSalary] = useState({});

    useEffect (()=>{
        const getSalary = async()=>{
            try {
              const response = await axios.get('http://localhost:8081/api/payroll/get-one/' + params.sid,
                    { headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') } }
                );
                //console.log(response.data)
                setSalary(response.data);
            
            } catch (error) {
               console.log(error);
            }
        }
        getSalary();
    },[])

    return (
    <div className="salary-breakdown-container">
        <h2>Salary Breakdown</h2>
        <div className="salary-details-card">
            <div className="card-text">Employee Id:  {salary.employee?.id}</div>
            <div className="card-text">Name:  {salary.employee?.id}</div>
            <div className="card-text">Pay Period:  {salary.payPeriod}</div>
            <div className="card-text">Processed On:  {salary.processedOn}</div>
        </div>
      <div className="salary-breakdown-row">
        <div className="salary-card earning-card">
          <h3>🟢 Earnings Breakdown</h3>
          <p>Basic Salary: ₹{salary.basicSalary}</p>
          <p>HRA: ₹{salary.hra}</p>
          <p>DA: ₹{salary.da}</p>
          <p>Other Allowances: ₹{salary.otherAllowances}</p>
          <p>Total Earnings: ₹{salary.totalEarnings}</p>
        </div>

        <div className="salary-card deduction-card">
          <h3>🔴 Deductions Breakdown</h3>
          <p>PF Contribution: ₹{salary.pfContribution}</p>
          <p>Tax Deductions: ₹{salary.taxDeduction}</p>
          <p>Unpaid Leave Deductions: ₹{salary.unpaidLeaveDeduction?.toFixed(2)}</p>
          <p>Total Deductions: ₹{salary.totalDeductions}</p>
        </div>
      </div>

      <div className="salary-breakdown-row center-row">
        <div className="salary-card net-salary-card">
          <h3>💰 Net Salary</h3>
          <p>Total Earnings: ₹{salary.totalEarnings}</p>
          <p>Total Deductions: ₹{salary.totalDeductions}</p>
          <p><strong>Net Salary: ₹{salary.netSalary}</strong></p>
        </div>
      </div>
            <div className="go-back-wrapper">
            <Link className="btn btn-primary" to="/employee/view-salary">Go Back</Link>
            </div>
      
    </div>
  );
}

export default SalaryDetails;