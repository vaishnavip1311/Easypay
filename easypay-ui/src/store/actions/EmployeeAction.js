import axios from "axios";

export const fetchEmployee = () => (dispatch) => {

    axios.get("http://localhost:8081/api/employee/get-one",
        { headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') } }
    )
    
    .then(function (response) {
        dispatch({
            'payload': response.data,
            'type': 'FETCH_EMPLOYEE'
        })
    })
}
