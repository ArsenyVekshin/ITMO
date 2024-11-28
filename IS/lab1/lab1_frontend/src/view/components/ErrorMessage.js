import "../../resources/ErrorMessage.css";
import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {addError, clearError} from "../../store/errorSlice";
import {Alert} from "@mui/material";
import {AlertTitle} from "@mui/lab";
import store from "../../store/store";

export function showError(code, message) {
    console.error("code = ", code, "mes = ", message);
    store.dispatch(addError({
        type: "error",
        summary: code,
        detail: message
    }));
}

export function showWarning(code, message) {
    console.warn("code = ", code, "mes = ", message);
    store.dispatch(addError({
        type: "warning",
        summary: code,
        detail: message
    }));
}

export function showSuccess(message) {
    store.dispatch(addError({
        type: "success",
        summary: 'OK',
        detail: message
    }));
}

function ErrorMessage() {
    const dispatch = useDispatch();
    const errorInfo = useSelector(state => state.error);

    useEffect(() => {
        if (errorInfo.summary) {
            const timer = setTimeout(() => {
                dispatch(clearError());
            }, 5000);

            return () => clearTimeout(timer);
        }
    }, [errorInfo, dispatch]);

    if (!errorInfo.summary) return null;

    return (
        <div className="error-message">
            <Alert severity={errorInfo.type} onClose={() => dispatch(clearError())}>
                <AlertTitle>{errorInfo.summary}</AlertTitle>
                {errorInfo.detail}
            </Alert>
        </div>
    );
}

export default ErrorMessage;
