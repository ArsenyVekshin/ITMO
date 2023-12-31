import React, { useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";

import AuthPage, {RegisterPage} from "./view/pages/AuthPage";
import MainPage from "./view/pages/MainPage";
import PageNotFound from "./view/pages/PageNotFound";

import "./resources/index.css"

import {logOut} from "./store/userSlice";

const App = () => {
  const userAuthorized = useSelector((state) => state.user.auth);
  const dispatch = useDispatch();

  const logOutCallback = useCallback(() => {
    dispatch(logOut());
  }, [dispatch]);

  return (
      <Router>
        <div>
          <nav className="navbar navbar-expand navbar-dark bg-dark">
            <div className="navbar-nav">
              {userAuthorized ? (
                  <li className="nav-item">
                    <Link to={"/login"} className="nav-link" onClick={logOutCallback}>
                      Log Out
                    </Link>
                  </li>
              ) : (
                  <>
                  <li className="nav-item">
                    <Link to={"/login"} className="nav-link">
                      Login
                    </Link>
                  </li>
                  <li>
                    <Link to={"/register"} className="nav-link">
                      Register
                    </Link>
                  </li>
                  </>
              )}
            </div>
          </nav>

          <div className="container-fluid mt-3">
            <Routes>
              <Route path="/" element={<AuthPage /> } />
              <Route path="/login" element={<AuthPage /> } />
              <Route path="/register" element={<RegisterPage /> } />
              <Route path="/main" element={<MainPage /> }/>;
              <Route path="*" element={<PageNotFound />}/>
            </Routes>
          </div>
        </div>
      </Router>
  );
}

export default App;
