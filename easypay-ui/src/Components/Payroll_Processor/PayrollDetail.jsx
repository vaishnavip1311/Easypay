import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import "./PayrollDetail.css"; // Optional styling file

function PayrollDetail() {
  const { id } = useParams(); // get payrollId from URL
  const navigate = useNavigate();
  const [payroll, setPayroll] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchPayroll = async () => {
      try {
        const response = await axios.get(`http://localhost:8081/api/payroll/get-one/${id}`, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        });
        setPayroll(response.data);
      } catch (err) {
        console.error(err);
        setError("Failed to fetch payroll details.");
      }
    };

    fetchPayroll();
  }, [id]);

  if (error) {
    return <div className="error-msg">{error}</div>;
  }

  if (!payroll) {
    return <div className="loading-msg">Loading payroll details...</div>;
  }

  return (
    <div className="payroll-detail-container">
      <div className="payroll-title"><h2>Payroll Details</h2></div>
      <div className="payroll-detail-card">
        <p><strong>Payroll ID:</strong> {payroll.id}</p>
        <p><strong>Employee Name:</strong> {payroll.employee?.name}</p>
        <p><strong>Email:</strong> {payroll.employee?.email}</p>
        <p><strong>Pay Period:</strong> {payroll.payPeriod}</p>
        <p><strong>Basic Salary:</strong> ₹{payroll.basicSalary}</p>
        <p><strong>DA:</strong> ₹{payroll.da}</p>
        <p><strong>HRA:</strong> ₹{payroll.hra}</p>
        <p><strong>Other Allowances:</strong> ₹{payroll.otherAllowances}</p>
        <p><strong>Tax Deduction:</strong> ₹{payroll.taxDeduction}</p>
        <p><strong>PF Contribution:</strong> ₹{payroll.pfContribution}</p>
        <p><strong>Unpaid Leave Deduction:</strong> ₹{payroll.unpaidLeaveDeduction}</p>
        <p><strong>Total Earnings:</strong> ₹{payroll.totalEarnings}</p>
        <p><strong>Total Deductions:</strong> ₹{payroll.totalDeductions}</p>
        <p><strong>Net Salary:</strong> ₹{payroll.netSalary}</p>
        <p><strong>Status:</strong> {payroll.status}</p>
        <p><strong>Processed On:</strong> {payroll.processedOn}</p>
      </div>

      <button className="back-btn" onClick={() => navigate(-1)}>← Back</button>
    </div>
  );
}

export default PayrollDetail;
