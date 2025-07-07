import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchEmployee } from "../../store/actions/EmployeeAction";
import axios from "axios";
import { Link } from "react-router-dom";
import "./ViewSalary.css"

function ViewSalary() {
    const dispatch = useDispatch();
    const employee = useSelector(state => state.employee.employee);

    let [page, setpage] = useState(0);
    let [size, setSize] = useState(5);

    const [payrolls, setPayrolls] = useState([]);

    useEffect(() => {
        dispatch(fetchEmployee());
    }, [dispatch]);

    const empId = employee?.id;

    useEffect(() => {
        if (!empId) return;

        const getPayrolls = async () => {
            
            try {
                const response = await axios.get('http://localhost:8081/api/payroll/get-all/' + empId + `?page=${page}&size=${size}` ,
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
    }, [empId,page]);

    return (
        <div className="salary-container">
            <h2>Salary Details</h2>
            <div className="salary-card-cont card-body">
                <div className="card-text">Employee Id:  {employee.id}</div>
                <div className="card-text">Name:  {employee.name}</div>
            </div>

            <table className="table table-bordered">
                <thead>
                    <tr>
                        <th scope="col">Payroll ID</th>
                        <th scope="col">Pay Period</th>
                        <th scope="col">Basic Salary</th>
                        <th scope="col">Net Salary</th>
                        <th scope="col">STATUS</th>
                        <th scope="col">Processed On</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        payrolls.map((payroll, index) => (
                            <tr key={payroll.id}>
                                <td>{payroll.id}</td>
                                <td>{payroll.payPeriod}</td>
                                <td>{payroll.basicSalary}</td>
                                <td>{payroll.netSalary}</td>
                                <td>{payroll.status}</td>
                                <td>{payroll.processedOn}</td>
                                <td><Link className="salary-view-btn" to={`/employee/salary-details/${payroll.id}`}>View</Link></td>
                                
                            </tr>
                        ))
                    }

                </tbody>
            </table>

            <nav aria-label="Page navigation example">
                <ul className="pagination">
                    <li className="page-item">
                        <a className="page-link" href="#" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                            <button onClick={()=>{
                                setpage(page-1)
                                //console.log(page)
                            }}>Previous</button>
                        </a>
                    </li>
                    <li className="page-item"><a className="page-link" onClick={()=>setpage(0)}>1</a></li>
                    <li className="page-item"><a className="page-link" onClick={()=>setpage(1)}>2</a></li>
                    <li className="page-item"><a className="page-link" onClick={()=>setpage(2)}>3</a></li>
                    <li className="page-item">
                        <a className="page-link" href="#" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                            <button  onClick={()=>{
                                setpage(page+1)
                            }}>Next</button>
                        </a>
                    </li>
                </ul>
            </nav>   
        </div>
    );
}

export default ViewSalary;
