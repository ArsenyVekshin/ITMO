import React, {useState} from "react";
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
import {deleteAllRoutesRequest} from "../../service/Service";
import {clearRoutes} from "../../store/collectionSlice";
import {wait} from "@testing-library/user-event/dist/utils";
import {setColumn, setRoute} from "../../store/chosenObjSlice";
import AnchorIcon from "@mui/icons-material/Anchor";
import LockIcon from "@mui/icons-material/Lock";

export const sampleRoutes = [
    { id: 1, name: "Route A", coordinates: { id: 1, x: 10, y: 20 }, creationDate: "2023-01-01", from: { id: 1,  name: "Point A", x: 1, y: 2, z: 3 }, to: { id: 11,  name: "Point B", x: 4, y: 5, z: 6 }, distance: 10, rating: 5 },
    { id: 2, name: "Route B", coordinates: { id: 2, x: 15, y: 25 }, creationDate: "2023-01-02", from: { id: 2,  name: "Point C", x: 2, y: 3, z: 4 }, to: { id: 12,  name: "Point D", x: 5, y: 6, z: 7 }, distance: 15, rating: 4 },
    { id: 3, name: "Route C", coordinates: { id: 3, x: 20, y: 30 }, creationDate: "2023-01-03", from: { id: 3,  name: "Point E", x: 3, y: 4, z: 5 }, to: { id: 13,  name: "Point F", x: 6, y: 7, z: 8 }, distance: 20, rating: 3 },
    { id: 4, name: "Route D", coordinates: { id: 4, x: 25, y: 35 }, creationDate: "2023-01-04", from: { id: 4,  name: "Point G", x: 4, y: 5, z: 6 }, to: { id: 14,  name: "Point H", x: 7, y: 8, z: 9 }, distance: 25, rating: 2 },
    { id: 5, name: "Route E", coordinates: { id: 5, x: 30, y: 40 }, creationDate: "2023-01-05", from: { id: 5,  name: "Point I", x: 5, y: 6, z: 7 }, to: { id: 15,  name: "Point J", x: 8, y: 9, z: 10 }, distance: 30, rating: 1 },
    { id: 6, name: "Route F", coordinates: { id: 6, x: 35, y: 45 }, creationDate: "2023-01-06", from: { id: 6,  name: "Point K", x: 6, y: 7, z: 8 }, to: { id: 16,  name: "Point L", x: 9, y: 10, z: 11 }, distance: 35, rating: 5 },
    { id: 7, name: "Route G", coordinates: { id: 7, x: 40, y: 50 }, creationDate: "2023-01-07", from: { id: 7,  name: "Point M", x: 7, y: 8, z: 9 }, to: { id: 17,  name: "Point N", x: 10, y: 11, z: 12 }, distance: 40, rating: 4 },
    { id: 8, name: "Route H", coordinates: { id: 8, x: 45, y: 55 }, creationDate: "2023-01-08", from: { id: 8,  name: "Point O", x: 8, y: 9, z: 10 }, to: { id: 18,  name: "Point P", x: 11, y: 12, z: 13 }, distance: 45, rating: 3 },
    { id: 9, name: "Route I", coordinates: { id: 9, x: 50, y: 60 }, creationDate: "2023-01-09", from: { id: 9,  name: "Point Q", x: 9, y: 10, z: 11 }, to: { id: 19,  name: "Point R", x: 12, y: 13, z: 14 }, distance: 50, rating: 2 },
    { id: 10, name: "Route J", coordinates: { id: 10, x: 55, y: 65 }, creationDate: "2023-01-10", from: { id: 10, name: "Point R", x: 10, y: 11, z: 12 }, to: { id: 20, name: "Point S", x: 13, y: 14, z: 15 }, distance: 55, rating: 4 },
    { id: 11, name: "Route K", coordinates: { id: 11, x: 60, y: 70 }, creationDate: "2023-01-11", from: { id: 11, name: "Point T", x: 11, y: 12, z: 13 }, to: { id: 21, name: "Point U", x: 14, y: 15, z: 16 }, distance: 60, rating: 3 },
    { id: 12, name: "Route L", coordinates: { id: 12, x: 65, y: 75 }, creationDate: "2023-01-12", from: { id: 12, name: "Point V", x: 12, y: 13, z: 14 }, to: { id: 22, name: "Point W", x: 15, y: 16, z: 17 }, distance: 65, rating: 5 },
    { id: 13, name: "Route M", coordinates: { id: 13, x: 70, y: 80 }, creationDate: "2023-01-13", from: { id: 13, name: "Point X", x: 13, y: 14, z: 15 }, to: { id: 23, name: "Point Y", x: 16, y: 17, z: 18 }, distance: 70, rating: 2 },
    { id: 14, name: "Route N", coordinates: { id: 14, x: 75, y: 85 }, creationDate: "2023-01-14", from: { id: 14, name: "Point Z", x: 14, y: 15, z: 16 }, to: { id: 24, name: "Point AA", x: 17, y: 18, z: 19 }, distance: 75, rating: 4 },
    { id: 15, name: "Route O", coordinates: { id: 15, x: 80, y: 90 }, creationDate: "2023-01-15", from: { id: 15, name: "Point AB", x: 15, y: 16, z: 17 }, to: { id: 25, name: "Point AC", x: 18, y: 19, z: 20 }, distance: 80, rating: 3 },
    { id: 16, name: "Route P", coordinates: { id: 16, x: 85, y: 95 }, creationDate: "2023-01-16", from: { id: 16, name: "Point AD", x: 16, y: 17, z: 18 }, to: { id: 26, name: "Point AE", x: 19, y: 20, z: 21 }, distance: 85, rating: 5 },
    { id: 17, name: "Route Q", coordinates: { id: 17, x: 90, y: 100 }, creationDate: "2023-01-17", from: { id: 17, name: "Point AF", x: 17, y: 18, z: 19 }, to: { id: 27, name: "Point AG", x: 20, y: 21, z: 22 }, distance: 90, rating: 2 },
    { id: 18, name: "Route R", coordinates: { id: 18, x: 95, y: 105 }, creationDate: "2023-01-18", from: { id: 18, name: "Point AH", x: 18, y: 19, z: 20 }, to: { id: 28, name: "Point AI", x: 21, y: 22, z: 23 }, distance: 95, rating: 4 },
    { id: 19, name: "Route S", coordinates: { id: 19, x: 100, y: 110 }, creationDate: "2023-01-19", from: { id: 19, name: "Point AJ", x: 19, y: 20, z: 21 }, to: { id: 29, name: "Point AK", x: 22, y: 23, z: 24 }, distance: 100, rating: 3 },
    { id: 20, name: "Route T", coordinates: { id: 20, x: 105, y: 115 }, creationDate: "2023-01-20", from: { id: 20, name: "Point AL", x: 20, y: 21, z: 22 }, to: { id: 30, name: "Point AM", x: 23, y: 24, z: 25 }, distance: 105, rating: 5 },
    { id: 21, name: "Route U", coordinates: { id: 21, x: 110, y: 120 }, creationDate: "2023-01-21", from: { id: 21, name: "Point AN", x: 21, y: 22, z: 23 }, to: { id: 31, name: "Point AO", x: 24, y: 25, z: 26 }, distance: 110, rating: 2 },
    { id: 22, name: "Route V", coordinates: { id: 22, x: 115, y: 125 }, creationDate: "2023-01-22", from: { id: 22, name: "Point AP", x: 22, y: 23, z: 24 }, to: { id: 32, name: "Point AQ", x: 25, y: 26, z: 27 }, distance: 115, rating: 4 },
    { id: 23, name: "Route W", coordinates: { id: 23, x: 120, y: 130 }, creationDate: "2023-01-23", from: { id: 23, name: "Point AR", x: 23, y: 24, z: 25 }, to: { id: 33, name: "Point AS", x: 26, y: 27, z: 28 }, distance: 120, rating: 3 },
    { id: 24, name: "Route X", coordinates: { id: 24, x: 125, y: 135 }, creationDate: "2023-01-24", from: { id: 24, name: "Point AT", x: 24, y: 25, z: 26 }, to: { id: 34, name: "Point AU", x: 27, y: 28, z: 29 }, distance: 125, rating: 5 },
    { id: 25, name: "Route Y", coordinates: { id: 25, x: 130, y: 140 }, creationDate: "2023-01-25", from: { id: 25, name: "Point AV", x: 25, y: 26, z: 27 }, to: { id: 35, name: "Point AW", x: 28, y: 29, z: 30 }, distance: 130, rating: 2 },
    { id: 26, name: "Route Z", coordinates: { id: 26, x: 135, y: 145 }, creationDate: "2023-01-26", from: { id: 26, name: "Point AX", x: 26, y: 27, z: 28 }, to: { id: 36, name: "Point AY", x: 29, y: 30, z: 31 }, distance: 135, rating: 4 },
    { id: 27, name: "Route AA", coordinates: { id: 27, x: 140, y: 150 }, creationDate: "2023-01-27", from: { id: 27, name: "Point AZ", x: 27, y: 28, z: 29 }, to: { id: 37, name: "Point BA", x: 30, y: 31, z: 32 }, distance: 140, rating: 3 },
    { id: 28, name: "Route AB", coordinates: { id: 28, x: 145, y: 155 }, creationDate: "2023-01-28", from: { id: 28, name: "Point BB", x: 28, y: 29, z: 30 }, to: { id: 38, name: "Point BC", x: 31, y: 32, z: 33 }, distance: 145, rating: 5 },
    { id: 29, name: "Route AC", coordinates: { id: 29, x: 150, y: 160 }, creationDate: "2023-01-29", from: { id: 29, name: "Point BD", x: 29, y: 30, z: 31 }, to: { id: 39, name: "Point BE", x: 32, y: 33, z: 34 }, distance: 150, rating: 2 },
    { id: 30, name: "Route AD", coordinates: { id: 30, x: 155, y: 165 }, creationDate: "2023-01-30", from: { id: 30, name: "Point BF", x: 30, y: 31, z: 32 }, to: { id: 40, name: "Point BG", x: 33, y: 34, z: 35 }, distance: 155, rating: 4 },
];

function RoutesTable() {
    const dispatch = useDispatch();
    const collection = useSelector(state => state.collection);
    const user = useSelector(state => state.user);
    const chosenObj = useSelector(state => state.chosenObj);

    const [openRow, setOpenRow] = useState(null);
    const [anchorEl, setAnchorEl] = useState(null);
    const [choosingMode, setChoosingMode] = useState(false)
    const [loading, setLoading] = React.useState(false);

    const handleClear = async () => {
        const response = await clearRoutes();
        if (response.error) {
            dispatch(showError({detail: response.message}));
            return;
        }
        dispatch(deleteAllRoutesRequest());
    };

    const handleSort = (column) => {
        console.log(column);
        // Sorting logic here
    };

    const handleDelete = () => {
        setLoading(true);
        waitCellClick();
    };

    const waitCellClick = () => {
        while (setChoosingMode === true) {
            wait(1);
        }
    };

    const handleCellClick = (route, column) => {
        setChoosingMode(false);
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
                        {(route.readonly || (route.owner !== user.username)) && (<LockIcon fontSize="small"/>)}
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
                    maxHeight: '70vh',
                    overflowY: 'auto',
                    backgroundColor: '#2F2F2F99',
                    borderRadius: '4px',
                }}
            >
                <TableContainer>
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
                            {sampleRoutes.map(route => (
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
