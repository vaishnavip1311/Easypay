import "../../Components/Sidebar.css"
function Sidebar() {

    return (
        <>
            <aside className="sidebar">
                <div className="sidebar-header">
                    <img src="images/logo.png" alt="logo" />
                    <h2>CodingLab</h2>
                </div>
                <ul className="sidebar-links">
                    <h4>
                        <span>Main Menu</span>
                        <div className="menu-separator"></div>
                    </h4>
                    <li>
                        <a href="#">
                            <span className="material-symbols-outlined"> dashboard </span>Dashboard</a>
                    </li>
                    <li>
                        <a href="#"><span className="material-symbols-outlined"> overview </span>Overview</a>
                    </li>
                    <li>
                        <a href="#"><span className="material-symbols-outlined"> monitoring </span>Analytic</a>
                    </li>
                    <h4>
                        <span>General</span>
                        <div className="menu-separator"></div>
                    </h4>
                    <li>
                        <a href="#"><span className="material-symbols-outlined"> folder </span>Projects</a>
                    </li>
                    <li>
                        <a href="#"><span className="material-symbols-outlined"> groups </span>Groups</a>
                    </li>
                    <li>
                        <a href="#"><span className="material-symbols-outlined"> move_up </span>Transfer</a>
                    </li>
                    <li>
                        <a href="#"><span className="material-symbols-outlined"> flag </span>All Reports</a>
                    </li>
                    <li>
                        <a href="#"><span className="material-symbols-outlined">
                            notifications_active </span>Notifications</a>
                    </li>
                    <h4>
                        <span>Account</span>
                        <div className="menu-separator"></div>
                    </h4>
                    <li>
                        <a href="#"><span className="material-symbols-outlined"> account_circle </span>Profile</a>
                    </li>
                    <li>
                        <a href="#"><span className="material-symbols-outlined"> settings </span>Settings</a>
                    </li>
                    <li>
                        <a href="#"><span className="material-symbols-outlined"> logout </span>Logout</a>
                    </li>
                </ul>
                <div className="user-account">
                    <div className="user-profile">
                        <img src="images/profile-img.jpg" alt="Profile Image" />
                        <div className="user-detail">
                            <h3>Eva Murphy</h3>
                            <span>Web Developer</span>
                        </div>
                    </div>
                </div>
            </aside>
        </>
    )
}

export default Sidebar;