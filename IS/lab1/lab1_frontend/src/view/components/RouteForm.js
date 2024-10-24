import React, { useState } from 'react';
import {
    Container,
    TextField,
    Button,
    Typography,
    Box, IconButton, DialogTitle, DialogContent, DialogActions, Dialog, Switch, Alert,
} from '@mui/material';
import LinkIcon from '@mui/icons-material/Link'; // Import the link icon
import CancelIcon from '@mui/icons-material/Cancel';
import {useSelector} from "react-redux";
import RoutesTable from "./RoutesTable";
import {wait} from "@testing-library/user-event/dist/utils";

const RouteForm = () => {
    const user = useSelector((state) => state.user);
    const chosenObj = useSelector((state) => state.chosenObj);

    const [referredPart, setReferredPart] = useState("all");
    const [loading, setLoading] = useState(false);

    const [openModal, setOpenModal] = useState(false);
    const [errors, setErrors] = useState({});

    const [route, setRoute] = useState({
        id: '',
        name: '',
        coordinates: {id: '', x: '', y: '' },
        creationDate: '',
        from: { id: '', x: '', y: '', z: '', name: '' },
        to: { id: '', x: '', y: '', z: '', name: '' },
        distance: '',
        rating: '',
        owner: user.username,
        readonly: false,
    });


    const chgReadonlyFlag = () => {
        setRoute((prev) => ({
            ...prev,
            readonly: !route.readonly,
        }));
    }

    const validate = (name, value, subclass) => {
        const newErrors = { ...errors };
        let buff;
        switch (name) {
            case 'name':
                if (!value.trim()) newErrors.name = 'Name cannot be empty.';
                else delete newErrors.name;
                break;
            case 'x':
                if (subclass === "coordinates") buff = parseFloat(value);
                else buff = parseInt(value);

                if (isNaN(buff)) newErrors[subclass + name.toUpperCase()] = 'Coordinates must be numbers.';
                else {
                    route[subclass][name] = buff;
                    delete newErrors[subclass + name.toUpperCase()];
                }
                break;
            case 'y':
                if (subclass === "coordinates") buff = parseInt(value);
                else buff = parseFloat(value);

                if (isNaN(buff)) newErrors[subclass + name.toUpperCase()] = 'Coordinates must be numbers.';
                else if (subclass === "coordinates" && buff <= -240) newErrors.coordinatesY = 'Y coordinate must be greater than -240.';
                else {
                    route[subclass][name] = buff;
                    delete newErrors[subclass + name.toUpperCase()];
                }
                break;
            case 'z':
                buff = parseInt(value);

                if (isNaN(buff)) newErrors[subclass + name.toUpperCase()] = 'Coordinates must be numbers.';
                else {
                    route[subclass][name] = buff;
                    delete newErrors[subclass + name.toUpperCase()];
                }
                break;
            case 'distance':
                const distance = parseFloat(value);
                if (distance <= 1 || isNaN(distance)) newErrors.distance = 'Distance must be greater than 1.';
                else delete newErrors.distance;
                break;
            case 'rating':
                const rating = parseInt(value, 10);
                if (rating <= 0 || isNaN(rating)) newErrors.rating = 'Rating must be greater than 0.';
                else delete newErrors.rating;
                break;
            case 'fromName':
                if (!value.trim()) newErrors.fromName = 'From location name cannot be empty.';
                else delete newErrors.fromName;
                break;
            default:
                break;
        }

        if (Object.keys(newErrors).length > Object.keys(errors).length) buff = '';
        setErrors(newErrors);
        return buff
    };

    const handleChange = (e) => {
        let { name, value } = e.target;
        value = validate(name, value);
        setRoute((prev) => ({
            ...prev,
            [name]: value,
        }));

    };

    const handleCoordinatesChange = (e) => {
        let { name, value } = e.target;
        value = validate(name, value, "coordinates");
        setRoute((prev) => ({
            ...prev,
            coordinates: {
                ...prev.coordinates,
                [name]: value,
            },
        }));

    };

    const handleLocationChange = (e, loc) => {
        let { name, value } = e.target;
        value = validate(name, value, loc);
        setRoute((prev) => ({
            ...prev,
            [loc]: {
                ...prev[loc],
                [name]: value,
            },
        }));

    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const validationErrors = Object.keys(errors).length > 0 ? errors : validateAll();
        if (Object.keys(validationErrors).length === 0) {
            const jsonString = JSON.stringify(route, null, 2);
            alert(jsonString);
        }
    };

    const validateAll = () => {
        const allErrors = {};
        validate('id', route.id);
        validate('name', route.name);
        validate('x', route.coordinates.x);
        validate('y', route.coordinates.y);
        validate('distance', route.distance);
        validate('rating', route.rating);
        validate('fromName', route.from.name);
        return allErrors;
    };


    const handleDeleteLink = (part) =>{

        switch (part) {
            case 'all':
                setRoute((prev) => ({
                    ...prev,
                    id : '',
                }));
                break;

            case 'coordinates':
            case 'from':
            case 'to':
                setRoute((prev) => ({
                    ...prev,
                    [part]: {
                        ...prev[part],
                        id : '',
                    },
                }));
                break;
            default:
                break;
        }
    }

    const handleRefer = (part) =>{
        setReferredPart(part);
        setOpenModal(true);
    }

    const handleReferChanges = () =>{
        setOpenModal(false);

        switch (referredPart) {
            case 'all':
                console.log("RELOAD2: ", route , " -> ", chosenObj.route);
                setRoute(chosenObj.route);
                break;
            case 'coordinates':
                setRoute((prev) => ({
                    ...prev,
                    coordinates: chosenObj.route.coordinates,
                }));
                break;
            case 'from':
            case 'to':
                if (chosenObj.column === "to.name" || chosenObj.column === "from.name") {
                    console.log("AAA:", chosenObj.column.split(".")[0], chosenObj.route[chosenObj.column.split(".")[0]]);
                    setRoute((prev) => ({
                        ...prev,
                        [referredPart]: chosenObj.route[chosenObj.column.split(".")[0]],
                    }));
                } else {
                    setRoute((prev) => ({
                        ...prev,
                        [referredPart]: chosenObj.route[referredPart],
                    }));
                }
                break;
            default:
                break;

        }

    }

    const refButtons = (part) =>{
        const isLinked = route[part].id; // Use optional chaining
        return (
            <Box display="inline" ml={1}>
                <IconButton onClick={() => handleRefer(part)}>
                    {isLinked ? <LinkIcon style={{color: '#19d21c'}}/> : <LinkIcon style={{color: '#d21919'}}/> }
                </IconButton>
                {isLinked &&( <IconButton onClick={() => handleDeleteLink(part)}> <CancelIcon/></IconButton>)}
            </Box>
    );
    }

    return (


        <Container>
            <Typography variant="h4" gutterBottom>
                Route
                <Box display="inline" ml={1}>
                    <IconButton onClick={() => handleRefer('all')}>
                        {route.id? <LinkIcon style={{color: '#19d21c'}}/> : <LinkIcon style={{color: '#d21919'}}/> }
                    </IconButton>
                    {route.id &&( <IconButton onClick={() => handleDeleteLink('all')}> <CancelIcon/></IconButton>)}
                </Box>
            </Typography>
                <TextField
                    name="id"
                    label="ID"
                    value={route.id}
                    disabled
                    fullWidth
                    margin="normal"
                />
                <TextField
                    name="name"
                    label="Name"
                    required
                    fullWidth
                    onChange={handleChange}
                    margin="normal"
                    value={route.name}  // Ensure value is set from route
                    error={!!errors.name}
                    helperText={errors.name}
                />
                <TextField
                    name="distance"
                    label="Distance"
                    required
                    onChange={handleChange}
                    margin="normal"
                    value={route.distance} // Ensure value is set from route
                    error={!!errors.distance}
                    helperText={errors.distance}
                />
                <TextField
                    name="rating"
                    label="Rating"
                    required
                    onChange={handleChange}
                    margin="normal"
                    value={route.rating} // Ensure value is set from route
                    error={!!errors.rating}
                    helperText={errors.rating}
                /> <br/>
                <Box style={{display:"flex !important" }} ml={1}>
                    <Switch
                        name="readonly"
                        label="readonly"
                        checked={route.readonly}
                        onChange={chgReadonlyFlag}
                        color="primary"
                        margin="normal"
                    />
                    <Typography variant="h7" gutterBottom> READONLY </Typography>
                </Box>
                {route.readonly &&(
                    <Alert variant="outlined" severity="warning">
                        Be careful, you will not be able to change the object after sending it.
                    </Alert>
                )}

                <Typography variant="h6" gutterBottom>
                    Coordinates {refButtons('coordinates')}
                </Typography>
                <TextField
                    name="id"
                    label="ID"
                    value={route.coordinates.id}
                    disabled
                    margin="normal"
                />
                <TextField
                    name="x"
                    label="X"
                    required
                    onChange={handleCoordinatesChange}
                    margin="normal"
                    value={route.coordinates.x} // Ensure value is set from route
                    error={!!errors.coordinatesX}
                    helperText={errors.coordinatesX}
                />
                <TextField
                    name="y"
                    label="Y"
                    required
                    onChange={handleCoordinatesChange}
                    margin="normal"
                    value={route.coordinates.y} // Ensure value is set from route
                    error={!!errors.coordinatesY}
                    helperText={errors.coordinatesY}
                />
                <Typography variant="h6" gutterBottom>
                    From {refButtons('from')}
                </Typography>
                <TextField
                    name="id"
                    label="ID"
                    value={route.from.id}
                    disabled
                    margin="normal"
                />
                <TextField
                    name="name"
                    label="Name"
                    required
                    onChange={(e) => handleLocationChange(e, 'from')}
                    margin="normal"
                    value={route.from.name} // Ensure value is set from route
                    error={!!errors.fromName}
                    helperText={errors.fromName}
                />
                <TextField
                    name="x"
                    label="X"
                    required
                    onChange={(e) => handleLocationChange(e, 'from')}
                    margin="normal"
                    value={route.from.x} // Ensure value is set from route
                    error={!!errors.fromX}
                    helperText={errors.fromX}
                />
                <TextField
                    name="y"
                    label="Y"
                    required
                    onChange={(e) => handleLocationChange(e, 'from')}
                    margin="normal"
                    value={route.from.y} // Ensure value is set from route
                    error={!!errors.fromY}
                    helperText={errors.fromY}
                />
                <TextField
                    name="z"
                    label="Z"
                    required
                    onChange={(e) => handleLocationChange(e, 'from')}
                    margin="normal"
                    value={route.from.z} // Ensure value is set from route
                    error={!!errors.fromZ}
                    helperText={errors.fromZ}
                />

                <Typography variant="h6" gutterBottom>
                    To {refButtons('to')}
                </Typography>
                <TextField
                    name="id"
                    label="ID"
                    value={route.to.id}
                    disabled
                    margin="normal"
                />
                <TextField
                    name="name"
                    label="Name"
                    onChange={(e) => handleLocationChange(e, 'to')}
                    margin="normal"
                    value={route.to.name} // Ensure value is set from route
                    error={!!errors.toName}
                    helperText={errors.toName}
                />
                <TextField
                    name="x"
                    label="X"
                    onChange={(e) => handleLocationChange(e, 'to')}
                    margin="normal"
                    value={route.to.x} // Ensure value is set from route
                    error={!!errors.toX}
                    helperText={errors.toX}
                />
                <TextField
                    name="y"
                    label="Y"
                    onChange={(e) => handleLocationChange(e, 'to')}
                    margin="normal"
                    value={route.to.y} // Ensure value is set from route
                    error={!!errors.toY}
                    helperText={errors.toY}
                />
                <TextField
                    name="z"
                    label="Z"
                    onChange={(e) => handleLocationChange(e, 'to')}
                    margin="normal"
                    value={route.to.z} // Ensure value is set from route
                    error={!!errors.toZ}
                    helperText={errors.toZ}
                />

                <Box sx={{ m: 1, position: 'relative' }}>
                    <Button
                        type="submit"
                        variant="contained"
                        color={route.id ? "primary" : "success"}
                        disabled={loading}
                    > {route.id ? "Redact" : "Add"}
                    </Button>
                </Box>

            <Dialog open={openModal} onClose={handleReferChanges} fullWidth maxWidth="lg">
                <DialogTitle>Click on the desired item</DialogTitle>
                <DialogContent>
                    <RoutesTable />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleReferChanges} color="primary">
                        Close
                    </Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
};

export default RouteForm;
