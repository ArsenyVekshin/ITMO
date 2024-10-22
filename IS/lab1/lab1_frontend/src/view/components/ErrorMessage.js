import {Messages} from "primereact/messages";

import "../../resources/ErrorMessage.css"
import {useDispatch, useSelector} from "react-redux";
import {clearError} from "../../store/errorSlice";

let messages;

function ErrorMessage() {
    const dispatch = useDispatch();

    const errorInfo = useSelector(state => state.error);

    if ((errorInfo.summary || errorInfo.detail) && messages) {
        console.log(errorInfo);
        messages.show(
            { severity: 'error', summary: errorInfo.summary, detail: errorInfo.detail, closable: false }
        );

        dispatch(clearError())
    }

    return (
        <div className="errorBlock">
            <Messages ref={(el) => messages = el} />
        </div>
    );
}

export default ErrorMessage;