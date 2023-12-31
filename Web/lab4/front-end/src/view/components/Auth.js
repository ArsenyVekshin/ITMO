import React, {useState, useCallback} from "react";
import "primeicons/primeicons.css";
import "primereact/resources/themes/lara-light-indigo/theme.css";
import '../../resources/Auth.css';

import {Link, Navigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {authorize} from "../../store/userSlice";
import {showError} from "../../store/errorSlice";

import {InputText} from "primereact/inputtext";
import {Button} from "primereact/button";
import {authorizeRequest} from "../../service/Service";

const MIN_USERNAME_LENGTH = 4;
const MIN_PASSWORD_LENGTH = 6;

const Auth = (props) => {
    const dispatch = useDispatch();

    const [user, setUser] = useState({
        username: '',
        password: '',
        login: props.authType !== "register",
        isAuthorized: useSelector(state => state.user.auth)
    });
    const [message, setMessage] = useState({ username: "", password: "" })
    const [isLoading, setLoading] = useState(false);

    const changeAuthType = useCallback(() => {
        setUser({ ...user, login: !user.login })
    }, [user, setUser]);

    const validateUsername = useCallback(() => {
        const usernameRegex = new RegExp('^[a-zA-Z0-9._-]*$');

        if (!usernameRegex.test(user.username)) {
            setMessage({ ...message, username: "Username incorrect format!"});
            return false;
        }
        if (user.username.length < MIN_USERNAME_LENGTH) {
            setMessage({ ...message, username: "Username is too short!"});
            return false;
        }

        setMessage({ ...message, username: ""})
        return true;
    }, [message, user.username])

    const validatePassword = useCallback(() => {
        if (user.password.length < MIN_PASSWORD_LENGTH) {
            setMessage({ ...message, password: "Password is too short!"});
            return false;
        }

        setMessage({ ...message, password: ""})
        return true;
    }, [message, user.password])

    const handleUsernameChange = useCallback( (event) => {
        if (message.username) validateUsername()
        setUser({ ...user, username: event.target.value });
    }, [message.username, user, validateUsername]);

    const handlePasswordChange = useCallback( (event) => {
        if (message.password) validatePassword()
        setUser({ ...user, password: event.target.value })
    }, [message.password, user, validatePassword]);

    const showErrorMessage = (summary, detail) => {
        setLoading(false);
        dispatch(showError({summary: summary, detail: detail}))
        return false;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        if (!validateUsername())
            return showErrorMessage('Incorrect input!');
        if (!validatePassword())
            return showErrorMessage('Incorrect input!');

        console.log("Make request")
        const response = await authorizeRequest(user);

        console.log("Get request data: " + response);

        if (response.message)
            return showErrorMessage(response.message);

        // Set token and auth state
        dispatch(authorize(response.token));

        setUser({ ...user, isAuthorized: true });
        setLoading(false);
        return true;
    };

    const isButtonDisabled = () => {
        return Boolean(message.username) || Boolean(message.password) ||
            !Boolean(user.username) || !Boolean(user.password)
    }

    if (user.isAuthorized)
        return <Navigate to="/main" />;

    return (
        <div className="auth-block">
            <h2 className="form-title">
                {user.login ? "Login" : "Sign Up"}
            </h2>

            <form className="auth-from">
                <div className="form-input-block">
                    <div className="form-input">
                        <label>Username:</label>
                        <InputText
                            value={user.username} placeholder={"Username"} type="text"
                            onChange={handleUsernameChange} onBlur={validateUsername}
                        />
                    </div>
                    {message.username && (
                        <div className="input-message">
                            <span>{message.username}</span>
                        </div>
                    )}
                </div>
                <div className="form-input-block">
                    <div className="form-input">
                        <label>Password:</label>
                        <InputText
                            value={user.password} placeholder={"Password"} type="password"
                            onChange={handlePasswordChange} onBlur={validatePassword}
                        />
                    </div>
                    {message.password && (
                        <div className="input-message">
                            <span>{message.password}</span>
                        </div>
                    )}
                </div>
                <div className="form-input auth-buttons">
                    <Button
                        label={user.login ? "Login" : "Sign Up"} iconPos="right"
                        loading={isLoading} onClick={handleSubmit}
                        disabled={isButtonDisabled()}
                    />
                    <Link to={user.login ? "/register" : "/login"} onClick={changeAuthType}>
                        {user.login ? "Sign Up" : "Login"}
                    </Link>
                </div>
            </form>
        </div>
    );
}

export default Auth;