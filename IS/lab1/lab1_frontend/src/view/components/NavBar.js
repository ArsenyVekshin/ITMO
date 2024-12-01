import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import HomeIcon from '@mui/icons-material/Home';
import MapIcon from '@mui/icons-material/Map'; // Иконка для Map
import AccountCircle from '@mui/icons-material/AccountCircle';
import Settings from '@mui/icons-material/Settings';
import Logout from '@mui/icons-material/Logout';
import AccountSwitch from '@mui/icons-material/SwitchAccount';
import AttachFileIcon from '@mui/icons-material/AttachFile';
import {useDispatch, useSelector} from "react-redux";
import {logOut} from "../../store/userSlice";
import CreateIcon from '@mui/icons-material/Create';
import FunctionsIcon from '@mui/icons-material/Functions';
import HistoryIcon from '@mui/icons-material/History';

const NavBar = () => {
    const dispatch = useDispatch();

    const user = useSelector((state) => state.user);
    const [anchorEl, setAnchorEl] = useState(null);
    const navigate = useNavigate();

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleLogOut = () => {
        dispatch(logOut());
        navigate('/sign-in');
    };

    const handleMenuClick = (path) => {
        handleClose();
        navigate(path);
    }

    return (
        <nav position="static">
            <Toolbar>
                <div style={{display: 'flex', alignItems: 'center', marginRight: 'auto'}}>
                    <Button color="inherit" onClick={() => navigate('/main')}>
                        <HomeIcon sx={{marginRight: 1}}/>
                        Home
                    </Button>
                    <Button color="inherit" onClick={() => navigate('/map')}>
                        <MapIcon sx={{marginRight: 1}}/>
                        Map
                    </Button>
                    <Button color="inherit" onClick={() => navigate('/obj')}>
                        <CreateIcon sx={{marginRight: 1}}/>
                        Object
                    </Button>
                    <Button color="inherit" onClick={() => navigate('/obj/file')}>
                        <AttachFileIcon sx={{marginRight: 1}}/>
                        Add
                    </Button>
                    <Button color="inherit" onClick={() => navigate('/func')}>
                        <FunctionsIcon sx={{marginRight: 1}}/>
                        Func
                    </Button>
                </div>

                {!user.auth && (
                    <div>
                        <Button color="inherit" onClick={() => navigate('/sign-in')}>Sing In</Button>
                        <Button color="inherit" onClick={() => navigate('/sign-up')} style={{marginLeft: '8px'}}>Sign
                            Up</Button>
                    </div>
                )}

                {user.auth && (
                    <div style={{display: 'flex', alignItems: 'center'}}>
                        <Button
                            aria-controls="user-menu"
                            aria-haspopup="true"
                            onClick={handleClick}
                            color="inherit"
                        >
                            <AccountCircle sx={{marginRight: 1}}/> {user.username}
                        </Button>
                        <Menu
                            id="user-menu"
                            anchorEl={anchorEl}
                            open={Boolean(anchorEl)}
                            onClose={handleClose}
                        >
                            <MenuItem onClick={() => handleMenuClick('/log/import')}>
                                <HistoryIcon sx={{marginRight: 1}}/> Log
                            </MenuItem>
                            {user.adminRole && (
                                <MenuItem onClick={() => handleMenuClick('/admin')}>
                                    <Settings sx={{marginRight: 1}}/> Admin panel
                                </MenuItem>
                            )}
                            <MenuItem onClick={() => handleMenuClick('/sign-in')}>
                                <AccountSwitch sx={{marginRight: 1}}/> Change Account
                            </MenuItem>
                            <MenuItem onClick={handleLogOut}>
                                <Logout sx={{marginRight: 1}}/> Logout
                            </MenuItem>

                        </Menu>
                    </div>
                )}
            </Toolbar>
        </nav>
    );
};

export default NavBar;
