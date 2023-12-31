import { configureStore } from '@reduxjs/toolkit'
import userReducer from './userSlice'
import errorReducer from './errorSlice'

const store = configureStore({
    reducer: {
        user: userReducer,
        error: errorReducer
    },
})

export default store;