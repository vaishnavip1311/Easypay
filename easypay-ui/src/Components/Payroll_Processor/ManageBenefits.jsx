import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./ManageBenefits.css"

function ManageBenefits() {

    const navigate = useNavigate();
    const [benefits, setBenefits] = useState([]);
    let [msg, setMsg] = useState("");

    useEffect(() => {
        const getAllBenefits = async () => {
            try {
                let response = await axios.get(`http://localhost:8081/api/benefit/get-all`,
                    { headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') } }
                )
                setBenefits(response.data);
            }
            catch (err) {
                console.log(err)
            }
        }
        getAllBenefits();

    }, [benefits])

    const deleteBenefit = async (id) => {
        try {
            await axios.delete(`http://localhost:8081/api/benefit/delete/${id}`, {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token')
                }
            });
            setMsg('✅ Benefit deleted successfully.');
        } catch (err) {
            console.error(err);
            setMsg('❌ Failed to delete benefit.');
        }
    };


    return (
        <div className="benefit-container-style">
            <div className="benefit-header">
                <h1>Manage Benefits</h1>
                <button className="benefit-add-btn" onClick={() => navigate('/payroll-processor/add-benefit')}>+ New Benefit</button>
            </div>
            <div className="options-table">
                <table>
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Benefit Name</th>
                            <th>Benefit Type</th>
                            <th>Allowance Amount</th>
                            <th>Description</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            benefits.map((benefit, index) => (
                                <tr key={index}>
                                    <td><strong>{benefit.id}</strong></td>
                                    <td>{benefit.benefitName}</td>
                                    <td>{benefit.benefitType}</td>
                                    <td>{benefit.allowanceAmount}</td>
                                    <td>{benefit.description}</td>
                                    <td><button className="benefit-edit-btn" onClick={() => navigate(`/payroll-processor/benefit/edit/${benefit.id}`)}>Edit</button>
                                        <button className="benefit-delete-btn" onClick={() => {
                                            if (window.confirm("Are you sure you want to delete this benefit?")) {
                                                deleteBenefit(benefit.id);
                                            }
                                        }}>❌</button></td>
                                </tr>
                            ))
                        }

                    </tbody>
                </table>
                {
                    msg != "" ? <div className="mb-4">
                        <div className="alert alert-primary">
                            {msg}
                        </div>
                    </div> : ""
                }
            </div>

        </div>
    )
}

export default ManageBenefits;