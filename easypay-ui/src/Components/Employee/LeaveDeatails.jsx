import axios from "axios";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import "./leavedetails.css"

function LeaveDetails() {

    const params = useParams();
    const[leave , setLeave] = useState({});

    useEffect (()=>{
        const getLeave = async()=>{
            try {
              const response = await axios.get('http://localhost:8081/api/leave/get-one/' + params.lid,
                    { headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') } }
                );
                //console.log(response.data)
                setLeave(response.data);
            
            } catch (error) {
               console.log(error);
            }
        }
        getLeave();
    })
    return(
        <div className="leave-details-container">
            <h2>Leave Details</h2>
            {
                <div className="leave-details-card-body">
                    <div className="leave-details-card-text">Leave Id:  {leave.id}</div>
                    <div className="leave-details-card-text">Start Date:  {leave.startDate}</div>
                    <div className="leave-details-card-text">End Date:  {leave.endDate}</div>
                    <div className="leave-details-card-text">Leave Type:  {leave.leaveType}</div>
                    <div className="leave-details-card-text">Reason:  {leave.reason}</div>
                    <div className="leave-details-card-text">Status:  {leave.status}</div>
                </div>
                
            }
            <div className="go-back-wrapper">
            <Link className="btn btn-primary" to="/employee/manage-leave">Go Back</Link>
            </div>
        </div>
    )
}

export default LeaveDetails;
