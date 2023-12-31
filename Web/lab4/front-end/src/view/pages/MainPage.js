import Header from "../components/Header";
import UserInput from "../components/UserInput";
import Graph from "../components/Graph";
import HitsTable from "../components/HitsTable";
import {useDispatch, useSelector} from "react-redux";
import {getHitsRequest} from "../../service/Service";
import {setHits} from "../../store/userSlice";
import ErrorMessage from "../components/ErrorMessage";
import {Navigate} from "react-router-dom";

const MainPage = () => {
    const dispatch = useDispatch();
    const token = useSelector(state => state.user.token)

    if (!token) {
        return <Navigate to="/login" />;
    }

    console.log("Get all hits")

    getHitsRequest(token).then( res => {
        if (Array.isArray(res)) dispatch(setHits(res));
    })

    return (
        <>
            <Header />
            <div className="row justify-content-md-center">
                <UserInput />
                <Graph />
                <HitsTable />
            </div>
            <ErrorMessage />
        </>
    )
}
export default MainPage;