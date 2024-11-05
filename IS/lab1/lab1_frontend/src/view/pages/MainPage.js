
import RoutesTable from "../components/RoutesTable";
import {useDispatch, useSelector} from "react-redux";
import ErrorMessage from "../components/ErrorMessage";
import {Navigate} from "react-router-dom";

const MainPage = () => {
    const collection = useSelector(state => state.collection);
    return (
        <>
            <div className="row justify-content-md-center">
                <RoutesTable collection={collection}/>
            </div>
        </>
    )
}
export default MainPage;