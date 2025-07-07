import Navbar from "../Navbar";
import SupervisorSidebar from "./SupervisorSidebar";

function SupervisorDashboard(){

    return(
        <div style={{display: 'flex'}}>
            <SupervisorSidebar />
            <div style={{flexGrow: 1}}>
                <Navbar />
                <div className="employee-dashboard-content"> 
                    <Outlet />
                </div>
            </div>
        </div>
    )

}

export default SupervisorDashboard;