import RoutesMap from "../components/RoutesMap";
import ErrorMessage from "../components/ErrorMessage";

const MapPage = (props) => {
    return (
        <div>
            <RoutesMap/>
            <ErrorMessage />
        </div>
    )
}


export default MapPage;