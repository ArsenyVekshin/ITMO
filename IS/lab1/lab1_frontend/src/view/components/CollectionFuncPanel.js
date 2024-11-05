import React, { useState } from 'react';
import {
    Container,
    TextField,
    Button,
    Typography,
    Box,
    Table,
    TableHead,
    TableRow,
    TableCell,
    TableBody,
    TableContainer,
    DialogTitle,
    DialogContent,
    DialogActions, Dialog, IconButton,
} from '@mui/material';
import SendIcon from '@mui/icons-material/Send';
import {
    getAllRoutesByRequest,
    getGreatestRateRequest,
    getMaxToRequest,
    getTotalRatingRequest
} from "../../service/Service";
import RoutesTable from "./RoutesTable";
import LinkIcon from "@mui/icons-material/Link";
import {useSelector} from "react-redux";
import SearchIcon from '@mui/icons-material/Search';

const CollectionFuncPanel = () => {
    const collection = useSelector(state => state.collection);
    const chosenObj = useSelector((state) => state.chosenObj);
    let collectionAdapter = {routes : ''}

    const [totalRatingResult, setTotalRatingResult] = useState('');  // состояние для хранения результата
    const [rateInput, setRateInput] = useState(null);
    const [locations, setLocations] = useState([null, null]);
    const [referredPart, setReferredPart] = useState(null);

    const [errors, setErrors] = useState({});
    const [openModal, setOpenModal] = useState(false);
    const [modalData, setModalData] = useState(null); // Данные для таблицы в диалоге

    const handleRateInputChange = (event) => {
        setRateInput(event.target.value);
        let buff = parseInt(event.target.value);
        if (buff <= 0 || isNaN(buff)) errors.rating = 'Rating must be greater than 0.';
        else {
            delete errors.rating;
            setRateInput(buff);
        }
    };

    const handleLocationChange = (index) => (event) => {
        const newLocations = [...locations];
        let buff = parseInt(event.target.value);
        if (buff <= 0 || isNaN(buff)) errors["location" + index] = 'ID must be greater than 0.';
        else delete errors["location" + index];

        newLocations[index] = event.target.value;
        setLocations(newLocations);
    };

    const handleTotalRatingButton = async () => {
        try {
            const response = await getTotalRatingRequest();
            setTotalRatingResult(response);
        } catch (error) {
            setTotalRatingResult("");
            console.error('Failed to fetch total rating:', error);
        }
    };

    const handleMaxToButton = async () => {
        try {
            const response = await getMaxToRequest();
            collectionAdapter.routes = response;
            openDialog(collectionAdapter);
        } catch (error) {
            console.error('Failed to fetch Max TO:', error);
        }
    };

    const handleGreaterRateButton = async () => {
        try {
            const response = await getGreatestRateRequest(rateInput);
            collectionAdapter.routes = response;
            openDialog(collectionAdapter);
        } catch (error) {
            console.error('Failed to fetch Greater rate:', error);
        }
    };

    const handleRoutesBetweenButton = async () => {

        try {
            const response = await getAllRoutesByRequest(locations[0], locations[1]);
            collectionAdapter.routes = response;
            openDialog(collectionAdapter);
        } catch (error) {
            console.error('Failed to fetch Routes between locations:', error);
        }
    };

    // Функция для открытия диалога с данными
    const openDialog = (data) => {
        setModalData(data);
        setOpenModal(true);
    };

    const handleCloseDialog = () => {
        setOpenModal(false);
        setModalData(null); // Очистим данные при закрытии
        if (referredPart !== null){
            if (chosenObj.column.includes("to.")) locations[referredPart] = chosenObj.route.to.id;
            else locations[referredPart] = chosenObj.route.from.id;

            delete errors["location" + referredPart];
        }
        setReferredPart(null);
    };

    const handleLocationDialogOpen = async (index) => {
        setReferredPart(index)
        openDialog(collection)
    };

    return (
        <Container>
            <Typography variant="h4" gutterBottom>
                Collection functions
            </Typography>

            <Box display="flex" flexDirection="column" alignItems="center">
                <TableContainer sx={{ maxHeight: '70vh', overflowY: 'auto' }}>
                    <Table stickyHeader aria-label="sticky table">
                        <TableHead>
                            <TableRow>
                                <TableCell>Function</TableCell>
                                <TableCell>arg 1</TableCell>
                                <TableCell>arg 2</TableCell>
                                <TableCell> </TableCell>
                                <TableCell>Result</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            <TableRow>
                                <TableCell>Total rating</TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                                <TableCell>
                                    <Button
                                        onClick={handleTotalRatingButton}
                                        variant="outlined"
                                        endIcon={<SendIcon />}
                                    >
                                        send
                                    </Button>
                                </TableCell>
                                <TableCell> {totalRatingResult} </TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell>Route with max TO</TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                                <TableCell>
                                    <Button
                                        onClick={handleMaxToButton}
                                        variant="outlined"
                                        endIcon={<SendIcon />}
                                    >
                                        send
                                    </Button>
                                </TableCell>
                                <TableCell></TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell>Routes with greater rate</TableCell>
                                <TableCell>
                                    <TextField
                                        name="rating"
                                        label="Rating"
                                        margin="normal"
                                        value={rateInput}
                                        onChange={handleRateInputChange}
                                        error={!!errors.rating}
                                        helperText={errors.rating}
                                    />
                                </TableCell>
                                <TableCell></TableCell>
                                <TableCell>
                                    <Button
                                        onClick={handleGreaterRateButton}
                                        variant="outlined"
                                        endIcon={<SendIcon />}
                                        disabled={!!errors.rating  || rateInput === null}
                                    >
                                        send
                                    </Button>
                                </TableCell>
                                <TableCell></TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell>Routes between locations</TableCell>
                                <TableCell>
                                    <Box display="flex" alignItems="center">
                                        <TextField
                                            name="locationID1"
                                            label="location 1 ID"
                                            margin="normal"
                                            value={locations[0]}
                                            onChange={handleLocationChange(0)}
                                            error={!!errors.location0}
                                            helperText={errors.location0}
                                        />
                                        <IconButton onClick={() => handleLocationDialogOpen(0)}><LinkIcon/></IconButton>
                                    </Box>
                                </TableCell>
                                <TableCell>
                                    <Box display="flex" alignItems="center">
                                        <TextField
                                            name="locationID2"
                                            label="location 2 ID"
                                            margin="normal"
                                            value={locations[1]}
                                            onChange={handleLocationChange(1)}
                                            error={!!errors.location1}
                                            helperText={errors.location1}
                                        />
                                        <IconButton onClick={() => handleLocationDialogOpen(1)}><LinkIcon/></IconButton>
                                    </Box>
                                </TableCell>
                                <TableCell>
                                    <Button
                                        onClick={handleRoutesBetweenButton}
                                        variant="outlined"
                                        endIcon={<SendIcon />}
                                        disabled={!!errors.location0 || !!errors.location1 || locations[0] === null || locations[1] === null}
                                    >
                                        send
                                    </Button>
                                </TableCell>
                                <TableCell></TableCell>
                            </TableRow>
                        </TableBody>
                    </Table>
                </TableContainer>
            </Box>

            {/* Диалог с таблицей */}
            <Dialog open={openModal} onClose={handleCloseDialog} fullWidth maxWidth="lg">
                <DialogTitle>Click on the desired item</DialogTitle>
                <DialogContent>
                    {modalData && <RoutesTable collection={modalData} />}
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDialog} color="primary">
                        Close
                    </Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
};

export default CollectionFuncPanel;
