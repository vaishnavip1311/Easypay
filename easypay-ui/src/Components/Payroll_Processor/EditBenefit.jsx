import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import './EditBenefit.css'; // Optional

function EditBenefit() {
  const { id } = useParams(); // benefitId from URL
  const navigate = useNavigate();
  const [benefit, setBenefit] = useState(null);
  const [msg, setMsg] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchBenefit = async () => {
      try {
        const res = await axios.get(`http://localhost:8081/api/benefit/get-one/${id}`, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        });
        setBenefit(res.data);
      } catch (err) {
        console.error(err);
        setError("Failed to load benefit data.");
      }
    };

    fetchBenefit();
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setBenefit({ ...benefit, [name]: value });
  };

  const handleUpdate = async () => {
    try {
      await axios.put(`http://localhost:8081/api/benefit/update/${id}`, benefit, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
          "Content-Type": "application/json"
        },
      });
      setMsg("✅ Benefit updated successfully!");
    } catch (err) {
      console.error(err);
      setMsg("❌ Failed to update benefit.");
    }
  };

  if (error) return <div className="error-msg">{error}</div>;
  if (!benefit) return <div className="loading-msg">Loading benefit...</div>;

  return (
    <div className="edit-benefit-container">
      <h2>Edit Benefit</h2>

      <div className="form-group">
        <label>Benefit Name</label>
        <input
          type="text"
          name="benefitName"
          value={benefit.benefitName || ""}
          onChange={handleChange}
        />
      </div>

      <div className="form-group">
        <label>Benefit Type</label>
        <input
          type="text"
          name="benefitType"
          value={benefit.benefitType || ""}
          onChange={handleChange}
        />
      </div>

      <div className="form-group">
        <label>Amount</label>
        <input
          type="number"
          name="amount"
          value={benefit.amount || ""}
          onChange={handleChange}
        />
      </div>

      <div className="form-group">
        <label>Status</label>
        <select name="status" value={benefit.status || ""} onChange={handleChange}>
          <option value="">Select Status</option>
          <option value="Active">Active</option>
          <option value="Inactive">Inactive</option>
        </select>
      </div>

      {msg && <div className="alert-msg">{msg}</div>}

      <div className="button-group">
  <button className="update-btn" onClick={handleUpdate}>Update</button>
  <button className="cancel-btn" onClick={() => navigate("/payroll-processor/manage-benefits")}>Cancel</button>
</div>

    </div>
  );
}

export default EditBenefit;
