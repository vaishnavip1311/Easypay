import React, { useEffect, useState } from "react";
import "./ManagePayrollPolicy.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function ManagePayrollPolicy() {
  const navigate = useNavigate();
  const [expandedRow, setExpandedRow] = useState(null);
  const [payrollPolicies, setPayrollPolicies] = useState([]);

  const toggleRow = (rowId) => {
    setExpandedRow((prev) => (prev === rowId ? null : rowId));
  };

  useEffect(()=>{
    const getAllPayrollPolicies = async () => {
            try {
                let response = await axios.get(`http://localhost:8081/api/payrollpolicy/get-all`,
                    { headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') } }
                )
                setPayrollPolicies(response.data);
            }
            catch (err) {
                console.log(err)
            }
        }
        getAllPayrollPolicies();
  },[])

  return (
    <div className="payroll-table-wrapper">
      <h2 >Manage Payroll Policies</h2>
      <table className="payroll-table">
        <thead>
          <tr>
            <th>&nbsp;</th>
            <th>Id</th>
            <th>Name</th>
            <th>Type</th>
            <th>Tax Rate</th>
            <th>PF Rate</th>
            <th>Effective From</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>

        {payrollPolicies.map((policy) => (
          <tbody key={policy.id}>
            <tr>
              <td>
                <button
                  className="payroll-collapse-toggle"
                  onClick={() => toggleRow(policy.id)}
                >
                  {expandedRow === policy.id ? "˅" : ">"}
                </button>
              </td>
              <td>{policy.id}</td>
              <td>{policy.name}</td>
              <td>{policy.type}</td>
              <td>{policy.taxRate}</td>
              <td>{policy.pfRate}</td>
              <td>{policy.effectiveFrom}</td>
              <td>{policy.status}</td>
              <td><button className="policy-edit-btn">Edit</button>
              <button className="policy-remove-btn">❌</button></td>
              
            </tr>
            {expandedRow === policy.id && (
              <tr>
                <td colSpan="10" className="payroll-collapse-content">
                  Description :&nbsp;{policy.description}
                </td>
              </tr>
            )}
          </tbody>
        ))}
      </table>
      <button className="policy-add-btn" onClick={() => navigate('/hr-manager/add-payroll-policy')}>+ Add New Policy</button>
    </div>
  );
}

export default ManagePayrollPolicy;
