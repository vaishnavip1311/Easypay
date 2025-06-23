import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchEmployee } from "../../store/actions/EmployeeAction";
import axios from "axios";

function ViewSalary() {
    const dispatch = useDispatch();
    const employee = useSelector(state => state.employee.employee);

    const [payrolls, setPayrolls] = useState([]);

    useEffect(() => {
        dispatch(fetchEmployee());
    }, [dispatch]);

    const empId = employee?.id;

    useEffect(() => {
        if (!empId) return;

        const getPayrolls = async () => {
            setLoading(true);
            setError(null);
            try {
                const response = await axios.get('http://localhost:8081/api/payroll/get-all/' + empId,
                    { headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') } }
                );
                //console.log(response.data)
                setPayrolls(response.data);
            } catch (error) {
                setError('Failed to fetch payroll data');
                console.error(error);
            } 
        };

        getPayrolls();
    }, [empId]);

    return (
        <div className="container">
            <h2>Salary Details</h2>
            <div className="card card-body">
                <div className="card-text">Employee Id:  {employee.id}</div>
                <div className="card-text">Name:  {employee.name}</div>
            </div>
            
                
                    {payrolls.map((payroll) => (
                        <div className="card" key={payroll.id}>

                            <div className="card-body">
                                <div className="card-text">Pay Period : {payroll.payPeriod}</div>
                                <div className="card-text">Net Salary: {payroll.netSalary}</div>
                            </div>

                            <div className="card-body">
                                <div className="card-header">Earnings Breakdown</div>
                                <div className="card-text">Basic Salary: {payroll.basicSalary}</div>
                                <div className="card-text">HRA: {payroll.hra}</div>
                                <div className="card-text">DA: {payroll.da}</div>
                                <div className="card-text">Other Allowances: {payroll.otherAllowances}</div>
                                <div className="card-text">Gross Earnings: {payroll.totalEarnings}</div>
                            </div>

                            <div className="card-body">
                                <div className="card-header">Deductions</div>
                                <div className="card-text">Tax : {payroll.taxDeduction}</div>
                                <div className="card-text">PF: {payroll.pfContribution}</div>
                                <div className="card-text">Leave Deduction: {payroll.unpaidLeaveDeduction}</div>
                                <div className="card-text">Total Deductions: {payroll.totalDeductions}</div>
                            </div>


                        </div>
                    ))}
              
            
        </div>
    );
}

export default ViewSalary;
