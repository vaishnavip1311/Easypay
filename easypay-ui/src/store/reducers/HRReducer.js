const initialState = {
    hr:""
}

const HRReducer = (state = initialState , action) =>{
    if(action.type === "FETCH_HR"){
        return{
            ...state,
            hr:action.payload
        }
    }

    return state;
}

export default HRReducer;