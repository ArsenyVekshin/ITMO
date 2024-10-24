import ErrorMessage from "../components/ErrorMessage";
import UserRolesPanel from "../components/UserRolesPanel";

const AdminPanelPage = (props) => {
    return (
        <div>
            <UserRolesPanel/>
            <ErrorMessage />
        </div>
    )
}

export default AdminPanelPage;