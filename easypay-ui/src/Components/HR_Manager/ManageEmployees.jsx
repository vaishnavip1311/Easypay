import axios from "axios";
import { useEffect, useState } from "react";
import "./ManageEmployees.css"
import { useNavigate } from "react-router-dom";

function ManageEmployees() {

    const navigate = useNavigate();
    const [employees, setEmployees] = useState([]);
    let [page, setpage] = useState(0);
    let [size, setSize] = useState(5);
    let [msg, setMsg] = useState("");

    useEffect(() => {
        const getAllEmployees = async () => {
            try {
                let response = await axios.get(`http://localhost:8081/api/employee/get-all?page=${page}&size=${size}`,
                    { headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') } }
                )
                setEmployees(response.data)
            }
            catch (err) {
                console.log(err)
            }
        }
        getAllEmployees();
    }, [page])

    const handleRemove = async (id) => {
        try {
            await axios.put(`http://localhost:8081/api/employee/update-status/${id}?status=inactive`,
                { status: "INACTIVE" },
                {
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('token'),
                        'Content-Type': 'application/json'
                    }
                }
            );

            setEmployees(prev => prev.filter(emp => emp.id !== id));
            setMsg("Employee removed successfully")
        } catch (err) {
            setMsg("Failed to deactivate employee");
        }
    };

    return (
        <div className="manage-employees-display-employee-container">
            {
                msg != "" ? <div className="mb-4">
                    <div className="alert alert-primary">
                        {msg}
                    </div>
                </div> : ""
            }
            <h1>
                📋Employee List
            </h1>
            <ul className="manage-employees-responsive-table">
                <li className="manage-employees-table-header">
                    <div className="manage-employees-col-1">ID</div>
                    <div className="manage-employees-col-2">Name</div>
                    <div className="manage-employees-col-3">Jobtitle</div>
                    <div className="manage-employees-col-4">Employement Type</div>
                    <div className="manage-employees-col-5">Status</div>
                    <div className="manage-employees-col-6">Action</div>
                </li>
                {
                    employees.map((e, index) => (
                        <li className="manage-employees-table-row" key={index}>
                            <div className="manage-employees-col-1" data-label="Emp Id">{e.id}</div>
                            <div className="manage-employees-col-2" data-label="Emp Name">{e.name}</div>
                            <div className="manage-employees-col-3" data-label="Jobtitle">{e.jobTitle?.titleName}</div>
                            <div className="manage-employees-col-4" data-label="Employement Type">{e.employementType}</div>
                            <div className="manage-employees-col-5" data-label="Status">{e.status}</div>
                            <div className="manage-employees-col-6" data-label="Action" >
                                <button className="manage-employees-custom-btn-danger" onClick={() => {
                                    if (window.confirm("Are you sure you want to deactivate this employee?")) {
                                        handleRemove(e.id);
                                    }
                                }}>Remove</button>
                            </div>
                        </li>
                    ))
                }
            </ul>

            <nav aria-label="Page navigation example">
                <ul className="manage-employees-pagination">
                    <li className="manage-employees-page-item">
                        <a className="manage-employees-page-link" href="#" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                            <button onClick={() => {
                                setpage(page - 1)
                                //console.log(page)
                            }}>Previous</button>
                        </a>
                    </li>
                    <li className="manage-employees-page-item"><a className="manage-employees-page-link" onClick={() => setpage(0)}>1</a></li>
                    <li className="manage-employees-page-item"><a className="manage-employees-page-link" onClick={() => setpage(1)}>2</a></li>
                    <li className="manage-employees-page-item"><a className="manage-employees-page-link" onClick={() => setpage(2)}>3</a></li>
                    <li className="manage-employees-page-item">
                        <a className="manage-employees-page-link" href="#" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                            <button onClick={() => {
                                setpage(page + 1)
                            }}>Next</button>
                        </a>
                    </li>
                </ul>
            </nav>

            <div>
                <button className="btn editprofile-btn editprofile-btn-primary" onClick={() => navigate('/hr-manager/add-employee')}>Add Employee</button>
                <button className="btn editprofile-btn editprofile-btn-primary" onClick={() => navigate('/hr-manager')}>Back To Dashboard</button>
            </div>
        </div>
    )
}

export default ManageEmployees;
