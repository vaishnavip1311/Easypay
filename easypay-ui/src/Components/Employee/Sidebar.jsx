import "./Sidebar.css";
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from 'react-router-dom';
import { fetchEmployee } from "../../store/actions/EmployeeAction";

function Sidebar() {

  const dispatch = useDispatch();
  const employee = useSelector(state => state.employee.employee);
    
    useEffect(() => {
        dispatch(fetchEmployee());
    }, [dispatch]);
  
  return (
    <aside className="sidebar">
      <ul className="sidebar-links">
        {/* Main Menu Section */}
        <h4>
          <span>Main Menu</span>
          <div className="menu-separator"></div>
        </h4>
        {/* Use Link components for navigation */}
        <li>
          <Link to="/employee" className="active">
            <span className="material-symbols-outlined">dashboard</span>
            Dashboard
          </Link>
        </li>
        <li>
          <Link to="/employee/profile">
            <span className="material-symbols-outlined">account_circle</span>
            Profile
          </Link>
        </li>
        <li>
          <Link to="/employee/manage-leave">
            <span className="material-symbols-outlined">event_available</span>
            Manage Leave
          </Link>
        </li>
        <li>
          <Link to="/employee/submit-timesheets">
            <span className="material-symbols-outlined">schedule</span>
            Submit Timesheets
          </Link>
        </li>
        <li>
          <Link to="/employee/view-timesheets">
            <span className="material-symbols-outlined">fact_check</span>
            View Timesheets
          </Link>
        </li>
        <li>
          <Link to="/employee/view-salary">
            <span className="material-symbols-outlined">paid</span>
            View Salary
          </Link>
        </li>

        {/* Account Section */}
        <h4>
          <span>Account</span>
          <div className="menu-separator"></div>
        </h4>
        <li>
          <a href="#">
            <span className="material-symbols-outlined">notifications</span> {/* notifications_active looks different */}
            Notifications
          </a>
        </li>
      </ul>

      <div className="user-account">
        <div className="user-profile">
          <img style={{'height':'50px'}} src={`../images/${employee.profilePic}`} alt="Profile Image" /> {/* Placeholder image */}
          <div className="user-detail">
            <h3>{employee.name}</h3>
            <span>{employee.jobTitle?.titleName}</span>
          </div>
        </div>
      </div>
    </aside>
  );
}

export default Sidebar;
