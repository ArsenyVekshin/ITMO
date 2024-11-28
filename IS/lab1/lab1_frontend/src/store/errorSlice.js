import {createSlice} from "@reduxjs/toolkit";

const errorSlice = createSlice({
    name: 'error',
    initialState: {
        type: '',
        summary: "",
        detail: "",
    },
    reducers: {
        addError(state, action) {
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

export const {addError, clearError} = errorSlice.actions;

export default errorSlice.reducer;