import React, { useEffect, useRef, useState } from 'react';
import './HrProfileCard.css';
import { useDispatch, useSelector } from 'react-redux';
import { fetchHR } from '../../store/actions/HRAction';
import axios from 'axios';

function HrProfileCard() {
    const dispatch = useDispatch();
    let [msg, setMsg] = useState("");
    const hr = useSelector(state => state.hr.hr);
    const [isEditing, setIsEditing] = useState(false);
    const [formData, setFormData] = useState({});
    const fileInputRef = useRef(null); // reference for file input

    useEffect(() => {
        dispatch(fetchHR());
    }, [dispatch]);

    const hrId = hr.id;

    useEffect(() => {
        if (hr) setFormData(hr);
    }, [hr]);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleToggleEdit = () => {
        setIsEditing(!isEditing);
    };

    const handleSave = async () => {
        try {
            await axios.put(`http://localhost:8081/api/hrmanager/update/` + hrId, formData, {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token'),
                    'Content-Type': 'application/json'
                }
            });
            setMsg('✅ Profile Updated successfully!');
            dispatch(fetchHR());
        } catch (err) {
            console.error(err);
            setMsg('❌ Operation failed. Try again.');
        }
        setIsEditing(false);
    };

    const handleProfilePicClick = () => {
    fileInputRef.current.click(); // trigger the hidden file input
  };

  const handleProfilePicChange = async (e) => {
    const file = e.target.files[0];
    if (!file) return;
    // Optional: send to backend as multipart/form-data
    const formData = new FormData();
    formData.append("file", file);

    try {
      await axios.post(`http://localhost:8081/api/hrmanager/upload/profile-pic`, formData, {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('token'),
          'Content-Type': 'multipart/form-data'
        }
      });
      setMsg('✅ Profile picture updated!');
      dispatch(fetchHR()); // refresh image
    } catch (err) {
      console.error(err);
      setMsg('❌ Failed to upload image.');
    }
  };

    return (
        <div className="hr-profile-card">
            <div className="hr-profile-left">
                <h2>Profile</h2>

                <div className="hr-profile-form">
                    <label>Name</label>
                    {isEditing ? (
                        <input
                            className="hr-editable-input"
                            name="name"
                            value={formData.name || ''}
                            onChange={handleChange}
                        />
                    ) : (
                        <div className="hr-readonly-input">{hr.name}</div>
                    )}

                    <label>Gender</label>
                    {isEditing ? (
                        <input
                            className="hr-editable-input"
                            name="gender"
                            value={formData.gender || ''}
                            onChange={handleChange}
                        />
                    ) : (
                        <div className="hr-readonly-input">{hr.gender}</div>
                    )}

                    <label>Email</label>
                    {isEditing ? (
                        <input
                            className="hr-editable-input"
                            name="email"
                            value={formData.email || ''}
                            onChange={handleChange}
                        />
                    ) : (
                        <div className="hr-readonly-input">{hr.email}</div>
                    )}

                    <label>Contact Number</label>
                    {isEditing ? (
                        <input
                            className="hr-editable-input"
                            name="phoneNumber"
                            value={formData.phoneNumber || ''}
                            onChange={handleChange}
                        />
                    ) : (
                        <div className="hr-readonly-input">{hr.phoneNumber}</div>
                    )}

                    <label>Address</label>
                    {isEditing ? (
                        <input
                            className="hr-editable-input"
                            name="address"
                            value={formData.address || ''}
                            onChange={handleChange}
                        />
                    ) : (
                        <div className="hr-readonly-input">{hr.address}</div>
                    )}

                    <label>Birth Date</label>
                    {isEditing ? (
                        <input
                            type="date"
                            className="hr-editable-input"
                            name="birthDate"
                            value={formData.birthDate || ''}
                            onChange={handleChange}
                        />
                    ) : (
                        <div className="hr-readonly-input">{hr.birthDate}</div>
                    )}

                    <button
                        type="button"
                        className="hr-edit-btn"
                        onClick={isEditing ? handleSave : handleToggleEdit}
                    >
                        {isEditing ? 'Save' : 'Edit'}
                    </button>

                    {
                        msg != "" ? <div className="mb-4">
                            <div className="alert alert-primary">
                                {msg}
                            </div>
                        </div> : ""
                    }

                </div>
            </div>

            <div className="hr-profile-right">
        <div className="hr-profile-content">
          <img
            src={`../images/${hr?.profilePic}`}
            alt="HR Profile"
            className="hr-profile-img"
          />
          <button className="change-profile-btn" onClick={handleProfilePicClick}>
            Change Profile
          </button>
          <input
            type="file"
            accept="image/*"
            ref={fileInputRef}
            style={{ display: 'none' }}
            onChange={handleProfilePicChange}
          />
        </div>
        
      </div>


        </div>
    );
}

export default HrProfileCard;
