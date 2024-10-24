import React, { useEffect, useState } from 'react';
import { ScatterChart, Scatter, XAxis, YAxis, Tooltip, CartesianGrid } from 'recharts';
import { Box, Typography } from '@mui/material';
import {useDispatch, useSelector} from "react-redux";


const CustomTooltip = ({ active, payload }) => {
    if (active && payload && payload.length) {
        const { id, name, rating } = payload[0].payload; // Извлечение id и rating
        return (
            <div style={{ background: 'rgba(44,44,44,0.9)', border: '1px solid #ccc', padding: '10px' }}>
                <p>ID: {id}</p>
                <p>Name: {name}</p>
                <p>Rating: {rating}</p>
            </div>
        );
    }
    return null;
};

const RoutesMap = ({ routes }) => {
    const dispatch = useDispatch();
    const user = useSelector(state => state.user);
    const chosenObj = useSelector(state => state.chosenObj);
    const collection = useSelector(state => state.collection);


    const [chartDimensions, setChartDimensions] = useState({
        width: window.innerWidth * 0.8, // 80% от ширины окна
        height: window.innerHeight * 0.6 // 60% от высоты окна
    });

    useEffect(() => {
        const handleResize = () => {
            setChartDimensions({
                width: window.innerWidth * 0.95,
                height: window.innerHeight * 0.6
            });
        };

        window.addEventListener('resize', handleResize);
        return () => window.removeEventListener('resize', handleResize);
    }, []);


    const chooseColour = (route) => {
        if (route.id === chosenObj.route.id) return '#ff00f2';
        if (route.owner === user.username) return '#960019';
        return '#00FF00';
    };

    const data = routes.map(route => ({
        x: route.coordinates.x,
        y: route.coordinates.y,
        name: route.name,
        id: route.id,
        rating: route.rating,
        color: chooseColour(route),
    }));

    const handlePointClick = (data) => {
        if (!data) return;

        const routeId = data.id;
        console.log('Clicked point ID:', routeId);
        const buff = false;

        //const buff = selectRouteById(collection, routeId); // Используйте селектор для получения маршрута
        if (buff) {
            chosenObj.setRoute(buff); // Предполагается, что это функция для обновления выбранного маршрута
        }


    };

    return (
        <Box sx={{ padding: 2}}>
            <Typography variant="h4" gutterBottom>
                Scatter Chart of Route Coordinates
            </Typography>
            <ScatterChart  width={chartDimensions.width} height={chartDimensions.height} style={{ background: '#2F2F2F99' }}>
                <CartesianGrid />
                <XAxis type="number" dataKey="x" name="X Coordinate" />
                <YAxis type="number" dataKey="y" name="Y Coordinate" />
                <Tooltip content={<CustomTooltip />} cursor={{ strokeDasharray: '3 3' }} />
                <Scatter
                    name="Routes"
                    data={data}
                    onClick={handlePointClick}
                    shape={(props) => (
                        <circle {...props} r={props.rating} fill={props.color} />
                    )}
                />
            </ScatterChart>
        </Box>
    );
};

export default RoutesMap;
