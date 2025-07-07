import { Link, useNavigate } from "react-router-dom";
import OnboardingForm from "./OnboardingForm";
import { useEffect, useState } from "react";
import axios from "axios";
import "./ManageJobtitles.css"

function ManageJobtitles() {
    const navigate = useNavigate(); 
    const [jobtitles, setJobtitles] = useState([]);
    const [msg, setMsg] = useState("");

    useEffect(() => {

        const getAllJobtitles = async () => {
            try {
                let response = await axios.get(`http://localhost:8081/api/jobtitle/get-all`,
                    { headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') } }
                )
                setJobtitles(response.data);
            }
            catch (err) {
                console.log(err)
            }
        }
        getAllJobtitles();

    }, [])

    const handleDelete = async (id) => {
    if (window.confirm("Are you sure you want to delete this job title?")) {
        try {
            await axios.delete(`http://localhost:8081/api/jobtitle/delete/${id}`, {
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                }
            });
            // Refresh the list
            setJobtitles(prev => prev.filter(jt => jt.id !== id));
            setMsg("Jobtitle deleted successfully")
        } catch (err) {
            setMsg("Failed to delete job title");
        }
    }
};

    return (
        <div className="jobtitle-container-style">
            <div className="jobtitle-header">
                <h1>Manage Jobtitles</h1>
                    <button className="jobtitle-add-btn" onClick={() => navigate('/hr-manager/add-jobtitle')}>+ New Jobtitle</button>
            </div>
            <div className="options-table">
                <table>
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Title</th>
                            <th>Basic Salary</th>
                            <th>HRA</th>
                            <th>DA</th>
                            <th>HRA</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            jobtitles.map((jobTitle, index) => (
                                <tr key={index}>
                                    <td><strong>{jobTitle.id}</strong></td>
                                    <td>{jobTitle.titleName}</td>
                                    <td>{jobTitle.basicSalary}</td>
                                    <td>{jobTitle.hraRate}</td>
                                    <td>{jobTitle.hraRate}</td>
                                    <td>{jobTitle.otherAllowances}</td>
                                    <td><button className="jobtitle-edit-btn" onClick={() => navigate(`/hr-manager/edit-jobtitle/${jobTitle.id}`)}>Edit</button>
                                        <button className="jobtitle-delete-btn" onClick={() => handleDelete(jobTitle.id)}>❌</button></td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
                {msg && <div className="alert-message">{msg}</div>}
            </div>

        </div>
    )
}

export default ManageJobtitles;