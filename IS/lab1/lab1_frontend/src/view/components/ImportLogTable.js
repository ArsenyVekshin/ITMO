import {
    Box,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Tooltip
} from "@mui/material";
import React, {useEffect, useState} from "react";
import CloseIcon from '@mui/icons-material/Close';
import DoneIcon from '@mui/icons-material/Done';
import VisibilityIcon from '@mui/icons-material/Visibility';
import {getFileFromServer, getImportLogRequest} from "../../service/Service";

const ImportLogTable = () => {
    const [data, setData] = useState([]);
    const [openDialog, setOpenDialog] = useState(false);
    const [fileContent, setFileContent] = useState("");
    const [fileName, setFileName] = useState("");

    const fetchLog = async () => {
        setData(await getImportLogRequest());
    };

    useEffect(() => {
        fetchLog();
        const interval = setInterval(fetchLog, 5000);
        return () => clearInterval(interval);
    }, []);

    const fetchFile = async (key) => {
        setFileName(key);
        const buff = await getFileFromServer(key);
        setFileContent(JSON.stringify(buff, null, 2));
        setOpenDialog(true);
    };

    const handleCloseDialog = () => {
        setOpenDialog(false);
    };

    const LogRow = ({note}) => {
        return (
            <TableRow>
                <TableCell>{note.id}</TableCell>
                <TableCell>{note.creationDate}</TableCell>
                <TableCell>{note.owner}</TableCell>
                <TableCell>{note.number}</TableCell>
                <TableCell>
                    {note.key ? (
                        <IconButton onClick={() => fetchFile(note.key)}>
                            <VisibilityIcon fontSize="small"/>
                        </IconButton>
                    ) : " "}
                </TableCell>
                <TableCell>
                    {note.successful ? (
                        <DoneIcon fontSize="small" color="success"/>
                    ) : (
                        <Tooltip title={note.error || "Error occurred"}>
                            <IconButton>
                                <CloseIcon fontSize="small" color="error"/>
                            </IconButton>
                        </Tooltip>
                    )}
                </TableCell>
            </TableRow>
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
                                <TableCell>File</TableCell>
                                <TableCell>Status</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {data.map(note => (<LogRow key={note.id} note={note}/>))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Box>

            <Dialog open={openDialog} onClose={handleCloseDialog}>
                <DialogTitle>{fileName}</DialogTitle>
                <DialogContent>
                    <Box component="pre" sx={{ whiteSpace: 'pre-wrap', wordBreak: 'break-word', fontFamily: 'monospace' }}>
                        {fileContent}
                    </Box>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDialog} color="primary">
                        Close
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};

export default ImportLogTable;
