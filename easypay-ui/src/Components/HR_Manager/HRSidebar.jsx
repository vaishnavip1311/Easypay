import { Link } from "react-router-dom";
import "./HRSidebar.css"
import { useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import { fetchHR } from "../../store/actions/HRAction";

function HRSidebar() {

    const dispatch = useDispatch();
    const hr = useSelector(state => state.hr.hr);
    
    useEffect(() => {
        dispatch(fetchHR());
    }, [dispatch]);


    return(
        <aside className="sidebar">
      <ul className="sidebar-links">
        {/* Main Menu Section */}
        <h4>
          <span>Main Menu</span>
          <div className="menu-separator"></div>
        </h4>
        {/* Use Link components for navigation */}
        <li>
          <Link to="/hr-manager" className="active">
            <span className="material-symbols-outlined">dashboard</span>
            Dashboard
          </Link>
        </li>
        <li>
          <Link to="/hr-manager/hr-profile">
            <span className="material-symbols-outlined">account_circle</span>
            Profile
          </Link>
        </li>
        <li>
          <Link to="/hr-manager/manage-employees">
            <span className="material-symbols-outlined">event_available</span>
            Manage Employees
          </Link>
        </li>
        <li>
          <Link to="/hr-manager/manage-payroll-policy">
            <span className="material-symbols-outlined">schedule</span>
            Manage Payroll Policies
          </Link>
        </li>
        <li>
          <Link to="/hr-manager/manage-jobtitles">
            <span className="material-symbols-outlined">fact_check</span>
            Manage Job Titles
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
          <img style={{'height':'50px'}} src={`../images/${hr.profilePic}`} alt="Profile Image" /> {/* Placeholder image */}
          <div className="user-detail">
            <h3>{hr.name}</h3>
            <span>{hr.user?.role}</span>
          </div>
        </div>
      </div>
    </aside>
    )
}

export default HRSidebar;