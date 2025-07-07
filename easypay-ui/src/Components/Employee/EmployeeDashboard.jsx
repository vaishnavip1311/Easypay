import { Link, Outlet } from 'react-router-dom';
import Navbar from '../Navbar';
import Sidebar from './Sidebar';
import './EmployeeDashboard.css';

function EmployeeDashboard() {

    return(
        <div style={{display: 'flex'}}>
            <Sidebar />
            <div style={{flexGrow: 1}}>
                <Navbar />
                <div className="employee-dashboard-content"> 
                    <Outlet />
                </div>
            </div>
        </div>
    )
}

export default EmployeeDashboard;
