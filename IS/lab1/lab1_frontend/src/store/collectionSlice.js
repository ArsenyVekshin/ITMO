import { createSlice } from '@reduxjs/toolkit';

const collectionSlice = createSlice({
    name: 'collection',
    initialState: {
        routes: []
    },
    reducers: {
        addRoute(state, action){
            state.routes.unshift(action.payload);
        },
        setRoutes(state, action) {
            state.routes = action.payload;
        },
        clearRoutes(state, action) {
            state.routes = [];
        },

    }
});

export const {addRoute, clearRoutes, setRoutes} = collectionSlice.actions;

export default collectionSlice.reducer;