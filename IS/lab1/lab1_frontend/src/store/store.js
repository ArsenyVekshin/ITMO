import {configureStore} from '@reduxjs/toolkit'
import userReducer from './userSlice'
import errorReducer from './errorSlice'
import collectionReducer from "./collectionSlice";
import chosenObjSlice from "./chosenObjSlice";

const store = configureStore({
    reducer: {
        collection: collectionReducer,
        user: userReducer,
        error: errorReducer,
        chosenObj: chosenObjSlice,
    },
})

export default store;