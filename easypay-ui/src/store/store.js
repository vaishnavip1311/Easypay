import { configureStore } from "@reduxjs/toolkit";
import EmployeeReducer from "./reducers/EmployeeReducer";
import HRReducer from "./reducers/HRReducer";
import ProcessorReducer from "./reducers/ProcessorReducer";

const store = configureStore({
    reducer:{
        employee:EmployeeReducer,
        hr:HRReducer,
        processor:ProcessorReducer
    }
})

export default store;