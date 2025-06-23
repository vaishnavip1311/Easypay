import { Link, Outlet } from 'react-router-dom';
import Navbar from './Navbar';
import Sidebar from './Sidebar';
import './dashboard.css';

function EmployeeDashboard() {

    return(
        <div style={{display: 'flex'}}>
            <Sidebar />
            <div style={{flexGrow: 1}}>
                <Navbar />
                <div className="employee-dashboard-content"> 
                    <Outlet />

                    <div className='card-body'>
                        <Link className='btn btn-primary' to="/employee/profile"></Link>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default EmployeeDashboard;
