import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import './ViewAllPayrolls.css';

function ViewAllPayrolls() {
    const navigate = useNavigate();
    const [payrolls, setPayrolls] = useState([]);
    const [filteredPayrolls, setFilteredPayrolls] = useState([]);
    const [statusFilter, setStatusFilter] = useState("All");
    const [msg, setMsg] = useState("");

    useEffect(() => {
        const getAllPayrolls = async () => {
            try {
                let response = await axios.get(`http://localhost:8081/api/payroll/get-all`, {
                    headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
                });
                setPayrolls(response.data);
                setFilteredPayrolls(response.data);
            } catch (err) {
                console.log(err);
            }
        };
        getAllPayrolls();
    }, []);

    // Filter when dropdown changes
    useEffect(() => {
        if (statusFilter === "All") {
            setFilteredPayrolls(payrolls);
        } else {
            const filtered = payrolls.filter(p => p.status === statusFilter);
            setFilteredPayrolls(filtered);
        }
    }, [statusFilter, payrolls]);

    return (
        <div className="payroll-container">
            <div className="payroll-header">
                <h1 className="payroll-title">All Payrolls</h1>

                <div className="payroll-filter">
                    <label htmlFor="statusFilter">Filter by Status:</label>
                    <select
                        id="statusFilter"
                        value={statusFilter}
                        onChange={(e) => setStatusFilter(e.target.value)}
                    >
                        <option value="All">All</option>
                        <option value="Processed">Processed</option>
                        <option value="Pending">Pending</option>
                    </select>
                </div>
            </div>

            <div className="payroll-table-container">
                <table className="payroll-table">
                    <thead>
                        <tr>
                            <th>Employee ID</th>
                            <th>Name</th>
                            <th>Pay Period</th>
                            <th>Net Salary</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            filteredPayrolls.length > 0 ? (
                                filteredPayrolls.map((payroll, index) => (
                                    <tr key={index}>
                                        <td>{payroll.employee?.id}</td>
                                        <td>{payroll.employee?.name}</td>
                                        <td>{payroll.payPeriod}</td>
                                        <td>₹{payroll.netSalary}</td>
                                        <td>{payroll.status}</td>
                                        <td>
                                            <button
                                                className="payroll-view-btn"
                                                onClick={() => navigate(`/payroll-processor/payroll/${payroll.id}`)}
                                            >
                                                View
                                            </button>
                                        </td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan="5" className="text-center">No payrolls found.</td>
                                </tr>
                            )
                        }
                    </tbody>
                </table>

                {msg && (
                    <div className="payroll-message">
                        <div className="payroll-alert">{msg}</div>
                    </div>
                )}
            </div>
            <button
                className="generate-payroll-btn"
                onClick={() => navigate("/payroll-processor/generate-payroll")}
            >
                Generate Payroll
            </button>

        </div>
    );
}

export default ViewAllPayrolls;
