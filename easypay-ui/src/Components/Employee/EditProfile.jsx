import React, { useEffect, useState } from 'react';
import './EditProfile.css';
import { useDispatch, useSelector } from 'react-redux';
import { fetchEmployee } from '../../store/actions/EmployeeAction';
import { Link } from 'react-router-dom';
import axios from 'axios';

function EditProfile() {

    let [name, setName] = useState("");
    let [email, setEmail] = useState("");
    let [phone, setPhone] = useState("");
    let [gender, setGender] = useState("");
    let [address, setAddress] = useState("");
    let [msg, setMsg] = useState("");
    let [bankName, setBankName] = useState("");
    let [branch, setBranch] = useState("");
    let [accountNumber, setAccountNumber] = useState("");
    let [accounttype, setAccounttype] = useState("");
    let [ifscCode, setIfsccode] = useState("");
    let [profileImage, setProfleImage] = useState("");

    const dispatch = useDispatch();
    const employee = useSelector(state => state.employee.employee);

    useEffect(() => {
        dispatch(fetchEmployee());
    }, [dispatch]);

    useEffect(() => {
        if (employee) {
            setName(employee.name || "");
            setEmail(employee.email || "");
            setPhone(employee.phone || "");
            setGender(employee.gender || "");
            setAddress(employee.address || "");
            setBankName(employee.bankDetails?.bankName || "");
            setBranch(employee.bankDetails?.branchName || "");
            setAccountNumber(employee.bankDetails?.accountNumber || "");
            setAccounttype(employee.bankDetails?.accountType || "");
            setIfsccode(employee.bankDetails?.ifscCode || "");
        }
    }, [employee]);

    const empId = employee.id;

    const upload = async () => {
        const formData = new FormData();
        formData.append("file", profileImage);

        try {
            await axios.put('http://localhost:8081/api/employee/update/profile-pic',
                formData,
                {
                    headers:
                    {
                        'Authorization': 'Bearer ' + localStorage.getItem('token')
                    }

                }
            )
            dispatch(fetchEmployee());
            setMsg("Profile image uploaded successfully.")
        }
        catch (err) {
            setMsg(`❌ Upload failed: ${err.message}`);
        }
    }

    const updateEmployee = async () => {

        try {
            await axios.put(`http://localhost:8081/api/employee/update/` + empId,
                {
                    'name': name,
                    'email': email,
                    'address': address,
                    'gender': gender,
                    "phone": phone,
                    bankDetails: {
                        'bankName': bankName,
                        'branchName': branch,
                        'accountNumber': accountNumber,
                        'accountType': accounttype,
                        'ifscCode': ifscCode
                    }
                },
                {
                    headers:
                    {
                        'Authorization': 'Bearer ' + localStorage.getItem('token'),
                        'Content-Type': 'application/json'
                    }
                }
            );
            setMsg("Details Updated successfully!!!!");

        } catch (error) {
            setMsg("Operation Failed, Try again")
        }
    }

    const discardChanges = () => {
        if (employee) {
            setName(employee.name || "");
            setEmail(employee.email || "");
            setPhone(employee.phone || "");
            setGender(employee.gender || "");
            setAddress(employee.address || "");
            setBankName(employee.bankDetails?.bankName || "");
            setBranch(employee.bankDetails?.branchName || "");
            setAccountNumber(employee.bankDetails?.accountNumber || "");
            setAccounttype(employee.bankDetails?.accountType || "");
            setIfsccode(employee.bankDetails?.ifscCode || "");
        }
    }

    return (
        <div style={{
            fontFamily: 'Inter, sans-serif', backgroundColor: '#f3f4f6', display: 'flex', justifyContent: 'center',
            alignItems: 'flex-start', minHeight: '100vh', padding: '10px', boxSizing: 'border-box', width: '100%',
        }}>
            <div style={{ maxWidth: '80rem', width: '100%', margin: '0 auto', padding: '0.5rem', }}>
                <div style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}>
                    <div className="editprofile-card-container">
                        {/* Profile Picture Card */}
                        <div className="w-full lg:w-1/3 profile-card mb-3" style={{ position: 'relative' }}>
                            <Link to="/employee/profile">
                                <button className="back-btn-top-right">Back</button>
                            </Link>
                            <h2
                                style={{ fontSize: '1.25rem', fontWeight: '600', color: '#2d3748', marginBottom: '0.75rem', width: '100%', textAlign: 'left', }}>
                                Profile Picture
                            </h2>
                            <div
                                style={{
                                    display: 'flex',
                                    flexDirection: 'column',
                                    alignItems: 'center',
                                    marginBottom: '0.5rem',
                                }}
                            >
                                <div className="profile-text-circle">
                                    <img
                                        src={`/images/${employee.profilePic}`} // Replace with your actual image URL
                                        alt="Profile"
                                        className="profile-image"
                                    />
                                </div>
                                <div>
                                    <div className="file-input-row">
                                        <input type="file" onChange={($e) => { setProfleImage($e.target.files[0]) }} />

                                    </div>
                                    <button className='update-hrprofile-edit-button mt-2' onClick={() => upload()}>Change Profile</button>
                                </div>
                                <div style={{ 'color': 'black', marginTop: "8px" }}>{msg}</div>
                            </div>

                        </div>

                        {/* Personal Details Card */}
                        <div className="w-full lg:w-2/3 account-card">
                            <div
                                style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '0.5rem', }}>
                                <h2
                                    style={{ fontSize: '1.25rem', fontWeight: '600', color: '#2d3748', }}>
                                    Edit Personal Information
                                </h2>
                            </div>

                            <div>
                                <div className="mb-2">
                                    <label className="form-label">
                                        Employee Id
                                    </label>
                                    <div className="display-detail">{employee.id}</div>
                                </div>
                                <div className="grid-cols-1 md:grid-cols-2">
                                    <div>
                                        <label className="form-label">Employee Name :</label>
                                        <input type="text" className="display-detail" value={name} onChange={$e => setName($e.target.value)} />
                                    </div>
                                    <div>
                                        <label className="form-label">Gender</label>
                                        <select id="gender" name="gender" value={gender} onChange={$e => setGender($e.target.value)} className="gender-select">
                                            <option value="">Select Gender</option>
                                            <option value="Male">Male</option>
                                            <option value="Female">Female</option>
                                            <option value="Other">Other</option>
                                        </select>
                                    </div>
                                </div>

                                <div className="m2-5">
                                    <label className="form-label">Address</label>
                                    <input type="text" className="display-detail" value={address} onChange={$e => setAddress($e.target.value)} />
                                </div>

                                <div
                                    className="grid-cols-1 md:grid-cols-2"
                                    style={{ marginBottom: '1rem' }}
                                >
                                    <div>
                                        <label className="form-label">Email</label>
                                        <input className="display-detail" value={email} onChange={$e => setEmail($e.target.value)} />
                                    </div>
                                    <div>
                                        <label className="form-label">Phone number</label>
                                        <input className="display-detail" type="text" value={phone} onChange={$e => setPhone($e.target.value)} />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="w-full lg:w-2/3 account-card">
                            <div
                                style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '0.5rem', }}>
                                <h2
                                    style={{ fontSize: '1.25rem', fontWeight: '600', color: '#2d3748', }}>
                                    Edit Bank Account Details
                                </h2>
                            </div>

                            <div>
                                <div className="grid-cols-1 md:grid-cols-2">
                                    <div>
                                        <label className="form-label">Bank Name</label>
                                        <input type="text" className="display-detail" value={bankName} onChange={$e => setBankName($e.target.value)} />
                                    </div>
                                    <div>
                                        <label className="form-label">Branch</label>
                                        <input className="display-detail" value={branch} onChange={$e => setBranch($e.target.value)} />
                                    </div>
                                </div>
                                <div className="grid-cols-1 md:grid-cols-2">
                                    <div>
                                        <label className="form-label">Account Number</label>
                                        <input className="display-detail" value={accountNumber} onChange={$e => setAccountNumber($e.target.value)} />
                                    </div>
                                    <div>
                                        <label className="form-label">Account Type</label>
                                        <input className="display-detail" value={accounttype} onChange={$e => setAccounttype($e.target.value)} />
                                    </div>
                                </div>
                                <div className="m2-5">
                                    <label className="form-label">IFSC Code</label>
                                    <input className="display-detail" value={ifscCode} onChange={$e => setIfsccode($e.target.value)} />
                                </div>
                            </div>
                        </div>
                        <div className="mt-2">
                            {
                                msg != "" ? <div className="mb-4">
                                    <div className="alert alert-primary">
                                        {msg}
                                    </div>
                                </div> : ""
                            }
                            <button className='btn editprofile-btn editprofile-btn-primary' onClick={() => updateEmployee()}>Save Changes</button>
                            <button className='btn editprofile-btn editprofile-btn-primary' onClick={() => discardChanges()}>Discard Changes</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default EditProfile;
