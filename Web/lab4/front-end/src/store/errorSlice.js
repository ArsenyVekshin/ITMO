import {createSlice} from "@reduxjs/toolkit";

const errorSlice = createSlice({
    name: 'error',
    initialState: {
        summary: "",
        detail: ""
    },
    reducers: {
        showError(state, action) {
            state.summary = action.payload.summary || "";
            state.detail = action.payload.detail || "";
        },
        clearError(state, action) {
            state.summary = "";
            state.detail = "";
        },
    },
});

export const {showError, clearError} = errorSlice.actions;

export default errorSlice.reducer;