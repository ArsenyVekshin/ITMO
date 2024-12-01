import {Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import React, {useEffect, useState} from "react";
import CloseIcon from '@mui/icons-material/Close';
import DoneIcon from '@mui/icons-material/Done';
import {getImportLogRequest} from "../../service/Service";

const ImportLogTable = () => {
    const [data, setData] = useState([]);

    const fetchLog = async () => {
        setData(await getImportLogRequest());
        console.log(data);
    };

    useEffect(() => {
        fetchLog();
        const interval = setInterval(fetchLog, 5000);
        return () => clearInterval(interval);
    }, []);

    const LogRow = ({note}) => {
        return (
            <>
                <TableRow>
                    <TableCell>{note.id}</TableCell>
                    <TableCell>{note.creationDate}</TableCell>
                    <TableCell>{note.owner}</TableCell>
                    <TableCell>{note.number}</TableCell>
                    <TableCell>{note.successful ? <DoneIcon fontSize="small"/> :
                        <CloseIcon fontSize="small"/>} </TableCell>
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
                                <TableCell>ID</TableCell>
                                <TableCell>Time</TableCell>
                                <TableCell>User</TableCell>
                                <TableCell>Obj num</TableCell>
                                <TableCell> </TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {data.map(note => (<LogRow key={note.id} note={note}/>))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Box>
        </div>
    );

}

export default ImportLogTable;