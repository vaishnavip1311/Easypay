import axios from "axios";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { fetchHR } from "../../store/actions/HRAction";
import "./UpdateHrProfile.css"

function UpdateHRProfile() {

    const dispatch = useDispatch();
    const hr = useSelector(state => state.hr.hr);

    useEffect(() => {
        dispatch(fetchHR());
    }, [dispatch]);

    const hrId = hr.id;

    let [profileImage, setProfleImage] = useState("");
    let [msg, setMsg] = useState("");
    let [name, setName] = useState("");
    let [email, setEmail] = useState("");
    let [phone, setPhone] = useState("");
    let [gender, setGender] = useState("");
    let [address, setAddress] = useState("");
    let [birthDate, setBirthDate] = useState("");

    useEffect(() => {
            if (hr) {
                setName(hr.name || "");
                setEmail(hr.email || "");
                setPhone(hr.phoneNumber || "");
                setGender(hr.gender || "");
                setAddress(hr.address || "");
                setBirthDate(hr.birthDate || "");
            }
        }, [hr]);

    const upload = async () => {
        const formData = new FormData();
        formData.append("file", profileImage);

        try {
            await axios.post('http://localhost:8081/api/hrmanager/upload/profile-pic',
                formData,
                {
                    headers:
                    {
                        'Authorization': 'Bearer ' + localStorage.getItem('token')
                    }

                }
            )
            dispatch(fetchHR());
            setMsg("Profile image uploaded successfully.")
        }
        catch (err) {
            setMsg(`❌ Upload failed: ${err.message}`);
        }
    }

    const updateHr = async () => {

        try {
            await axios.put(`http://localhost:8081/api/hrmanager/update/` + hrId,
                {
                    'name': name,
                    'email': email,
                    'address': address,
                    'gender': gender,
                    'phoneNumber': phone,
                    'birthDate':birthDate
                    
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
        if (hr) {
                setName(hr.name || "");
                setEmail(hr.email || "");
                setPhone(hr.phoneNumber || "");
                setGender(hr.gender || "");
                setAddress(hr.address || "");
                setBirthDate(hr.birthDate || "");
            }
    }
    {/** return (
        <div>
            {msg}
            <label>Select your Profile Pic: </label>
            <input type="file" onChange={($e) => { setProfleImage($e.target.files[0]) }} />
            <br />
            <button onClick={() => upload()}>Upload Image</button>
        </div>
    ) */}

    return (
        <div style={{
            fontFamily: 'Inter, sans-serif', backgroundColor: '#f3f4f6', display: 'flex', justifyContent: 'center',
            alignItems: 'flex-start', minHeight: '100vh', padding: '10px', boxSizing: 'border-box', width: '100%',
        }}>
            <div style={{ maxWidth: '80rem', width: '100%', margin: '0 auto', padding: '0.5rem', }}>
                <div style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}>
                    <div className="flex flex-col lg:flex-row gap-4">
                        {/* Profile Picture Card */}
                        <div className="w-full lg:w-1/3 profile-card mb-3" style={{ position: 'relative' }}>
                            <Link to="/hr-manager">
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
                                        src={`../images/${hr.profilePic}`}
                                        alt="Profile"
                                        className="profile-image"
                                        width="120"
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
                                <div className="grid-cols-1 md:grid-cols-2">
                                    <div>
                                        <label className="form-label">Name</label>
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
                                <div className="m2-5">
                                    <label className="form-label">Birth Date</label>
                                    <input type="text" className="display-detail" value={birthDate} onChange={$e => setBirthDate($e.target.value)} />
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
                            <button className='btn editprofile-btn editprofile-btn-primary' onClick={() => updateHr()}>Save Changes</button>
                            <button className='btn editprofile-btn editprofile-btn-primary' onClick={() => discardChanges()}>Discard Changes</button>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    );
}

export default UpdateHRProfile;