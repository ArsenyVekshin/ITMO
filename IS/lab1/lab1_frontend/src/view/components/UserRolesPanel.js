import React, {useEffect, useState} from 'react';
import {
    Button,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography
} from '@mui/material';
import {approveUserListRequest, approveUserRequest} from "../../service/Service";

const UserRolesPanel = () => {
    const [users, setUsers] = useState([]);

    // Запрос списка пользователей
    const fetchUsers = async () => {
        let response = await approveUserListRequest();
        setUsers(response.usernameList);
    };

    // Функция для подтверждения пользователя
    const handleApprove = async (username) => {
        try {
            let response = await approveUserRequest(username);

        } catch (err) {
            console.error(err);
        }
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
                                        onClick={() => handleApprove(user)}
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