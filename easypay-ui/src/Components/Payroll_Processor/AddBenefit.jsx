import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./AddBenefit.css"

function AddBenefit() {

const navigate = useNavigate();
  let [msg, setMsg] = useState("");
  let [name, setName] = useState("");
  let [type, setType] = useState("");
  let [amount, setAmount] = useState("");
  let [description, setDescription] = useState("");

  const AddBenefit = async () => {
    try {
      await axios.post(`http://localhost:8081/api/benefit/add`,
        {
          'benefitName': name,
          'benefitType': type,
          'allowanceAmount': amount,
          'description': description
        },
        {
          headers:
          {
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
            'Content-Type': 'application/json'
          }
        }
      );
      setMsg("Benefit added successfully!!!!")

    } catch (error) {
      setMsg("Operation Failed, Try again")
    }
  }

  return (
    <div className="benefit-container">
      <form className="benefit-form">
        <div className="benefit-row">
          <h4>Benefit Information</h4>
          <div className="benefit-input-group jobtitle-input-group-icon">
            <input type="text" placeholder="Benefit Name" onChange={$e => setName($e.target.value)} />
          </div>
          <div className="benefit-input-group jobtitle-input-group-icon">
            <input type="text" placeholder="Benefit Type" onChange={$e => setType($e.target.value)} />
          </div>
          <div className="benefit-input-group jobtitle-input-group-icon">
            <input type="text" placeholder="Allowance Amount" onChange={$e => setAmount($e.target.value)} />
          </div>
          <div className="benefit-input-group jobtitle-input-group-icon">
            <input type="text" placeholder="Description" onChange={$e => setDescription($e.target.value)} />
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
      <div className="benefit-button">
        <button onClick={() => AddBenefit()}>Add Benefit</button>
        <button className="benefit-back-button" onClick={() => navigate('/payroll-processor/manage-benefits')}>Back</button>
      </div>
    </div>
  );
}

export default AddBenefit;
