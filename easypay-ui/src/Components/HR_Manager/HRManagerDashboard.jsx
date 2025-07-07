import HRSidebar from "./HRSidebar";
import '../dashboard.css';
import Navbar from "../Navbar";
import { Outlet } from "react-router-dom";

function HRManagerDashboard() {

    return(
        <div style={{display: 'flex'}}>
            <HRSidebar />
            <div style={{flexGrow: 1}}>
                <Navbar />
                <div className="employee-dashboard-content"> 
                   <Outlet />
                </div>
            </div>
        </div>
    )
}

export default HRManagerDashboard;