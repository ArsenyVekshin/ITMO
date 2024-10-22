
import RoutesTable from "../components/RoutesTable";
import {useDispatch, useSelector} from "react-redux";
import ErrorMessage from "../components/ErrorMessage";
import RouteForm from "../components/RouteForm";

const ObjPage = () => {
    const dispatch = useDispatch();
    const token = useSelector(state => state.user.token)

    // if (!token) {
    //     return <Navigate to="/login" />;
    // }

    /*
        console.log("Get all hits")

        getPointsTable(token).then( res => {
            if (Array.isArray(res)) dispatch(setHits(res));
        })
    */

    return (
        <>
            <div className="row justify-content-md-center">
                <RouteForm />
            </div>
            <ErrorMessage />
        </>
    )
}
export default ObjPage;