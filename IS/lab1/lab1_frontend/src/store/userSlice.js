import { createSlice } from '@reduxjs/toolkit';

const storageToken = localStorage.getItem("token");

const userSlice = createSlice({
    name: 'user',
    initialState: {
        username: '',
        auth: Boolean(storageToken),
        token: storageToken || '',
        adminRole: false,
    },
    reducers: {
        logIn(state, action) {
            localStorage.setItem("token", action.payload);
            state.token = action.payload;
            state.auth = true;
            state.adminRole = action.payload;
            console.log("User authorized");
        },
        logOut(state, action) {
            localStorage.setItem("token", '');
            state.auth = false;
            state.token = '';
            state.adminRole = false;
        }
    },
});

export const {logIn, logOut} = userSlice.actions;

export default userSlice.reducer;