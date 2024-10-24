
import RoutesTable from "../components/RoutesTable";
import {useDispatch, useSelector} from "react-redux";
import ErrorMessage from "../components/ErrorMessage";
import {Navigate} from "react-router-dom";

const MainPage = () => {
    return (
        <>
            <div className="row justify-content-md-center">
                <RoutesTable />
            </div>
        </>
    )
}
export default MainPage;