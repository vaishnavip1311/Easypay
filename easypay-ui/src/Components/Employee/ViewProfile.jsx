import axios from "axios";
import { useEffect, useState } from "react";
import './ViewProfile.css';
import { useDispatch, useSelector } from "react-redux";
import { fetchEmployee } from "../../store/actions/EmployeeAction";

function ViewProfile() {

    const dispatch = useDispatch();
    const employee = useSelector(state => state.employee.employee);

    useEffect(() => {
        dispatch(fetchEmployee());

    }, [dispatch]);


    return (
        <div className="container">
            <h2>Personal Information</h2>
            {
                <div className="card">
                    <div className="card-body">
                        <div className="card-text">Employee Id:  {employee.id}</div>
                        <div className="card-text">Name:  {employee.name}</div>
                        <div className="card-text">Email:  {employee.email}</div>
                        <div className="card-text">Phone Number:   {employee.phone}</div>
                        <div className="card-text">Address:   {employee.address}</div>
                        <div className="card-text">Gender:   {employee.gender}</div>
                        <div className="card-text">Employement Type:   {employee.employementType}</div>
                        <div className="card-text">Job Title:   {employee.jobTitle?.titleName}</div>
                        <div className="card-text">Joining Date:   {employee.joiningDate}</div>
                        <div className="card-text">Payroll Policy:   {employee.payrollPolicy?.name}</div>
                        <div className="card-text">Basic Salary:   {employee.jobTitle?.basicSalary}</div>
                        <div className="card-text">Status:   {employee.status}</div>
                    </div>
                    <div className="card-body">
                        <div className="card-header">Bank Details</div>
                        <div className="card-text">Bank Name:   {employee.bankDetails.bankName}</div>
                        <div className="card-text">Branch Name:   {employee.bankDetails.branchName}</div>
                        <div className="card-text">Account Number:   {employee.bankDetails.accountNumber}</div>
                        <div className="card-text">Account Type:   {employee.bankDetails.accountType}</div>
                        <div className="card-text">Ifsc Code:   {employee.bankDetails.ifscCode}</div>
                    </div>
                </div>
            }
        </div>
    )
}

export default ViewProfile;
