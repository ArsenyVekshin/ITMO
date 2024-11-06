import { createSlice } from '@reduxjs/toolkit';

const storageUsername = localStorage.getItem("username");
const storageToken = localStorage.getItem("token");
const storageAdminRole = localStorage.getItem("adminRole");



const userSlice = createSlice({
    name: 'user',
    initialState: {
        username: storageUsername || '',
        auth: Boolean(storageToken),
        token: storageToken || '',
        adminRole: storageAdminRole || false,
    },
    reducers: {
        logIn(state, action) {
            // const { username, token, adminRole } = action.payload;
            state.username = action.payload.username;
            state.token = action.payload.token;
            state.auth = true;
            state.adminRole = action.payload.adminRole;
            localStorage.setItem("username", action.payload.username);
            localStorage.setItem("token", action.payload.token);
            localStorage.setItem("adminRole", action.payload.adminRole);
        },
        logOut(state, action) {
            localStorage.removeItem("username");
            localStorage.removeItem("token");
            localStorage.removeItem("adminRole");
            state.auth = false;
            state.token = '';
            state.adminRole = false;
        }
    },
});

export const {logIn, logOut} = userSlice.actions;

export default userSlice.reducer;