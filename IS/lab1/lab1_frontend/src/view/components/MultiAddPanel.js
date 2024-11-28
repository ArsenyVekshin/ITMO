import React, {useState} from 'react';
import {multiAddRoutesRequest} from "../../service/Service";
import {Button, Collapse, Input, Typography} from "@mui/material";
import {showError} from "./ErrorMessage";
import {Light as SyntaxHighlighter} from 'react-syntax-highlighter';
import {dracula} from 'react-syntax-highlighter/dist/esm/styles/prism';

const MultiAddPanel = () => {
    const [file, setFile] = useState(null);
    const [open, setOpen] = useState(false); // Состояние для управления видимостью блока

    const handleFileChange = (event) => {
        setFile(event.target.files[0]);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!file) {
            showError("File undefined", "Please select a file first!");
            setFile(null);
            return;
        } else {
            if (file.name.split('.').pop().toLowerCase() !== "json") {
                showError("Wrong format", "Invalid file type. Please upload a JSON file.");
                setFile(null);
                return;
            }
        }

        await multiAddRoutesRequest(file);
    };

    return (
        <div>
            <h1>Upload File</h1>
            <form onSubmit={handleSubmit}>
                <Input
                    type="file"
                    inputProps={{accept: '.json'}}
                    onChange={handleFileChange}
                    sx={{marginBottom: 2}}
                />
                <Button
                    variant="contained"
                    type="submit"
                >Upload</Button>
            </form>

            <Button
                variant="outlined"
                sx={{marginTop: 2}}
                onClick={() => setOpen(!open)}
            >
                {open ? "Hide Sample File" : "Show Sample File"}
            </Button>

            <Collapse in={open}>
                <Typography sx={{marginTop: 2}}>
                    <SyntaxHighlighter language="json" style={dracula}>
                        {`
{
  "routes": [
    {
      "id": 4,
      "name": "name2",
      "coordinates": {
        "id": null,
        "x": 2,
        "y": 2
      },
      "from": {
        "id": null,
        "x": 2,
        "y": 2,
        "z": 2,
        "name": "name2"
      },
      "to": {
        "id": null,
        "x": 2,
        "y": 2,
        "z": 2,
        "name": "name2"
      },
      "distance": 2.0,
      "rating": 2,
      "owner": "admin",
      "readonly": false
    }
  ]
}
                        `}
                    </SyntaxHighlighter>
                </Typography>
            </Collapse>
        </div>
    );
};

export default MultiAddPanel;
