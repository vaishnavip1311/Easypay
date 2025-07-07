import { Outlet } from "react-router-dom";
import Navbar from "../Navbar";
import ProcessorSidebar from "./ProcessorSidebar";

function ProcessorDashboard() {

    return(
        <div style={{display: 'flex'}}>
            <ProcessorSidebar />
            <div style={{flexGrow: 1}}>
                <Navbar />
                <div className="employee-dashboard-content"> 
                    <Outlet />
                </div>
            </div>
        </div>
    )
}

export default ProcessorDashboard;