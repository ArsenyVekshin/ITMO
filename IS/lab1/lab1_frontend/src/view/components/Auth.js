import React, {useState, useCallback} from "react";
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
    CircularProgress, Switch, FormControlLabel,
} from '@mui/material';
import LockIcon from '@mui/icons-material/Lock'; // Импортируйте иконку

const Auth = (props) => {
    const [isSignIn, setIsSignIn] = useState(props.authType !== "register");
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [adminRoleRequest, setAdminRoleRequest] = useState('');

    const [error, setError] = useState('');

    const [loading, setLoading] = React.useState(false);

    const handleSubmit = (event) => {
        event.preventDefault();
        if (username === '' || password === '') {
            setError('Пожалуйста, заполните все поля.');
            return;
        }

        // Здесь можно добавить логику для отправки данных на сервер
        console.log('Отправка данных:', { username, password });
        setError(''); // Сбросить ошибку
    };

    return (
        <div>
            <Container component="main" maxWidth="xs">
                    <Box display="flex" flexDirection="column" alignItems="center">
                        <LockIcon fontSize="large" color="primary" /> {/* Иконка */}
                        <Typography variant="h5" component="h1" gutterBottom textAlign="center">
                            {isSignIn? "Sign In" : "Sign Up"}
                        </Typography>
                    </Box>
                    {error && <Typography color="error">{error}</Typography>}
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
                        <Box textAlign="center " sx={{ m: 1, position: 'relative' }}>
                            <Button
                                type="submit"
                                variant="contained"
                                color="primary"
                                fullWidth
                                disabled={loading}
                            >
                                {isSignIn? "Sign In" : "Sign Up"}
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