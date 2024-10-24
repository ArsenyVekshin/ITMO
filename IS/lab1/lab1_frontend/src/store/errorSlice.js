import {createSlice} from "@reduxjs/toolkit";

const errorSlice = createSlice({
    name: 'error',
    initialState: {
        type: '',
        summary: "",
        detail: "",
    },
    reducers: {
        showError(state, action) {
            state.type = action.payload.type || "";
            state.summary = action.payload.summary || "";
            state.detail = action.payload.detail || "";
        },
        clearError(state, action) {
            state.type = "";
            state.summary = "";
            state.detail = "";
        },
    },
});

export const {showError, clearError} = errorSlice.actions;

export default errorSlice.reducer;