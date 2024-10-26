import store from "../store/store";
import {showError} from "../store/errorSlice";
const API_URL = 'http://localhost:8080/'
const AUTH_URL = API_URL + 'auth'
const USER_URL = API_URL + 'user'
const ROUTE_URL = API_URL + 'route'

const token = store.getState().user.token;



function showNetError(code, message){
    store.dispatch(showError({
        type: "error",
        summary: `Server returned an error ${code}`,
        detail: message
    }));
}

function showNetSuccess(message){
    store.dispatch(showError({
        type: "success",
        summary: "OK",
        detail: message,
    }));
}



async function makeRequest(url, method, body = null) {
    const headers = {
        'Content-Type': 'application/json',
        ...(token ? { token } : {})
    };


    let response;
    if(method === 'POST'){
        response = await fetch(url, {
            method,
            headers,
            body: JSON.stringify(body),
    });}
    else {
        response = await fetch(url, {
            method,
            headers,
    });}


    const data = await response.json();

    try {
        if (!response.ok) {
            showNetError(response.status, data.message);
            throw new Error(`Error: ${response.status} ${data.message}`);
        } else showNetSuccess(response.message);
        return data;
    } catch (error) {
        throw error;
    }
}

export async function signUpRequest(user) {
    return makeRequest(AUTH_URL + "/sign-up", 'POST', {
        username: user.username,
        password: user.password,
        role: user.role
    });
}

export async function signInRequest(user) {
    return makeRequest(AUTH_URL + "/sign-in", 'POST', {
        username: user.username,
        password: user.password,
    });
}

export async function approveUserRequest(user) {
    return makeRequest(USER_URL + "/approve", 'POST', {
        'message': user
    });
}

export async function approveUserListRequest() {
    return makeRequest(USER_URL + "/approve/list", 'GET');
}

export async function getRoutesListRequest() {
    return makeRequest(ROUTE_URL + "/list", 'GET');
}
export async function getSortedRoutesListRequest(sign, field, value) {
    return makeRequest(ROUTE_URL + `/list/sorted?sign=${sign}&field=${field}&value=${value}`, 'GET');
}

export async function addRouteRequest(route) {
    return makeRequest(ROUTE_URL + "/add", 'POST', route);
}
export async function updateRouteRequest(route) {
    return makeRequest(ROUTE_URL + "/update", 'POST', route);
}

export async function deleteRouteRequest(route) {
    return makeRequest(ROUTE_URL + "/delete", 'POST', {
        'message': route.id
    });
}

export async function deleteAllRoutesRequest() {
    return makeRequest(ROUTE_URL + "/delete/all", 'POST', {});
}


export async function getTotalRatingRequest() {
    return makeRequest(ROUTE_URL + "/func/total-rating", 'GET');
}

export async function getMaxToRequest() {
    return makeRequest(ROUTE_URL + "/func/max-to", 'GET');
}

export async function getGreatestRateRequest(rating) {
    return makeRequest(ROUTE_URL + "/func/greatest-rate?rating=" + rating, 'GET');
}

export async function getAllRoutesByRequest(from_id, to_id) {
    return makeRequest(ROUTE_URL + `/func/all-routes-by?from_location_id=${from_id}&to_location_id=${to_id}`, 'GET');
}

