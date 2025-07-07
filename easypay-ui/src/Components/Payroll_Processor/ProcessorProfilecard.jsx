import React, { useEffect, useRef, useState } from 'react';
import './ProcessorProfileCard.css'; // Create a separate CSS if needed
import { useDispatch, useSelector } from 'react-redux';
import { fetchProcessor } from '../../store/actions/ProcessorAction'; // create this action
import axios from 'axios';

function ProcessorProfileCard() {
  const dispatch = useDispatch();
  const processor = useSelector(state => state.processor.processor); // from Redux store
  const [msg, setMsg] = useState('');
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({});
  const fileInputRef = useRef(null);

  useEffect(() => {
    dispatch(fetchProcessor());
  }, [dispatch]);

  const processorId = processor.id;

  useEffect(() => {
    if (processor) setFormData(processor);
  }, [processor]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleToggleEdit = () => {
    setIsEditing(!isEditing);
  };

  const handleSave = async () => {
    try {
      await axios.put(`http://localhost:8081/api/payroll-processor/update/` + processorId, formData, {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('token'),
          'Content-Type': 'application/json'
        }
      });
      setMsg('✅ Profile Updated successfully!');
      dispatch(fetchProcessor());
    } catch (err) {
      console.error(err);
      setMsg('❌ Operation failed. Try again.');
    }
    setIsEditing(false);
  };

  const handleProfilePicClick = () => {
    fileInputRef.current.click();
  };

  const handleProfilePicChange = async (e) => {
    const file = e.target.files[0];
    if (!file) return;

    const picFormData = new FormData();
    picFormData.append("file", file);

    try {
      await axios.post(`http://localhost:8081/api/payroll-processor/upload/profile-pic`, picFormData, {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('token'),
          'Content-Type': 'multipart/form-data'
        }
      });
      setMsg('✅ Profile picture updated!');
      dispatch(fetchProcessor());
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
            <input name="name" value={formData.name || ''} onChange={handleChange} className="hr-editable-input" />
          ) : (
            <div className="hr-readonly-input">{processor?.name}</div>
          )}

          <label>Email</label>
          {isEditing ? (
            <input name="email" value={formData.email || ''} onChange={handleChange} className="hr-editable-input" />
          ) : (
            <div className="hr-readonly-input">{processor?.email}</div>
          )}

          <label>Contact Number</label>
          {isEditing ? (
            <input name="phoneNumber" value={formData.phoneNumber || ''} onChange={handleChange} className="hr-editable-input" />
          ) : (
            <div className="hr-readonly-input">{processor?.phoneNumber}</div>
          )}

          <label>Address</label>
          {isEditing ? (
            <input name="address" value={formData.address || ''} onChange={handleChange} className="hr-editable-input" />
          ) : (
            <div className="hr-readonly-input">{processor?.address}</div>
          )}

          <label>Birth Date</label>
          {isEditing ? (
            <input type="date" name="birthDate" value={formData.birthDate || ''} onChange={handleChange} className="hr-editable-input" />
          ) : (
            <div className="hr-readonly-input">{processor?.birthDate}</div>
          )}

          <button type="button" className="hr-edit-btn" onClick={isEditing ? handleSave : handleToggleEdit}>
            {isEditing ? 'Save' : 'Edit'}
          </button>

          {msg && (
            <div className="mb-4">
              <div className="alert alert-primary">{msg}</div>
            </div>
          )}
        </div>
      </div>

      <div className="hr-profile-right">
        <div className="hr-profile-content">
          <img src={`../images/${processor?.profilePic}`} alt="Processor Profile" className="hr-profile-img" />
          <button className="change-profile-btn" onClick={handleProfilePicClick}>Change Profile</button>
          <input type="file" accept="image/*" ref={fileInputRef} style={{ display: 'none' }} onChange={handleProfilePicChange} />
        </div>
      </div>
    </div>
  );
}

export default ProcessorProfileCard;
