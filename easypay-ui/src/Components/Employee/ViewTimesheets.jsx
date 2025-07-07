import { useDispatch, useSelector } from "react-redux";
import { fetchEmployee } from "../../store/actions/EmployeeAction";
import { useEffect, useState } from "react";
import axios from "axios";

import "./Timesheets.css";
import { Link } from "react-router-dom";

function ViewTimesheets() {

    const [weekStart, setWeekStart] = useState("");
    const [timesheetData, setTimesheetData] = useState([]);
    const [hasSearched, setHasSearched] = useState(false);
    const dispatch = useDispatch();
    const employee = useSelector(state => state.employee.employee);

    useEffect(() => {
        dispatch(fetchEmployee());
    }, [dispatch]);

    const empId = employee.id;

    const showTimesheets = async () => {
        if (!weekStart) {
            alert("Please select week start date.");
            return;
        }
        try {
            const response = await axios.get(
                `http://localhost:8081/api/timesheet/get-by-weekstart/${empId}`,
                {
                    params: { weekStart },
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem("token")}`,
                        "Content-Type": "application/json",
                    },
                }
            );
            setTimesheetData(response.data);
            setHasSearched(true);
        } catch (error) {
            alert("Error retrieving timesheet.");
        }
    }

    return (
        <div className="container mt-5 view-timesheets-container">
            <div className="text-end mb-3">
                <Link to="/employee" className="btn btn-secondary view-timesheets-back">
                    Back
                </Link>
            </div>

            <h2 className="mb-4">View Weekly Timesheet</h2>
            <div className="card card-body">
                <div className="card-text">Employee Id:  {employee.id}</div>
                <div className="card-text">Name:  {employee.name}</div>
            </div>

            <div className="mb-3 view-timesheets-weekstart">
                <label className="view-timesheets-label">Week Start Date:</label>
                <input
                    type="date"
                    className="form-control"
                    value={weekStart}
                    onChange={(e) => setWeekStart(e.target.value)}
                />
            </div>

            <div className="text-center">
                <button onClick={showTimesheets} className="btn btn-primary view-timesheets-button">
                    View Timesheet
                </button>
            </div>



            {timesheetData.length > 0 && (
                <table className="table table-bordered table-striped text-center">
                    <thead className="table-dark">
                        <tr>
                            <th>Day</th>
                            <th>Date</th>
                            <th>Clock In</th>
                            <th>Clock Out</th>
                            <th>Hours Worked</th>
                        </tr>
                    </thead>
                    <tbody>
                        {timesheetData.map((entry, index) => (
                            <tr key={index}>
                                <td>{entry.day}</td>
                                <td>{entry.date}</td>
                                <td>{entry.clockIn}</td>
                                <td>{entry.clockOut}</td>
                                <td>{entry.hoursWorked.toFixed(2)} hrs</td>
                            </tr>
                        ))}

                    </tbody>
                </table>
            )}

            {hasSearched && timesheetData.length === 0 && (
                <p className="text-muted">No data available for selected week.</p>
            )}
        </div>
    )
}

export default ViewTimesheets;