import {createSlice} from '@reduxjs/toolkit';

const chosenObjSlice = createSlice({
    name: 'chosen',
    initialState: {
        route: {},
        column: '',
    },
    reducers: {
        clear(state) {
            state.routes = {};
        },
        setRoute(state, action) {
            state.route = action.payload;
        },
        setColumn(state, action) {
            state.column = action.payload;
        },
    }
});

export const {clear, setRoute, setColumn} = chosenObjSlice.actions;

export default chosenObjSlice.reducer;