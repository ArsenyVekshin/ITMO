import Auth from "../components/Auth";
import ErrorMessage from "../components/ErrorMessage";

const AuthPage = (props) => {
    return (
        <div>
            <Auth authType={props.authType}/>
        </div>
    )
}

export const RegisterPage = () => {
    return <AuthPage authType="register" />
}

export default AuthPage;