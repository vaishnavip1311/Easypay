import { useSelector, useDispatch } from "react-redux";
import { fetchEmployee } from "../../store/actions/EmployeeAction";
import { useEffect, useState } from "react";
import axios from "axios";
import './updateprofile.css';

function UpdateProfile() {
    const dispatch = useDispatch();
    const employee = useSelector(state => state.employee.employee);

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [gender, setGender] = useState("");
    const [address, setAddress] = useState("");
    const [msg, setMsg] = useState("");

    useEffect(() => {
        dispatch(fetchEmployee());
    }, [dispatch]);

    const empId = employee.id;

    const updateEmployee = async () => {
        try {
            await axios.put(`http://localhost:8081/api/employee/update/` + empId,
                {
                    'name': name,
                    'email': email,
                    'address': address,
                    'gender': gender,
                    "phone": phone
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

    return (
        <div style={{
            fontFamily: 'Inter, sans-serif', backgroundColor: '#f3f4f6', display: 'flex', justifyContent: 'center',
            alignItems: 'flex-start', minHeight: '100vh', padding: '10px', boxSizing: 'border-box', width: '100%',
        }}>
            <div style={{ maxWidth: '80rem', width: '100%', margin: '0 auto', padding: '0.5rem', }}>
                <div style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}>
                    <div className="updateprofile-card-container">
                        {/* Profile Picture Card */}
                        <div className="w-full profile-card mb-3">
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
                                        src="/images/vaishnavi.jpeg" // Replace with your actual image URL
                                        alt="Profile"
                                        className="profile-image"
                                    />
                                </div>
                            </div>
                        </div>

                        {/* Personal Details Card */}
                        <div className="w-full account-card">
                            <div
                                style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '0.5rem', }}>
                                <h2
                                    style={{ fontSize: '1.25rem', fontWeight: '600', color: '#2d3748', }}>
                                    Personal Information
                                </h2>
                            </div>

                            <div>
                                {/* Username */}
                                <div className="mb-2">
                                    <label className="form-label">
                                        Employee Id
                                    </label>
                                    <div className="display-detail">{employee.id}</div>
                                </div>

                                {/* First and Last Name */}
                                <div className="grid-cols-1 md:grid-cols-2">
                                    <div>
                                        <label className="form-label">Employee Name :</label>
                                        <div className="display-detail">{employee.name}</div>
                                    </div>
                                    <div>
                                        <label className="form-label">Gender</label>
                                        <div className="display-detail">{employee.gender}</div>
                                    </div>
                                </div>

                                {/* Email */}
                                <div className="m2-5">
                                    <label className="form-label">Email</label>
                                    <div className="display-detail">{employee.email}</div>
                                </div>

                                {/* Phone and Birthday */}
                                <div
                                    className="grid-cols-1 md:grid-cols-2"
                                    style={{ marginBottom: '1rem' }}
                                >
                                    <div>
                                        <label className="form-label">Phone number</label>
                                        <div className="display-detail">{employee.phone}</div>
                                    </div>
                                    <div>
                                        <label className="form-label">Address</label>
                                        <div className="display-detail">{employee.address}</div>
                                    </div>
                                </div>
                                <div className="m2-5">
                                    <label className="form-label">Job Title</label>
                                    <div className="display-detail">{employee.jobTitle?.titleName}</div>
                                </div>
                                {/* Organization and Location */}
                                <div className="grid-cols-1 md:grid-cols-2">
                                    <div>
                                        <label className="form-label">Employement Type</label>
                                        <div className="display-detail">{employee.employementType}</div>
                                    </div>
                                    <div>
                                        <label className="form-label">Joining Date</label>
                                        <div className="display-detail">{employee.joiningDate}</div>
                                    </div>
                                </div>
                                <div className="m2-5">
                                    <label className="form-label">Payroll Policy</label>
                                    <div className="display-detail">{employee.payrollPolicy?.name}</div>
                                </div>
                                <div className="grid-cols-1 md:grid-cols-2">
                                    <div>
                                        <label className="form-label">Basic Salary</label>
                                        <div className="display-detail">{employee.jobTitle?.basicSalary}</div>
                                    </div>
                                    <div>
                                        <label className="form-label">Status</label>
                                        <div className="display-detail">{employee.status}</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default UpdateProfile;
