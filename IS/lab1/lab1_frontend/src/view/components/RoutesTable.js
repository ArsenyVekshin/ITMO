import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {
    Box,
    Button,
    Collapse,
    IconButton,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TableSortLabel
} from "@mui/material";


import {showError} from "../../store/errorSlice";
import {KeyboardArrowDown, KeyboardArrowUp} from "@mui/icons-material";
import {deleteAllRoutesRequest, getRoutesListRequest} from "../../service/Service";
import {wait} from "@testing-library/user-event/dist/utils";
import {setColumn, setRoute} from "../../store/chosenObjSlice";
import AnchorIcon from "@mui/icons-material/Anchor";
import LockIcon from "@mui/icons-material/Lock";
import {setRoutes} from "../../store/collectionSlice";



function RoutesTable() {
    const dispatch = useDispatch();
    const collection = useSelector(state => state.collection);
    const user = useSelector(state => state.user);
    const chosenObj = useSelector(state => state.chosenObj);

    const [openRow, setOpenRow] = useState(null);
    const [anchorEl, setAnchorEl] = useState(null);
    const [choosingMode, setChoosingMode] = useState(false);
    const [loading, setLoading] = React.useState(false);

    useEffect(() => {
        const fetchRoutes = async () => {
            try {
                const routes = await getRoutesListRequest();
                console.log(routes)
                dispatch(setRoutes(routes));
            } catch (error) {
                console.error('Failed to fetch routes:', error);
            }
        };

        fetchRoutes();
    }, [dispatch]);


    const handleSort = (column) => {
        console.log(column);
        // Sorting logic here
    };

    const handleDelete = () => {
        console.log("dewe");
    };

    const waitCellClick = () => {
        while (setChoosingMode === true) {
            wait(1);
        }
    };

    const handleCellClick = (route, column) => {
        dispatch(setRoute(route));
        dispatch(setColumn(column));
        console.log(chosenObj.route, chosenObj.column)
    };

    const RouteRow = ({route}) => {
        const handleToggle = () => {
            setOpenRow(openRow === route.id ? null : route.id);
        };

        return (
            <>
                <TableRow hover onClick={handleToggle}>
                    <TableCell>
                        <IconButton aria-label="expand row" size="small">
                            {openRow === route.id ? <KeyboardArrowUp/> : <KeyboardArrowDown/>}
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
                    <TableCell>
                        {(route.readonly || (route.owner.username !== user.username)) && (<LockIcon fontSize="small"/>)}
                        {route.id === chosenObj.route.id && (<AnchorIcon fontSize="small"/>)}
                    </TableCell>
                </TableRow>
                <TableRow>
                    <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={15}>
                        <Collapse in={openRow === route.id} timeout="auto" unmountOnExit>
                            <div style={{padding: 16}}>
                                <p><strong>Coordinates ID:</strong> {route.coordinates.id}</p>
                                <p><strong>From:</strong> id: {route.from.id} coord:
                                    ({route.from.x}, {route.from.y}, {route.from.z})</p>
                                <p><strong>To:</strong> id: {route.to.id} coord:
                                    ({route.to.x}, {route.to.y}, {route.to.z})</p>
                            </div>
                        </Collapse>
                    </TableCell>
                </TableRow>
            </>
        );
    };



    return (
        <div>
            <Box
                sx={{
                    backgroundColor: '#2F2F2F99',
                    borderRadius: '4px',
                }}
            >
                <TableContainer sx={{maxHeight: '70vh', overflowY: 'auto'}}>
                    <Table stickyHeader aria-label="sticky table">
                        <TableHead>
                            <TableRow>
                                <TableCell/>
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
                                    <TableSortLabel onClick={() => handleSort('creationDate')}>Creation
                                        Date</TableSortLabel>
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
                                <TableCell> </TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {collection.routes.map(route => (
                                <RouteRow key={route.id} route={route}/>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Box>
            <Box sx={{m: 1, position: 'relative'}}>
                <Button
                    type="submit"
                    variant="contained"
                    color="primary"
                    disabled={loading}
                    onClick={handleDelete}
                > Delete
                </Button>
            </Box>
        </div>

    );
}

export default RoutesTable;
