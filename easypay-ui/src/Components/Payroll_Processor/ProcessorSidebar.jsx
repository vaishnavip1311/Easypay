import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { fetchProcessor } from "../../store/actions/ProcessorAction";

function ProcessorSidebar() {

  const dispatch = useDispatch();
  const processor = useSelector(state => state.processor.processor);

  useEffect(() => {
      dispatch(fetchProcessor());
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
          <Link to="/payroll-processor" className="active">
            <span className="material-symbols-outlined">dashboard</span>
            Dashboard
          </Link>
        </li>
        <li>
          <Link to="/payroll-processor/processor-profile">
            <span className="material-symbols-outlined">account_circle</span>
            Profile
          </Link>
        </li>
        <li>
          <Link to="/payroll-processor/view-payrolls">
            <span className="material-symbols-outlined">event_available</span>
            View Payrolls
          </Link>
        </li>
        <li>
          <Link to="/payroll-processor/manage-benefits">
            <span className="material-symbols-outlined">schedule</span>
            Manage Benefits
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
          <img style={{'height':'50px'}} src={`../images/${processor.profilePic}`} alt="Profile Image" /> {/* Placeholder image */}
          <div className="user-detail">
            <h3>{processor.name}</h3>
            <span>{processor.user?.role}</span>
          </div>
        </div>
      </div>
    </aside>
    )
}

export default ProcessorSidebar;