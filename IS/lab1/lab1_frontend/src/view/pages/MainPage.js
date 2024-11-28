import RoutesTable from "../components/RoutesTable";
import {useSelector} from "react-redux";

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