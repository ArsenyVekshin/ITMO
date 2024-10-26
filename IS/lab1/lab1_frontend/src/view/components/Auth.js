import React, { useState } from "react";
import "primeicons/primeicons.css";
import "primereact/resources/themes/lara-light-indigo/theme.css";
import '../../resources/Auth.css';
import {
    Container,
    TextField,
    Button,
    Typography,
    Box,
    Paper,
    CircularProgress,
    Switch,
    FormControlLabel, Alert,
} from '@mui/material';
import LockIcon from '@mui/icons-material/Lock';
import { signInRequest, signUpRequest } from "../../service/Service";
import {AlertTitle} from "@mui/lab";
import {useDispatch, useSelector} from "react-redux";
import {logIn} from "../../store/userSlice";
import {useNavigate} from "react-router-dom";
import {showError} from "../../store/errorSlice";

const Auth = (props) => {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [isSignIn, setIsSignIn] = useState(props.authType !== "register");
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [adminRoleRequest, setAdminRoleRequest] = useState(false);
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (username === '' || password === '') {
            return;
        }

        setLoading(true); // Показать индикатор загрузки
        const user = {
            username: username,
            password: password,
            role: adminRoleRequest ? 'ADMIN' : 'USER',
        };

        try {
            let response;
            if (isSignIn) {
                response = await signInRequest(user);
            } else {
                response = await signUpRequest(user);
            }

            if (response.token) {
                response.username = username;
                dispatch(logIn(response));
                setTimeout(() => navigate("/main"), 100);
            }
        } catch (err) {
            console.error(err);
        } finally {
            setLoading(false); // Скрыть индикатор загрузки
        }
    };

    return (
        <div>
            <Container component="main" maxWidth="xs">
                <Box display="flex" flexDirection="column" alignItems="center">
                    <LockIcon fontSize="large" color="primary" />
                    <Typography variant="h5" component="h1" gutterBottom textAlign="center">
                        {isSignIn ? "Sign In" : "Sign Up"}
                    </Typography>
                </Box>
                <form onSubmit={handleSubmit}>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        label="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        label="password"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    {!isSignIn && (
                        <FormControlLabel
                            control={
                                <Switch
                                    checked={adminRoleRequest}
                                    onChange={(e) => setAdminRoleRequest(e.target.checked)}
                                    color="primary"
                                    margin="normal"
                                />
                            }
                            label="Запросить роль ADMIN"
                        />
                    )}
                    <Box textAlign="center" sx={{ m: 1, position: 'relative' }}>
                        <Button
                            type="submit"
                            variant="contained"
                            color="primary"
                            fullWidth
                            disabled={loading}
                        >
                            {isSignIn ? "Sign In" : "Sign Up"}
                        </Button>
                        {loading && (
                            <CircularProgress
                                size={24}
                                sx={{
                                    position: 'absolute',
                                    top: '50%',
                                    left: '50%',
                                    marginTop: '-12px',
                                    marginLeft: '-12px',
                                }}
                            />
                        )}
                    </Box>
                    <Box mt={2} textAlign="center">
                        <Button onClick={() => setIsSignIn(!isSignIn)}>
                            {isSignIn ? 'Нет аккаунта? Зарегистрироваться' : 'Уже есть аккаунт? Войти'}
                        </Button>
                    </Box>
                </form>
            </Container>
        </div>
    );
}

export default Auth;