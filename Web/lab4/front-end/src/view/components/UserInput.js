import {useDispatch, useSelector} from "react-redux";
import {useState} from "react";
import {Slider} from "primereact/slider";
import {InputNumber} from "primereact/inputnumber";
import { Button } from 'primereact/button';

import "primereact/resources/themes/lara-light-indigo/theme.css";
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";

import {addHit, changeR} from "../../store/userSlice";
import {addHitRequest} from "../../service/Service";
import {showError} from "../../store/errorSlice";

import "../../resources/UserInput.css"

const UserInput = () => {
    const dispatch = useDispatch();

    const userInfo = useSelector(state => state.user);

    const [point, setPoint] = useState({
        x: 0.0,
        y: 0.0,
        r: userInfo.r,
    });

    const [isLoading, setLoading] = useState(false);

    const handleRChange = (e) => {
        dispatch(changeR(e.value))
        setPoint({ ...point, r: e.value })
    };

    const handleSubmit = async () => {
        setLoading(true);

        const response = await addHitRequest(point, userInfo.token);

        if (response.message) {
            dispatch(showError({ detail: response.message }))
            return;
        }

        dispatch(addHit(response));

        setLoading(false);
    }

    return (
        <div className="controller col-md">
            <label id="controller-title">Control pane</label>

            <div className="coordinateInput">
                <label className="coordinateName">X</label>
                <Slider value={point.x} min={-5} max={3} step={0.01} onChange={e => setPoint({ ...point, x: e.value })}/>
                <span className="coordinateValue">{point.x}</span>
            </div>
            <div className="coordinateInput">
                <label className="coordinateName">Y</label>
                <InputNumber
                    value={point.y} onValueChange={e => setPoint({ ...point, y: e.value })}
                    min={-3} max={3} minFractionDigits={0} maxFractionDigits={2}
                />
                <span className="coordinateValue"></span>
            </div>
            <div className="coordinateInput">
                <label className="coordinateName">R</label>
                <Slider value={point.r} min={0.5} max={3} step={0.5} onChange={handleRChange}/>
                <span className="coordinateValue">{point.r}</span>
            </div>

            <div className="submitButton coordinateInput">
                <Button label="Check" icon="pi pi-times-circle" iconPos="right" loading={isLoading} onClick={handleSubmit} />
            </div>
            {/* <p>Current data. X: {point.x}, Y: {point.y}, R: {point.r}</p> */}
        </div>
    );
}

export default UserInput;