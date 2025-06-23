import { configureStore } from "@reduxjs/toolkit";
import EmployeeReducer from "./reducers/EmployeeReducer";

const store = configureStore({
    reducer:{
        employee:EmployeeReducer
    }
})

export default store;