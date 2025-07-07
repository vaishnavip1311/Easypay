import React, { useState } from "react";
import "./AddPayrollPolicy.css";
import { useNavigate } from "react-router-dom";

function AddPayrollPolicy() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: "",
    type: "",
    taxRate: "",
    pfRate: "",
    effectiveFrom: "",
    status: "Active",
    description: ""
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8081/api/payrollpolicy/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(formData)
      });
      if (response.ok) {
        alert("Payroll policy added successfully!");
        setFormData({
          name: "",
          type: "",
          taxRate: "",
          pfRate: "",
          effectiveFrom: "",
          status: "Active",
          description: ""
        });
      } else {
        alert("Failed to add payroll policy.");
      }
    } catch (error) {
      console.error("Error submitting payroll policy:", error);
      alert("Server error occurred.");
    }
  };

  return (
    <div className="payroll-form-container">
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
        <h2 className="payroll-title">Add Payroll Policy</h2>
        <button className="policy-back-btn" onClick={() => navigate('/hr-manager/manage-payroll-policy')}>← Back</button>
      </div>
      <form className="payrollpolicy-form" onSubmit={handleSubmit}>
        {/* Row: Policy Name + Type */}
        <div className="payroll-row">
          <div className="payroll-form-group">
            <label>Policy Name</label>
            <input type="text" name="name" value={formData.name} onChange={handleChange} required />
          </div>
          <div className="payroll-form-group">
            <label>Type</label>
            <input type="text" name="type" value={formData.type} onChange={handleChange} required />
          </div>
        </div>

        {/* Row: Tax Rate + PF Rate */}
        <div className="payroll-row">
          <div className="payroll-form-group">
            <label>Tax Rate (%)</label>
            <input type="number" step="0.01" name="taxRate" value={formData.taxRate} onChange={handleChange} required />
          </div>
          <div className="payroll-form-group">
            <label>PF Rate (%)</label>
            <input type="number" step="0.01" name="pfRate" value={formData.pfRate} onChange={handleChange} required />
          </div>
        </div>

        {/* Row: Effective From + Status */}
        <div className="payroll-row">
          <div className="payroll-form-group">
            <label>Effective From</label>
            <input type="date" name="effectiveFrom" value={formData.effectiveFrom} onChange={handleChange} required />
          </div>
          <div className="payroll-form-group">
            <label>Status</label>
            <select name="status" value={formData.status} onChange={handleChange}>
              <option value="Active">Active</option>
              <option value="Inactive">Inactive</option>
            </select>
          </div>
        </div>

        {/* Description */}
        <div className="payroll-form-group">
          <label>Description</label>
          <textarea name="description" value={formData.description} onChange={handleChange} required />
        </div>

        {/* Submit Button */}
        <div className="payroll-form-actions">
          <button type="submit">Add Policy</button>
        </div>
      </form>
    </div>
  );
}
export default AddPayrollPolicy;
