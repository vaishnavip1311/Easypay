import { useDispatch, useSelector } from "react-redux";
import "./applyleave.css";
import { fetchEmployee } from "../../store/actions/EmployeeAction";
import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

function ApplyLeave() {

    let[type ,setType] =useState("");
    let[startDate ,setStartDate] =useState("");
    let[endDate ,setEndDate] =useState("");
    let[reason ,setReason] =useState("");
    let[msg, setMsg] = useState("");

    const dispatch = useDispatch();
    const employee = useSelector(state => state.employee.employee);

    useEffect(() => {
        dispatch(fetchEmployee());
    }, [dispatch]);

    const empId = employee.id;

    const addLeave = async()=>{
        try {
            await axios.post(`http://localhost:8081/api/leave/add/`+ empId,
                {
                    'leaveType': type,
                    'startDate': startDate,
                    'endDate': endDate,
                    'reason':reason
                },
                { headers: 
                    { 'Authorization': 'Bearer ' + localStorage.getItem('token') ,
                    'Content-Type': 'application/json'
                    } }
            );
            setMsg("Leave Applied successfully!!!!")

        } catch (error) {
            setMsg("Operation Failed, Try again")
        }
    }

    return (
        <>
            <div className="card">
                <div className="card-body">
                    <div className="card-text">Employee ID:{employee.id}</div>
                    <div className="card-text">Employee Name:{employee.name}</div>
                </div>
            </div>
            <div className="card">
                <div className="card-body">
                    <h5>📝Apply For Leave</h5>
                    {
                            msg != "" ? <div className="mb-4">
                                <div className="alert alert-primary">
                                    {msg}
                                </div>
                            </div> : ""
                        }
                    <div className="mb-3">
                        <label>Leave Type :&nbsp;&nbsp;&nbsp;</label>
                        <select id="type" name="type" value={type} onChange={$e => setType($e.target.value)} className="form-select">
                            <option value="">Select Leave Type</option>
                            <option value="sick">Sick</option>
                            <option value="casual">Casual</option>
                            <option value="unpaid">Unpaid</option>
                            <option value="earned">Earned</option>
                        </select>
                    </div>
                    <div className="mb-3">
                        <label>Start Date :&nbsp;&nbsp;&nbsp;</label>
                        <input type="date" onChange={$e => setStartDate($e.target.value)}></input>
                    </div>
                    <div className="mb-3">
                        <label>End Date :&nbsp;&nbsp;&nbsp;</label>
                        <input type="date" onChange={$e => setEndDate($e.target.value)}></input>
                    </div>
                    <div>
                        <label>Reason:&nbsp;&nbsp;&nbsp;</label>
                        <textarea rows="4" cols="50" onChange={$e => setReason($e.target.value)}></textarea>
                    </div>
                    <div className="mb-3">
                        <button className="btn btn-primary" onClick={()=>addLeave()}>Apply</button>
                        
                        <Link className="btn btn-primary" to="/employee/manage-leave">Cancel</Link>
                    </div>
                </div>
            </div>
        </>
    )
}

export default ApplyLeave;