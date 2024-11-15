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
    TableSortLabel, TextField
} from "@mui/material";
import {showError} from "../../store/errorSlice";
import {KeyboardArrowDown, KeyboardArrowUp} from "@mui/icons-material";
import {
    deleteAllRoutesRequest,
    deleteRouteRequest,
    getRoutesListRequest,
    getSortedRoutesListRequest
} from "../../service/Service";
import {setColumn, setRoute} from "../../store/chosenObjSlice";
import AnchorIcon from "@mui/icons-material/Anchor";
import LockIcon from "@mui/icons-material/Lock";
import {setRoutes} from "../../store/collectionSlice";
import store from "../../store/store";
import {showWarning} from "./ErrorMessage";
import LinkIcon from "@mui/icons-material/Link";
import SearchIcon from "@mui/icons-material/Search";


function RoutesTable({ collection })  {
    const dispatch = useDispatch();
    const user = useSelector(state => state.user);
    const chosenObj = useSelector(state => state.chosenObj);

    const [openRow, setOpenRow] = useState(null);
    const [loading, setLoading] = React.useState(false);

    const [sortInput, setSortInput] = useState('');
    const [activeSortColumn, setActiveSortColumn] = useState(null);
    const [sortType, setSortType] = useState('<');

    // Локальное состояние для отсортированных маршрутов
    const [sortedRoutes, setSortedRoutes] = useState([]);

    const fetchRoutes = async () => {
        try {
            const routes = await getRoutesListRequest();
            console.log(routes)
            dispatch(setRoutes(routes));
        } catch (error) {
            console.error('Failed to fetch routes:', error);
        }
    };

    // useEffect(() => {
    //     fetchRoutes();
    // }, [dispatch]);
    useEffect(() => {
        fetchRoutes();
        const interval = setInterval(fetchRoutes, 1000);
        return () => clearInterval(interval);
    }, []);


    const handleDelete = async () => {
        try {
            if(!chosenObj.route.id) {
                showWarning("Unable to delete", "The object has not been selected for deletion");
                return;
            }
            await deleteRouteRequest(chosenObj.route);
            await fetchRoutes();
        } catch (error) {
            console.error('Failed to delete route:', error);
        }
    };

    const handleCellClick = (route, column) => {
        dispatch(setRoute(route));
        dispatch(setColumn(column));
        console.log(chosenObj.route, chosenObj.column)
    };

    const handleSortInputChange = (event) => {
        setSortInput(event.target.value);
    };

    const handleSort = (column) => {
        setActiveSortColumn(column);
        console.log(column, "sort clmn = ", activeSortColumn, "sort value = ", sortInput);
        if(activeSortColumn && sortInput){
            requestSorted();
        }
    };

    const requestSorted = async () => {
        try {
            const response = await getSortedRoutesListRequest('=', activeSortColumn, sortInput);
            console.log(response);
            setSortedRoutes(response); // Обновляем локальное состояние
            dispatch(setRoutes(response)); // Можно обновить данные в Redux, если нужно
        } catch (error) {
            console.error('Failed to request sorted list: ', error);
        }
    };

    const SortPanel = (column) => {
        if (activeSortColumn !== column) return;
        return (
            <Box display="flex" alignItems="center">
                <TextField
                    variant="outlined"
                    size="small"
                    value={sortInput}
                    onChange={handleSortInputChange}
                    placeholder="Sort value"
                    onBlur={() => setActiveSortColumn(null)} // Hide input on blur
                />
                <IconButton onClick={() => handleSort(column)}><SearchIcon/></IconButton>
            </Box>
        );
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
                                <TableCell onClick={() => setActiveSortColumn('id')}>
                                    ID {SortPanel('id')}
                                </TableCell>
                                <TableCell onClick={() => setActiveSortColumn('name')}>
                                    Name {SortPanel('name')}
                                </TableCell>
                                <TableCell onClick={() => setActiveSortColumn('coordinates.x')}>
                                    X {SortPanel('coordinates.x')}
                                </TableCell>
                                <TableCell onClick={() => setActiveSortColumn('coordinates.y')}>
                                    Y {SortPanel('coordinates.y')}
                                </TableCell>
                                <TableCell onClick={() => setActiveSortColumn('creationDate')}>
                                    Creation Date {SortPanel('creationDate')}
                                </TableCell>
                                <TableCell onClick={() => setActiveSortColumn('from.name')}>
                                    From {SortPanel('from.name')}
                                </TableCell>
                                <TableCell onClick={() => setActiveSortColumn('to.name')}>
                                    To {SortPanel('to.name')}
                                </TableCell>
                                <TableCell onClick={() => setActiveSortColumn('distance')}>
                                    Distance {SortPanel('distance')}
                                </TableCell>
                                <TableCell onClick={() => setActiveSortColumn('rating')}>
                                    Rating {SortPanel('rating')}
                                </TableCell>
                                <TableCell> </TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {sortedRoutes.length > 0 ? (
                                sortedRoutes.map(route => (
                                    <RouteRow key={route.id} route={route}/>
                                ))
                            ) : (
                                collection.routes.map(route => (
                                    <RouteRow key={route.id} route={route}/>
                                ))
                            )}
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
