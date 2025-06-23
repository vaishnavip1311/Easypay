import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchEmployee } from "../../store/actions/EmployeeAction";
import axios from "axios";
import "./ManageLeave.css";
import { Link } from "react-router-dom";

function ManageLeave() {

    const[leaves , setLeaves]=useState([]);

    const dispatch = useDispatch();
    const employee = useSelector(state => state.employee.employee);

    useEffect(() => {
        dispatch(fetchEmployee());
    }, [dispatch]);

    const empId = employee?.id;

    useEffect(()=>{
        if (!empId) return;
        const fetchLeaves =async() =>{
            try {
                const response = await axios.get('http://localhost:8081/api/leave/get-all/' + empId,
                    { headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') } }
                );
                //console.log(response.data)
                setLeaves(response.data);
            } catch (error) {
                console.log(error)
            }
        }
        fetchLeaves();

    },[])

    return(
        
        <div className="container">
            <h2>Manage Leaves</h2>
            <div className="card card-body">
                <div className="card-text">Employee Id:  {employee.id}</div>
                <div className="card-text">Name:  {employee.name}</div>
            </div>
            <table className="table table-bordered">
                <thead>
                    <tr>
                        <th scope="col">Leave ID</th>
                        <th scope="col">Type</th>
                        <th scope="col">From</th>
                        <th scope="col">To</th>
                        <th scope="col">STATUS</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        leaves.map((leave, index) => (
                            <tr key={leave.id}>
                                <td>{leave.id}</td>
                                <td>{leave.leaveType}</td>
                                <td>{leave.startDate}</td>
                                <td>{leave.endDate}</td>
                                <td>{leave.status}</td>
                                <td><Link className="btn btn-secondary" to={`/employee/leave-details/${leave.id}`}>View</Link></td>
                                
                            </tr>
                        ))
                    }

                </tbody>
            </table>

            <div className="button-wrapper">
                <Link className="btn btn-primary" to={`/employee/apply-leave`}>Apply Leave</Link>
            </div>
        </div>
    )
}

export default ManageLeave;
