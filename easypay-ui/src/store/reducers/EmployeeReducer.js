const initialState = {
    employee:""
}

const EmployeeReducer = (state = initialState , action) =>{
    if(action.type === "FETCH_EMPLOYEE"){
        return{
            ...state,
            employee:action.payload
        }
    }

    return state;
}

export default EmployeeReducer;