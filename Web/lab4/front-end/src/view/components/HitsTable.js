import {useDispatch, useSelector} from "react-redux";

import {Button} from "primereact/button";
import {clearHitsRequest} from "../../service/Service";
import {clearHits} from "../../store/userSlice";
import {showError} from "../../store/errorSlice";

import "../../resources/HitsTable.css"

function HitsTable() {
    const dispatch = useDispatch();

    const userInfo = useSelector(state => state.user);

    const Hit = (hit) => {
        return (
            <tr>
                <td>{hit.x}</td>
                <td>{hit.y}</td>
                <td>{hit.r}</td>
                <td>{hit.creationTime}</td>
                <td style={{ color: hit.result ?"greenyellow" : "red" }}>{hit.result ? "Area" : "Miss"}</td>
            </tr>
        );
    };

    const handleClear = async () => {
        const response = await clearHitsRequest(userInfo.token);

        if (response.message) {
            dispatch(showError({ detail: response.message }))
            return;
        }
        dispatch(clearHits());
    }

    return (
        <div className="table-dark col-md">
            <table id="results-table">
                <thead>
                <tr>
                    <th width="20%">X</th>
                    <th width="20%">Y</th>
                    <th width="15%">R</th>
                    <th width="40%">Creation time</th>
                    <th width="20%">Result</th>
                </tr>
                </thead>
                <tbody>
                    {
                        userInfo.hits.map(hit => {
                            return (
                                <Hit {...hit}/>
                            );
                        })
                    }
                </tbody>
            </table>
            <div id="clear-button-block">
                <Button label="Clear" id="clear-button" onClick={handleClear} />
            </div>
        </div>
    );
}

export default HitsTable;