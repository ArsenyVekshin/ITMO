import RoutesMap from "../components/RoutesMap";
import ErrorMessage from "../components/ErrorMessage";
import {sampleRoutes} from "../components/RoutesTable";

const MapPage = (props) => {
    return (
        <div>
            <RoutesMap routes={sampleRoutes}/>
            <ErrorMessage />
        </div>
    )
}


export default MapPage;