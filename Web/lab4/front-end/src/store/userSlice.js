import { createSlice } from '@reduxjs/toolkit';
import {getHitsRequest} from "../service/Service";

const storageToken = localStorage.getItem("token");

const userSlice = createSlice({
    name: 'user',
    initialState: {
        auth: Boolean(storageToken),
        token: storageToken || '',
        hits: [],
        r: 1.0
    },
    reducers: {
        addHit(state, action) {
            state.hits.unshift(action.payload);
        },
        setHits(state, action) {
            state.hits = action.payload;
        },
        clearHits(state, action) {
            state.hits = [];
        },
        changeR(state, action) {
            state.r = parseFloat(action.payload);
        },
        authorize(state, action) {
            localStorage.setItem("token", action.payload);
            state.token = action.payload;
            state.auth = true;
            console.log("User authorized");
        },
        logOut(state, action) {
            localStorage.setItem("token", '');
            state.auth = false;
            state.token = '';
            state.hits = [];
        }
    },
});

export const {addHit, setHits, clearHits, changeR, authorize, logOut} = userSlice.actions;

export default userSlice.reducer;