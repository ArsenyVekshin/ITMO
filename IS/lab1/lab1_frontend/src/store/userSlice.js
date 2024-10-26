import { createSlice } from '@reduxjs/toolkit';

const storageUsername = localStorage.getItem("username");
const storageToken = localStorage.getItem("username");
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
            const { username, token, adminRole } = action.payload;
            state.username = username;
            state.token = token;
            state.auth = true;
            state.adminRole = adminRole;
            localStorage.setItem("username", username);
            localStorage.setItem("token", token);
            localStorage.setItem("adminRole", adminRole);
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