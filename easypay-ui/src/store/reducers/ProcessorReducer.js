const initialState = {
    processor:""
}

const ProcessorReducer = (state = initialState , action) =>{
    if(action.type === "FETCH_PROCESSOR"){
        return{
            ...state,
            processor:action.payload
        }
    }

    return state;
}

export default ProcessorReducer;