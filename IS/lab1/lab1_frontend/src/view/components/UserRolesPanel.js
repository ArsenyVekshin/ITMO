import React, { useEffect, useState } from 'react';
import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Switch,
    Paper,
    Button,
    Typography
} from '@mui/material';

const UserRolesPanel = () => {
    const [users, setUsers] = useState(['user1', 'user2']);

    // Запрос списка пользователей
    const fetchUsers = async () => {
        console.error('Ошибка при получении пользователей');
    };

    // Функция для подтверждения пользователя
    const handleApprove = async (username) => {
        console.error('Ошибка при подтверждении пользователя');
    };

    useEffect(() => {
        fetchUsers();
    }, []);

    return (
        <div>
            <Typography variant="h4" component="h1" gutterBottom textAlign="center">
                Admin panel
            </Typography>
            <Typography variant="h6" component="h1" gutterBottom textAlign="left">
                Admin role requests:
            </Typography>
            <TableContainer component={Paper} sx={{backgroundColor: '#2F2F2F99',}}>

                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Username</TableCell>
                            <TableCell></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {users.map((user) => (
                            <TableRow key={user}>
                                <TableCell>{user}</TableCell>
                                <TableCell>
                                    <Button
                                        checked={user.approved}
                                        onChange={() => handleApprove(user)}
                                        variant="outlined"
                                        color="success"
                                    >Approve</Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>

    );
};

export default UserRolesPanel;