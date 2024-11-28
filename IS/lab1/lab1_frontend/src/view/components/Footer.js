import React, {useEffect, useState} from 'react';
import {Box, Link, Typography} from '@mui/material';
import GitHubIcon from '@mui/icons-material/GitHub';

const Footer = () => {
    const [currentTime, setCurrentTime] = useState(new Date().toLocaleTimeString());

    useEffect(() => {
        const interval = setInterval(() => {
            setCurrentTime(new Date().toLocaleTimeString());
        }, 1000);

        return () => clearInterval(interval); // Очистка интервала при размонтировании
    }, []);

    return (
        <Box
            component="footer"
            sx={{
                display: 'flex',
                flexDirection: 'row',
                justifyContent: 'space-between',
                alignItems: 'center',
                padding: 2,
                backgroundColor: '#1e1e1e',
                color: '#fff',
                textAlign: 'center',
            }}
        >
            <Typography variant="body2">
                Векшин Арсений P3316

            </Typography>
            <Link href="https://github.com/ArsenyVekshin/ITMO/tree/master/IS/lab1" target="_blank" color="inherit"
                  sx={{display: 'flex', alignItems: 'center', justifyContent: 'center', mt: 1}}>
                <GitHubIcon sx={{mr: 0.5}}/>
            </Link>
            <Typography variant="body2" sx={{mt: 1}}>
                Текущее время: {currentTime}
            </Typography>
        </Box>
    );
};

export default Footer;