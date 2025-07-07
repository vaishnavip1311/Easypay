import axios from "axios";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchEmployee } from "../../store/actions/EmployeeAction";
import { Link } from "react-router-dom";

import "./Timesheets.css";

function SubmitTimesheets() {

    const initialWeek = [
        { day: "Monday", date: "", clockIn: "", clockOut: "", weekStart: "" },
        { day: "Tuesday", date: "", clockIn: "", clockOut: "", weekStart: "" },
        { day: "Wednesday", date: "", clockIn: "", clockOut: "", weekStart: "" },
        { day: "Thursday", date: "", clockIn: "", clockOut: "", weekStart: "" },
        { day: "Friday", date: "", clockIn: "", clockOut: "", weekStart: "" },
        { day: "Saturday", date: "", clockIn: "", clockOut: "", weekStart: "", disabled: true },
        { day: "Sunday", date: "", clockIn: "", clockOut: "", weekStart: "", disabled: true },
    ];

    let[msg, setMsg] = useState("");

    const dispatch = useDispatch();
    const employee = useSelector(state => state.employee.employee);

    useEffect(() => {
        dispatch(fetchEmployee());
    }, [dispatch]);

    const empId = employee.id;

    const [weekTimesheet, setWeekTimesheet] = useState(initialWeek);
    const [responseData, setResponseData] = useState([]);

    const handleInputChange = (index, field, value) => {
        const updated = [...weekTimesheet];
        updated[index][field] = value;

        // Set weekStart for all sheets based on Monday
        if (field === "date" && index === 0) {
            updated.forEach(row => (row.weekStart = value));
        }

        setWeekTimesheet(updated);
    };

    const handleSubmit = async () => {
    try {
        const mondayDate = weekTimesheet[0].date;

        const filtered = weekTimesheet
            .filter(row => !row.disabled && row.date && row.clockIn && row.clockOut)
            .map(row => ({
                day: row.day,
                date: row.date,
                clockIn: row.clockIn,
                clockOut: row.clockOut,
                weekStart: mondayDate  // Assign Monday as common week start
            }));

        //console.log("Payload:", filtered);

        const response = await axios.post(
            `http://localhost:8081/api/timesheet/add/${empId}`,
            filtered,
            {
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('token'),
                    'Content-Type': 'application/json'
                }
            }
        );

        setResponseData(response.data);
        setMsg("Timesheet submitted successfully");
    } catch (error) {
        setMsg("Operation Failed, Try again");
    }
};

    return (
        <div className="container mt-5 submit-timesheets-container">
            <h2 className="mb-4">Weekly Timesheet</h2>
            <table className="table table-bordered table-hover text-center">
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
                    {weekTimesheet.map((entry, index) => (
                        <tr key={index}>
                            <td>{entry.day}</td>
                            <td>
                                <input
                                    type="date"
                                    className="form-control"
                                    value={entry.date}
                                    onChange={(e) => handleInputChange(index, "date", e.target.value)}
                                    disabled={entry.disabled}
                                />
                            </td>
                            <td>
                                <input
                                    type="time"
                                    className="form-control"
                                    value={entry.clockIn}
                                    onChange={(e) => handleInputChange(index, "clockIn", e.target.value)}
                                    disabled={entry.disabled}
                                />
                            </td>
                            <td>
                                <input
                                    type="time"
                                    className="form-control"
                                    value={entry.clockOut}
                                    onChange={(e) => handleInputChange(index, "clockOut", e.target.value)}
                                    disabled={entry.disabled}
                                />
                            </td>
                            <td>
                                {responseData[index]?.hoursWorked !== undefined
                                    ? `${responseData[index].hoursWorked} hrs`
                                    : "-"}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            <div className="text-center">
                <button onClick={handleSubmit} className="btn btn-primary px-4 py-2">
                    Submit Timesheet
                </button>
                <Link className="btn btn-primary px-4 py-2" to="/employee">
                    Back
                </Link>
            </div>

            {responseData.length > 0 && (
                <div className="alert alert-success mt-4">
                    {msg}
                </div>
            )}
        </div>
    );
}

export default SubmitTimesheets;
