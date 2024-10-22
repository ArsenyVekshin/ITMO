import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {Alert, Button} from "@mui/material";
import { showError } from "../../store/errorSlice";

import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TableSortLabel,
    Paper,
    Collapse,
    IconButton,
    Menu,
    MenuItem,
} from "@mui/material";
import {KeyboardArrowDown, KeyboardArrowUp, MoreVert} from "@mui/icons-material";
import { deleteAllRoutesRequest } from "../../service/Service";
import { clearRoutes } from "../../store/collectionSlice";
import AccountSwitch from "@mui/icons-material/SwitchAccount";
import {wait} from "@testing-library/user-event/dist/utils";
import {setRoute, setColumn} from "../../store/chosenObjSlice";

function RoutesTable() {
    const dispatch = useDispatch();
    const collection = useSelector(state => state.collection);
    const chosenObj = useSelector(state => state.chosenObj);

    const [openRow, setOpenRow] = useState(null);
    const [anchorEl, setAnchorEl] = useState(null);
    const [choosingMode, setChoosingMode] = useState(false)

    const handleClear = async () => {
        const response = await clearRoutes();
        if (response.error) {
            dispatch(showError({ detail: response.message }));
            return;
        }
        dispatch(deleteAllRoutesRequest());
    };

    const handleSort = (column) => {
        console.log(column);
        // Sorting logic here
    };

    const handleAdd = () => {
        // Logic for adding a new route
        console.log("Add new route");
    };

    const handleRedact = () => {
        // Logic for redacting a selected route
        setChoosingMode(true);
        waitCellClick();
        console.log("Redact selected route");

    };
    const handleDelete = () => {
        setChoosingMode(true);
        waitCellClick();
    };

    const waitCellClick = () => {
        while(setChoosingMode === true){
            wait(1);
        }
    };

    const handleCellClick = (route, column) => {
        setChoosingMode(false);
        dispatch(setRoute(route));
        dispatch(setColumn(column));
        console.log( chosenObj.route, chosenObj.column)
    };

    const RouteRow = ({ route }) => {
        const handleToggle = () => {
            setOpenRow(openRow === route.id ? null : route.id);
        };

        return (
            <>
                <TableRow hover onClick={handleToggle}>
                    <TableCell>
                        <IconButton aria-label="expand row" size="small">
                            {openRow === route.id ? <KeyboardArrowUp /> : <KeyboardArrowDown />}
                        </IconButton>
                    </TableCell>
                    <TableCell onClick={() => handleCellClick(route, 'id')}>{route.id}</TableCell>
                    <TableCell onClick={() => handleCellClick(route, 'name')}>{route.name}</TableCell>
                    <TableCell onClick={() => handleCellClick(route, 'coordinates.x')}>{route.coordinates.x}</TableCell>
                    <TableCell onClick={() => handleCellClick(route, 'coordinates.y')}>{route.coordinates.y}</TableCell>
                    <TableCell onClick={() => handleCellClick(route, 'creationDate')}>{route.creationDate}</TableCell>
                    <TableCell onClick={() => handleCellClick(route, 'from.name')}>{route.from.name}</TableCell>
                    <TableCell onClick={() => handleCellClick(route, 'to.name')}>{route.to.name}</TableCell>
                    <TableCell onClick={() => handleCellClick(route, 'distance')}>{route.distance}</TableCell>
                    <TableCell onClick={() => handleCellClick(route, 'rating')}>{route.rating}</TableCell>
                </TableRow>
                <TableRow>
                    <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={14}>
                        <Collapse in={openRow === route.id} timeout="auto" unmountOnExit>
                            <div style={{ padding: 16 }}>
                                <p><strong>Coordinates ID:</strong> {route.coordinates.id}</p>
                                <p><strong>From:</strong> id: {route.from.id} coord: ({route.from.x}, {route.from.y}, {route.from.z})</p>
                                <p><strong>To:</strong> id: {route.to.id} coord: ({route.to.x}, {route.to.y}, {route.to.z})</p>
                            </div>
                        </Collapse>
                    </TableCell>
                </TableRow>
            </>
        );
    };

    const sampleRoutes = [
        { id: 1, name: "Route A", coordinates: { id: 1, x: 10, y: 20 }, creationDate: "2023-01-01", from: { id: 1,  name: "Point A", x: 1, y: 2, z: 3 }, to: { id: 11,  name: "Point B", x: 4, y: 5, z: 6 }, distance: 10, rating: 5 },
        { id: 2, name: "Route B", coordinates: { id: 2, x: 15, y: 25 }, creationDate: "2023-01-02", from: { id: 2,  name: "Point C", x: 2, y: 3, z: 4 }, to: { id: 12,  name: "Point D", x: 5, y: 6, z: 7 }, distance: 15, rating: 4 },
        { id: 3, name: "Route C", coordinates: { id: 3, x: 20, y: 30 }, creationDate: "2023-01-03", from: { id: 3,  name: "Point E", x: 3, y: 4, z: 5 }, to: { id: 13,  name: "Point F", x: 6, y: 7, z: 8 }, distance: 20, rating: 3 },
        { id: 4, name: "Route D", coordinates: { id: 4, x: 25, y: 35 }, creationDate: "2023-01-04", from: { id: 4,  name: "Point G", x: 4, y: 5, z: 6 }, to: { id: 14,  name: "Point H", x: 7, y: 8, z: 9 }, distance: 25, rating: 2 },
        { id: 5, name: "Route E", coordinates: { id: 5, x: 30, y: 40 }, creationDate: "2023-01-05", from: { id: 5,  name: "Point I", x: 5, y: 6, z: 7 }, to: { id: 15,  name: "Point J", x: 8, y: 9, z: 10 }, distance: 30, rating: 1 },
        { id: 6, name: "Route F", coordinates: { id: 6, x: 35, y: 45 }, creationDate: "2023-01-06", from: { id: 6,  name: "Point K", x: 6, y: 7, z: 8 }, to: { id: 16,  name: "Point L", x: 9, y: 10, z: 11 }, distance: 35, rating: 5 },
        { id: 7, name: "Route G", coordinates: { id: 7, x: 40, y: 50 }, creationDate: "2023-01-07", from: { id: 7,  name: "Point M", x: 7, y: 8, z: 9 }, to: { id: 17,  name: "Point N", x: 10, y: 11, z: 12 }, distance: 40, rating: 4 },
        { id: 8, name: "Route H", coordinates: { id: 8, x: 45, y: 55 }, creationDate: "2023-01-08", from: { id: 8,  name: "Point O", x: 8, y: 9, z: 10 }, to: { id: 18,  name: "Point P", x: 11, y: 12, z: 13 }, distance: 45, rating: 3 },
        { id: 9, name: "Route I", coordinates: { id: 9, x: 50, y: 60 }, creationDate: "2023-01-09", from: { id: 9,  name: "Point Q", x: 9, y: 10, z: 11 }, to: { id: 19,  name: "Point R", x: 12, y: 13, z: 14 }, distance: 50, rating: 2 },
    ];

    return (
        <div>
            <TableContainer component={Paper} style={{ backgroundColor: 'rgba(47,47,47,0.6)',  maxHeight: '60%', overflowY: 'auto'}}>
                <Table stickyHeader aria-label="sticky table">
                    <TableHead>
                        <TableRow>
                            <TableCell />
                            <TableCell>
                                <TableSortLabel onClick={() => handleSort('id')}>ID</TableSortLabel>
                            </TableCell>
                            <TableCell>
                                <TableSortLabel onClick={() => handleSort('name')}>Name</TableSortLabel>
                            </TableCell>
                            <TableCell>
                                <TableSortLabel onClick={() => handleSort('coordinates.x')}>X</TableSortLabel>
                            </TableCell>
                            <TableCell>
                                <TableSortLabel onClick={() => handleSort('coordinates.y')}>Y</TableSortLabel>
                            </TableCell>
                            <TableCell>
                                <TableSortLabel onClick={() => handleSort('creationDate')}>Creation Date</TableSortLabel>
                            </TableCell>
                            <TableCell>
                                <TableSortLabel onClick={() => handleSort('from.name')}>From</TableSortLabel>
                            </TableCell>
                            <TableCell>
                                <TableSortLabel onClick={() => handleSort('to.name')}>To</TableSortLabel>
                            </TableCell>
                            <TableCell>
                                <TableSortLabel onClick={() => handleSort('distance')}>Distance</TableSortLabel>
                            </TableCell>
                            <TableCell>
                                <TableSortLabel onClick={() => handleSort('rating')}>Rating</TableSortLabel>
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {sampleRoutes.map(route => (
                            <RouteRow key={route.id} route={route} />
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            {!choosingMode && (
            <div id="button-block" style={{ display: 'flex', gap:'16px', padding: '16px' }}>
                <Button variant="contained" color="primary" onClick={handleDelete}>
                    Choose
                </Button>
                <Button variant="contained" color="success" onClick={handleAdd}>
                    Add
                </Button>
                <Button variant="contained" color="warning" onClick={handleRedact}>
                    Redact
                </Button>
                <Button variant="contained" color="primary" onClick={handleDelete}>
                    Delete
                </Button>
            </div>
            )}
            {choosingMode && (
                <div>
                    <Alert variant="filled" severity="info">
                        Please select a route to proceed with the action.
                    </Alert>
                    <Button variant="contained" color="warning" onClick={() => setChoosingMode(false)}>
                        Cancel
                    </Button>
                </div>

            )}

        </div>

    );
}

export default RoutesTable;
