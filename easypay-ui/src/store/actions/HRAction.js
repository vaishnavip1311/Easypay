import axios from "axios";

export const fetchHR = () => (dispatch) => {

    axios.get("http://localhost:8081/api/hrmanager/get-one",
        { headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') } }
    )
    
    .then(function (response) {
        dispatch({
            'payload': response.data,
            'type': 'FETCH_HR'
        })
    })
}
