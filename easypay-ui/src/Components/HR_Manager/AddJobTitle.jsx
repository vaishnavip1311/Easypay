import React, { useState } from "react";
import "./AddJobTitle.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function AddJobTitle() {
  const navigate = useNavigate();
  let [msg, setMsg] = useState("");
  let [title, setTitle] = useState("");
  let [basicSalary, setBasicSalary] = useState("");
  let [hra, setHra] = useState("");
  let [da, setDa] = useState("");
  let [otherAllowances, setOtherAllowances] = useState("");

  const AddJobTitle = async () => {
    try {
      await axios.post(`http://localhost:8081/api/jobtitle/add`,
        {
          'titleName': title,
          'basicSalary': basicSalary,
          'hraRate': hra,
          'daRate': da,
          'otherAllowances': otherAllowances,
        },
        {
          headers:
          {
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
            'Content-Type': 'application/json'
          }
        }
      );
      setMsg("Job title added successfully!!!!")

    } catch (error) {
      setMsg("Operation Failed, Try again")
    }
  }

  return (
    <div className="jobtitle-container">
      <form className="jobtitle-form">
        <div className="jobtitle-row">
          <h4>Job Title Information</h4>
          <div className="jobtitle-input-group jobtitle-input-group-icon">
            <input type="text" placeholder="Job Title" onChange={$e => setTitle($e.target.value)} />
            <div className="jobtitle-input-icon">
              <i className="fa fa-user"></i>
            </div>
          </div>
          <div className="jobtitle-input-group jobtitle-input-group-icon">
            <input type="text" placeholder="Basic Salary" onChange={$e => setBasicSalary($e.target.value)} />
            <div className="jobtitle-input-icon">
              <i className="fa fa-money"></i>
            </div>
          </div>
          <div className="jobtitle-input-group jobtitle-input-group-icon">
            <input type="text" placeholder="HRA Rate" onChange={$e => setHra($e.target.value)} />
            <div className="jobtitle-input-icon">
              <i className="fa fa-percent"></i>
            </div>
          </div>
          <div className="jobtitle-input-group jobtitle-input-group-icon">
            <input type="text" placeholder="DA Rate" onChange={$e => setDa($e.target.value)} />
            <div className="jobtitle-input-icon">
              <i className="fa fa-percent"></i>
            </div>
          </div>
          <div className="jobtitle-input-group jobtitle-input-group-icon">
            <input type="text" placeholder="Other Allowances" onChange={$e => setOtherAllowances($e.target.value)} />
            <div className="jobtitle-input-icon">
              <i className="fa fa-plus-circle"></i>
            </div>
          </div>
        </div>
      </form>
      {
        msg != "" ? <div className="mb-4">
          <div className="alert alert-primary">
            {msg}
          </div>
        </div> : ""
      }
      <div className="jobtitle-button">
        <button onClick={() => AddJobTitle()}>Add Jobtitle</button>
        <button className="jobtitle-back-button" onClick={() => navigate('/hr-manager/manage-jobtitles')}>Back</button>
      </div>
    </div>
  );
}

export default AddJobTitle;
