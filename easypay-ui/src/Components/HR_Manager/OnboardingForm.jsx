import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './OnboardingForm.css';
import { useNavigate } from 'react-router-dom';

function OnboardingForm() {
  const [step, setStep] = useState(1);
  const [msg, setMsg] = useState('');
  const [jobTitles, setJobTitles] = useState([]);
  let [profileImage, setProfleImage] = useState("");
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    employeeId: '',
    name: '',
    gender: '',
    email: '',
    phone: '',
    address: '',
    password: '',
    joiningDate: '',
    jobTitleId: '',
    employementType: '',
    status: '',
    bankName: '',
    branchName: '',
    accountNumber: '',
    ifscCode: '',
    accountType: ''
  });

  useEffect(() => {
    const getAllJobtitles = async () => {
      try {
        const response = await axios.get(`http://localhost:8081/api/jobtitle/get-all`, {
          headers: {
            Authorization: 'Bearer ' + localStorage.getItem('token')
          }
        });
        setJobTitles(response.data);
      } catch (err) {
        console.log(err);
      }
    };
    getAllJobtitles();
  }, []);

  const handleChange = (e) => {
    const { name, value, type, checked, files } = e.target;

    if (type === 'checkbox' && name === 'consent') {
      setFormData({ ...formData, [name]: checked });
    } else if (type === 'checkbox' && name === 'status') {
      const current = formData.status ? formData.status.split(',') : [];
      const updated = checked
        ? [...current, value]
        : current.filter((s) => s !== value);
      setFormData({ ...formData, status: updated.join(',') });
    } else if (type === 'file') {
      setFormData({ ...formData, [name]: files[0] });
    } else {
      setFormData({ ...formData, [name]: value });
    }
  };

  const nextStep = () => setStep((prev) => prev + 1);
  const prevStep = () => setStep((prev) => prev - 1);

  const uploadProfilePicture = async () => {
    const imageData = new FormData();
    imageData.append("file", profileImage);

    try {
      const response = await axios.post(
        `http://localhost:8081/api/employee/upload/profile-pic/${formData.employeeId}`,
        imageData,
        {
          headers: {
            Authorization: 'Bearer ' + localStorage.getItem('token'),
            'Content-Type': 'multipart/form-data'
          }
        }
      );
      setMsg("✅ Profile image uploaded successfully.");
    } catch (err) {
      setMsg(`❌ Upload failed: ${err.message}`);
      throw err;
    }
  };

  const addEmployee = async () => {
    const payload = {
      id: formData.employeeId,
      name: formData.name,
      gender: formData.gender,
      phone: formData.phone,
      email: formData.email,
      address: formData.address,
      joiningDate: formData.joiningDate,
      employementType: formData.employementType,
      status: formData.status,
      user: {
        username: formData.email,
        password: formData.password
      },
      bankDetails: {
        bankName: formData.bankName,
        branchName: formData.branchName,
        accountNumber: formData.accountNumber,
        ifscCode: formData.ifscCode,
        accountType: formData.accountType
      }
    };



    try {
      await axios.post(`http://localhost:8081/api/employee/add/${formData.jobTitleId}`, payload, {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('token'),
          'Content-Type': 'application/json'
        }
      });
      setMsg('✅ Employee added successfully!');
    } catch (err) {
      console.error(err);
      setMsg('❌ Operation failed. Try again.');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await addEmployee();
      await uploadProfilePicture();
      alert("✅ Submitted successfully!");
    } catch (err) {
      alert("❌ Submission failed. Please check and try again.");
    }
  };

  return (
    <div className="onboard-wrapper">
      <div className="onboard-container">
        <div className="onboard-header">
          <button className="onboard-back-button" onClick={() => navigate('/hr-manager/manage-employees')}>
            ⬅ Back
          </button>
        </div>
        <h2 className="onboard-title">Employee Onboarding Form</h2>

        <form className="onboard-form" onSubmit={handleSubmit}>
          {step === 1 && (
            <>
              <h5>Step 1: Personal Details</h5>
              <div className="onboard-row">
                <div className="onboard-col">
                  <label className="onboard-label">Enter Id</label>
                  <input type="text" className="onboard-input" name="employeeId" value={formData.employeeId} onChange={handleChange} required />
                </div>
                <div className="onboard-col">
                  <label className="onboard-label">Enter Name</label>
                  <input type="text" className="onboard-input" name="name" value={formData.name} onChange={handleChange} required />
                </div>
                <div className="onboard-col">
                  <label className="onboard-label">Gender</label>
                  <select className="onboard-select" name="gender" value={formData.gender} onChange={handleChange} required>
                    <option value="">Choose...</option>
                    <option>Male</option>
                    <option>Female</option>
                    <option>Other</option>
                  </select>
                </div>
                <div className="onboard-col">
                  <label className="onboard-label">Phone</label>
                  <input type="tel" className="onboard-input" name="phone" value={formData.phone} onChange={handleChange} required />
                </div>
                <div className="onboard-col">
                  <label className="onboard-label">Email</label>
                  <input type="email" className="onboard-input" name="email" value={formData.email} onChange={handleChange} required />
                </div>
                <div className="onboard-col">
                  <label className="onboard-label">Password</label>
                  <input type="password" className="onboard-input" name="password" value={formData.password} onChange={handleChange} required />
                </div>
                <div className="onboard-col" style={{ flex: '1 1 100%' }}>
                  <label className="onboard-label">Address</label>
                  <textarea className="onboard-textarea" name="address" value={formData.address} onChange={handleChange} required />
                </div>
              </div>
            </>
          )}

          {step === 2 && (
            <>
              <h5>Step 2: Professional Details</h5>
              <div className="onboard-row">
                <div className="onboard-col">
                  <label className="onboard-label">Job Title</label>
                  <select className="onboard-select" name="jobTitleId" value={formData.jobTitleId} onChange={handleChange} required>
                    <option value="">Choose Job Title</option>
                    {jobTitles.map((job) => (
                      <option key={job.id} value={job.id}>{job.titleName}</option>
                    ))}
                  </select>
                </div>
                <div className="onboard-col">
                  <label className="onboard-label">Joining Date</label>
                  <input type="date" className="onboard-input" name="joiningDate" value={formData.joiningDate} onChange={handleChange} required />
                </div>
                <div className="onboard-col" style={{ flex: '1 1 100%' }}>
                  <label className="onboard-label">Employment Type</label>
                  <div className="onboard-radio-group">
                    {["Part Time", "Full Time", "Contract", "Intern"].map((type) => (
                      <label className="onboard-radio-item" key={type}>
                        <input type="radio" name="employementType" value={type} checked={formData.employementType === type} onChange={handleChange} required />
                        {type}
                      </label>
                    ))}
                  </div>
                </div>
                <div className="onboard-col" style={{ flex: '1 1 100%' }}>
                  <label className="onboard-label">Status</label>
                  <div className="onboard-radio-group">
                    {["Active", "Inactive", "On Leave"].map((status) => (
                      <label className="onboard-radio-item" key={status}>
                        <input type="checkbox" name="status" value={status} checked={formData.status?.includes(status)} onChange={handleChange} />
                        {status}
                      </label>
                    ))}
                  </div>
                </div>
              </div>
            </>
          )}

          {step === 3 && (
            <>
              <h5>Step 3: Bank Details</h5>
              <div className="onboard-row">
                <div className="onboard-col">
                  <label className="onboard-label">Bank Name</label>
                  <input type="text" className="onboard-input" name="bankName" value={formData.bankName} onChange={handleChange} required />
                </div>
                <div className="onboard-col">
                  <label className="onboard-label">Branch Name</label>
                  <input type="text" className="onboard-input" name="branchName" value={formData.branchName} onChange={handleChange} required />
                </div>
                <div className="onboard-col">
                  <label className="onboard-label">Account Number</label>
                  <input type="text" className="onboard-input" name="accountNumber" value={formData.accountNumber} onChange={handleChange} required />
                </div>
                <div className="onboard-col">
                  <label className="onboard-label">IFSC Code</label>
                  <input type="text" className="onboard-input" name="ifscCode" value={formData.ifscCode} onChange={handleChange} required />
                </div>
                <div className="onboard-col">
                  <label className="onboard-label">Account Type</label>
                  <select className="onboard-select" name="accountType" value={formData.accountType} onChange={handleChange} required>
                    <option value="">Choose...</option>
                    <option value="Savings">Savings</option>
                    <option value="Current">Current</option>
                    <option value="Joint">Joint</option>
                  </select>
                </div>
              </div>
            </>
          )}

          {step === 4 && (
            <>
              <h5>Step 4: Upload Documents</h5>
              <div className="onboard-row">
                <div className="onboard-col">
                  <label className="onboard-label">Upload Profile Picture</label>
                  <input
                    type="file"
                    className="onboard-file"
                    name="profile"
                    onChange={($e) => { setProfleImage($e.target.files[0]) }}
                    required
                  />

                </div>
              </div>
            </>
          )}


          <div className="onboard-button-group">
            {step > 1 && (
              <button type="button" className="onboard-btn onboard-btn-secondary" onClick={prevStep}>
                Back
              </button>
            )}
            {step < 4 && (
              <button type="button" className="onboard-btn onboard-btn-primary" onClick={nextStep}>
                Next
              </button>
            )}
            {step === 4 && (
              <button type="submit" className="onboard-btn onboard-btn-success">
                Submit
              </button>
            )}
          </div>
        </form>

        {
          msg != "" ? <div className="onboard-message">
            {msg}
          </div> : ""
        }

      </div>
    </div>
  );
}

export default OnboardingForm;
