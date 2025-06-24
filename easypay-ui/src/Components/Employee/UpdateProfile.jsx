import { useSelector, useDispatch } from "react-redux";
import { fetchEmployee } from "../../store/actions/EmployeeAction";
import { useEffect, useState } from "react";
import axios from "axios";
import './updateprofile.css';


function UpdateProfile() {

    let [name, setName] = useState("");
    let [email, setEmail] = useState("");
    let [phone, setPhone] = useState("");
    let [gender, setGender] = useState("");
    let [address, setAddress] = useState("");
    let [msg, setMsg] = useState("");

    const dispatch = useDispatch();
    const employee = useSelector(state => state.employee.employee);

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
        <div className="card">
            <div className="card-body">
                <h5>📝Update Details</h5>
                {
                    msg != "" ? <div className="mb-4">
                        <div className="alert alert-primary">
                            {msg}
                        </div>
                    </div> : ""
                }
                <div className="mb-3">
                    <label>Name :&nbsp;&nbsp;&nbsp;</label>
                    <input type="text" value={employee.name} onChange={$e => setName($e.target.value)}></input>
                </div>
                <div className="mb-3">
                    <label>Gender :&nbsp;&nbsp;&nbsp;</label>
                    <select id="gender" name="gender" value={gender} onChange={$e => setGender($e.target.value)} className="gender-select">
                        <option value="">Select Gender</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Other">Other</option>
                    </select>
                </div>
                <div className="mb-3">
                    <label>Email :&nbsp;&nbsp;&nbsp;</label>
                    <input type="text" value={employee.email} onChange={$e => setEmail($e.target.value)}></input>
                </div>
                <div className="mb-3">
                    <label>Phone :&nbsp;&nbsp;&nbsp;</label>
                    <input type="text" value={employee.phone} onChange={$e => setPhone($e.target.value)}></input>
                </div>
                <div>
                    <label>Address:&nbsp;&nbsp;&nbsp;</label>
                    <textarea rows="4" cols="50" value={employee.address} onChange={$e => setAddress($e.target.value)}></textarea>
                </div>
                <div className="mb-3">
                    <button className="btn btn-primary" onClick={() => updateEmployee()}>Update</button>

                    <button className="btn btn-primary">Cancel</button>
                </div>
            </div>
        </div>
    );
}

export default UpdateProfile;
