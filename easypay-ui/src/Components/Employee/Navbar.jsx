import { useState } from "react";
import { useNavigate } from "react-router-dom";
import logo from "../../assets/logo.png"
function Navbar() {


    const [name,] = useState(localStorage.getItem('name'));
    const navigate = useNavigate();
    const logout = () => {
        localStorage.clear();
        navigate("/")
    }

    return (
        <nav className="navbar navbar-light bg-light d-flex align-items-center" style={{padding: '0 1rem'}}>
            <div className="navbar-brand">
                <img src={logo} alt="Logo" style={{height: '40px'}} />
            </div>
            <div style={{flexGrow: 1, textAlign: 'center'}}>
                <h5 style={{margin: 0}}>Welcome {name}</h5>
            </div>
            <div>
                <button 
                  className="navbar-btn" 
                  onClick={() => logout()} 
                  style={{backgroundColor: '#2c4661', color: 'white', border: 'none', width:'100px', height:'40px', fontSize:'18px', textAlign:'center'}}
                >LogOut
                </button>
            </div>
        </nav>
    )
}

export default Navbar;
