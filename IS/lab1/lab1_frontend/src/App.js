import React from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";


import {ThemeProvider} from '@mui/material/styles';
import AuthPage, {RegisterPage} from "./view/pages/AuthPage";
import MainPage from "./view/pages/MainPage";
import PageNotFound from "./view/pages/PageNotFound";

import "./resources/index.css"
import NavBar from "./view/components/NavBar";
import theme from "./resources/theme";
import Footer from "./view/components/Footer";
import {Box} from "@mui/material";
import ObjPage from "./view/pages/ObjPage";
import MapPage from "./view/pages/MapPage";
import AdminPanelPage from "./view/pages/AdminPanelPage";
import {useSelector} from "react-redux";
import ErrorMessage from "./view/components/ErrorMessage";
import CollectionFuncPanel from "./view/components/CollectionFuncPanel";
import MultiAddPanel from "./view/components/MultiAddPanel";

const App = () => {
    const user = useSelector(state => state.user);

    return (
        <ThemeProvider theme={theme}>
            <Box
                display="flex"
                flexDirection="column"
                minHeight="100vh"
            >
                <Box flexGrow={1}>
                    <Router>
                        <div>
                            <NavBar/>
                            <div className="container-fluid mt-3">
                                <Routes>
                                    <Route path="/" element={<AuthPage/>}/>
                                    <Route path="/sign-in" element={<AuthPage/>}/>
                                    <Route path="/sign-up" element={<RegisterPage/>}/>
                                    {user.auth ? (
                                        <>
                                            <Route path="/main" element={<MainPage/>}/>
                                            <Route path="/func" element={<CollectionFuncPanel/>}/>
                                            <Route path="/map" element={<MapPage/>}/>
                                            <Route path="/obj" element={<ObjPage/>}/>
                                            <Route path="/obj/file" element={<MultiAddPanel/>}/>
                                            {user.adminRole && <Route path="/admin" element={<AdminPanelPage/>}/>}
                                            <Route path="*" element={<PageNotFound/>}/>
                                        </>
                                    ) : (
                                        <Route path="*" element={<AuthPage/>}/>
                                    )}
                                </Routes>
                            </div>
                        </div>
                    </Router>
                </Box>
                <ErrorMessage/>
                <Footer/>
            </Box>
        </ThemeProvider>
    );
}

export default App;
