import axios from "axios";

export const fetchProcessor = () => (dispatch) => {

    axios.get("http://localhost:8081/api/payroll-processor/get-one",
        { headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') } }
    )
    
    .then(function (response) {
        dispatch({
            'payload': response.data,
            'type': 'FETCH_PROCESSOR'
        })
    })
}
