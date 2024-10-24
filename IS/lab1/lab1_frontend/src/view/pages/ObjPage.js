
import RoutesTable from "../components/RoutesTable";
import {useDispatch, useSelector} from "react-redux";
import ErrorMessage from "../components/ErrorMessage";
import RouteForm from "../components/RouteForm";

const ObjPage = () => {
    return (
        <>
            <div className="row justify-content-md-center">
                <RouteForm />
            </div>
        </>
    )
}
export default ObjPage;