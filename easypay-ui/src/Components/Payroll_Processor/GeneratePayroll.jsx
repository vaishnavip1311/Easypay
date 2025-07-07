import React, { useState } from 'react';
import axios from 'axios';
import './GeneratePayroll.css';
import { useNavigate } from 'react-router-dom';

function GeneratePayroll() {
  const [employeeId, setEmployeeId] = useState('');
  const [payPeriod, setPayPeriod] = useState('');
  const [msg, setMsg] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const formattedPayPeriod = payPeriod + "-01"

    try {
      const payload = { payPeriod: formattedPayPeriod };

      const res = await axios.post(
        `http://localhost:8081/api/payroll/add/${employeeId}`,
        payload,
        {
          headers: {
            Authorization: 'Bearer ' + localStorage.getItem('token'),
            'Content-Type': 'application/json',
          },
        }
      );
      setMsg('✅ Payroll generated successfully!');
    } catch (err) {
      console.error(err);
      setMsg('❌ Failed to generate payroll.');
    }
  };

  return (
    <div className="generate-payroll-container">
      <h2>Generate Payroll</h2>

      <form onSubmit={handleSubmit} className="generate-payroll-form">
        <label>Employee ID</label>
        <input
          type="number"
          required
          value={employeeId}
          onChange={(e) => setEmployeeId(e.target.value)}
          placeholder="Enter Employee ID"
        />

        <label>Pay Period (YYYY-MM)</label>
        <input
          type="month"
          required
          value={payPeriod}
          onChange={(e) => setPayPeriod(e.target.value)}
        />

        {msg && <div className="generate-msg">{msg}</div>}

        <div className="btn-group">
          <button type="submit" className="generate-btn">Generate</button>
          <button type="button" className="cancel-btn" onClick={() => navigate('/payroll-processor/view-payrolls')}>Cancel</button>
        </div>
      </form>
    </div>
  );
}

export default GeneratePayroll;
