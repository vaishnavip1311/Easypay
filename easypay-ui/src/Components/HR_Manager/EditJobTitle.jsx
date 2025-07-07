import React, { useEffect, useState } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import axios from "axios";
import "./EditJobTitle.css"; 

function EditJobTitle() {
  const { id } = useParams(); 
  const navigate = useNavigate();

  const [form, setForm] = useState({
    titleName: "",
    basicSalary: "",
    hraRate: "",
    daRate: "",
    otherAllowances: "",
  });

  const [msg, setMsg] = useState("");

  // Fetch jobTitle by ID
  useEffect(() => {
    const fetchJobTitle = async () => {
      try {
        const response = await axios.get(`http://localhost:8081/api/jobtitle/get-one/${id}`, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        });
        setForm(response.data);
      } catch (error) {
        setMsg("Failed to fetch job title.");
        console.error(error);
      }
    };

    fetchJobTitle();
  }, [id]);

  // Handle input changes
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // Submit updated job title
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8081/api/jobtitle/update/${id}`, form, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      });
      setMsg("✅ Job title updated successfully!");
    } catch (error) {
      setMsg("❌ Failed to update job title.");
      console.error(error);
    }
  };

  return (
    <div className="jobtitle-form-container">
      <div className="jobtitle-form-box">
        <h2>Edit Job Title</h2>

        {msg && <div className="alert-message">{msg}</div>}

        <form className="jobtitle-edit-form" onSubmit={handleSubmit}>
          <label>Title Name</label>
          <input
            type="text"
            name="titleName"
            value={form.titleName}
            onChange={handleChange}
            required
          />

          <label>Basic Salary</label>
          <input
            type="number"
            name="basicSalary"
            value={form.basicSalary}
            onChange={handleChange}
            required
          />

          <label>HRA Rate</label>
          <input
            type="number"
            name="hraRate"
            value={form.hraRate}
            onChange={handleChange}
            required
          />

          <label>DA Rate</label>
          <input
            type="number"
            name="daRate"
            value={form.daRate}
            onChange={handleChange}
            required
          />

          <label>Other Allowances</label>
          <input
            type="number"
            name="otherAllowances"
            value={form.otherAllowances}
            onChange={handleChange}
            required
          />

          <div className="form-actions">
            <button type="submit" className="jobtitle-save-btn">Save Changes</button>
            <Link to="/hr-manager/manage-jobtitles">
              <button type="button" className="jobtitle-cancel-btn">Cancel</button>
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
}

export default EditJobTitle;
