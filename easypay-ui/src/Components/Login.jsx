import '../Components/Login.css'
import axios, { AxiosHeaders } from "axios";
import { useState } from "react";
import { useNavigate } from 'react-router-dom';

function Login() {

    let [username, setUsername] = useState("");
    let [password, setPassword] = useState("");
    let [msg, setMsg] = useState("");
    const navigate = useNavigate();

    const processLogin = async (event) => {
        event.preventDefault(); // Prevent form submission reload
        // Encode username and password using btoa 
        let encodedString = window.btoa(username + ':' + password);
        //console.log(encodedString)
        //console.log(window.atob(encodedString))
        try {
            const response = await axios.get('http://localhost:8081/api/user/token', {
                headers: { "Authorization": "Basic " + encodedString }
            })
            //console.log(response.data)
            let token = response.data; //<-- this is our access token, save it for later usage. (redux,localstorage)
            localStorage.setItem('token', token); //<-- saving token for future use in browsers local storage mem
            // Step 2: Get User Details 
            let details = await axios.get('http://localhost:8081/api/user/details', {
                headers: { "Authorization": "Bearer " + token }
            }
            )
            //console.log(details)

            let name = details.data.name;
            localStorage.setItem('name', name);
            let role = details.data.user.role;
            switch (role) {
                case "EMPLOYEE":
                    navigate("/employee")
                    break;
                case "HR MANAGER":
                    navigate("/hr-manager")
                    break;
                case "SUPERVISOR":
                    navigate("/supervisor")
                    break;
                case "PAYROLL PROCESSOR":
                    navigate("/payroll-processor")
                    break;
                default:
                    setMsg("Login Disabled, Contact Admin at admin@example.com")
            }
            setMsg("Login Success!!!")
        }
        catch (err) {
            setMsg('Invalid Credentials')
        }

    }

    return (
        <div className='login-background'>
            <div className='wrapper'>
                <form onSubmit={processLogin}>
                    <h3>Login Here</h3>
                    {msg !== "" ? <div>
                        <div className="alert alert-info" >
                            {msg}
                        </div>
                    </div> : ""}
                    <label htmlFor="username">Enter Username</label>
                    <input type="text" placeholder="Email or Phone" required onChange={($e) => setUsername($e.target.value)} />

                    <label htmlFor="password">Enter Password</label>
                    <input type="password" placeholder="Password" required onChange={($e) => setPassword($e.target.value)} />

                    <button>Log In</button>

                </form>
            </div>

        </div>
    )
}

export default Login;
