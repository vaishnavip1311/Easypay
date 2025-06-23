import "./Sidebar.css";
import React from 'react';
import { Link } from 'react-router-dom';

function Sidebar() {
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
          <Link to="/employee/details">
            <span className="material-symbols-outlined">person_outline</span>
            View Profile
          </Link>
        </li>
        <li>
          <Link to="/employee/edit-details">
            <span className="material-symbols-outlined">edit_note</span>
            Update Profile
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
            <span className="material-symbols-outlined">account_circle</span>
            Profile
          </a>
        </li>
        <li>
          <a href="#">
            <span className="material-symbols-outlined">notifications</span> {/* notifications_active looks different */}
            Notifications
          </a>
        </li>
        <li>
          <a href="#">
            <span className="material-symbols-outlined">settings</span>
            Settings
          </a>
        </li>
        <li>
          <a href="#">
            <span className="material-symbols-outlined">logout</span>
            Logout
          </a>
        </li>
      </ul>

      <div className="user-account">
        <div className="user-profile">
          {/* If you have a local profile image, use it here: */}
          {/* <img src={profileImg} alt="Profile Image" /> */}
          {/* Otherwise, use a placeholder or remove if not needed */}
          <img src="/images/vaishnavi.jpeg" alt="Profile Image" /> {/* Placeholder image */}
          <div className="user-detail">
            <h3>Vaishnavi Patil</h3>
            <span>Senior Developer</span>
          </div>
        </div>
      </div>
    </aside>
  );
}

export default Sidebar;
