import store from "../store/store";


const API_URL = 'http://localhost:8080/'
const AUTH_URL = API_URL + 'auth/'
const USER_URL = API_URL + 'user/'
const ROUTE_URL = API_URL + 'route/'

const token = store.getState().user.token;

export async function signUpRequest(user) {
    const response = await fetch(AUTH_URL + "/sign-up",
        {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: {
                'username': user.username,
                'password': user.password,
                'role': user.role
            }
        });

    const text = await response.text();
    try{
        return JSON.parse(text);  //TODO: ПОЛУЧЕННЫЙ ТОКЕН {message: token}
    } catch (error) {
        console.log(error.message);

        return {message: text};
    }
}

export async function signInRequest(user) {
    const response = await fetch(AUTH_URL + "/sign-in",
        {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: {
                'username': user.username,
                'password': user.password,
            }
        });

    const text = await response.text();
    try{
        return JSON.parse(text);  //TODO: ПОЛУЧЕННЫЙ ТОКЕН {message: token}
    } catch (error) {
        console.log(error.message);

        return {message: text};
    }
}

export async function approveUserRequest(user) {
    const response = await fetch(USER_URL + "/approve",
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'token': token,
            },
            body: {
                'message': user
            }
        });

    const text = await response.text();
    try{
        return JSON.parse(text);  //TODO: ПОЛУЧЕННЫЙ ТОКЕН {message: token}
    } catch (error) {
        console.log(error.message);

        return {message: text};
    }
}

export async function approveUserListRequest() {
    const response = await fetch(USER_URL + "/approve/list",
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'token': token,
            },
        });

    const text = await response.text();
    try{
        return JSON.parse(text);  //TODO: ПОЛУЧЕННЫЙ ТОКЕН {message: token}
    } catch (error) {
        console.log(error.message);

        return {message: text};
    }
}

export async function updateRouteRequest(route) {
    const response = await fetch(ROUTE_URL + "/update",
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'token': token,
            },
            body: route
        });

    const text = await response.text();
    try{
        return JSON.parse(text);
    } catch (error) {
        console.log(error.message);

        return {message: text};
    }
}

export async function deleteRouteRequest(route) {
    const response = await fetch(ROUTE_URL + "/delete",
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'token': token,
            },
            body: {
                'message': route.id
            }
        });

    const text = await response.text();
    try{
        return JSON.parse(text);
    } catch (error) {
        console.log(error.message);

        return {message: text};
    }
}

export async function deleteAllRoutesRequest() {
    const response = await fetch(ROUTE_URL + "/delete/all",
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'token': token,
            },
            body: {

            }
        });

    const text = await response.text();
    try{
        return JSON.parse(text);
    } catch (error) {
        console.log(error.message);

        return {message: text};
    }
}

export async function addRouteRequest(route) {
    const response = await fetch(ROUTE_URL + "/add",
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'token': token,
            },
            body: route
        });

    const text = await response.text();
    try{
        return JSON.parse(text);
    } catch (error) {
        console.log(error.message);

        return {message: text};
    }
}

export async function getTotalRatingRequest() {
    const response = await fetch(ROUTE_URL + "/func/total-rating",
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'token': token,
            },
        });

    const text = await response.text();
    try{
        return JSON.parse(text);
    } catch (error) {
        console.log(error.message);

        return {message: text};
    }
}

export async function getMaxToRequest() {
    const response = await fetch(ROUTE_URL + "/func/max-to",
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'token': token,
            },
        });

    const text = await response.text();
    try{
        return JSON.parse(text);
    } catch (error) {
        console.log(error.message);

        return {message: text};
    }
}
export async function getGreatestRateRequest(rating) {
    const response = await fetch(ROUTE_URL + "/func/greatest-rate" + "?rating=" + rating ,
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'token': token,
            },
        });

    const text = await response.text();
    try{
        return JSON.parse(text);
    } catch (error) {
        console.log(error.message);

        return {message: text};
    }
}

export async function getAllRotesByRequest(from_id, to_id) {
    const response = await fetch(ROUTE_URL + "/func/all-routes-by"
        + "?from_location_id=" + from_id
        + "?to_location_id =" + to_id ,
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'token': token,
            },
        });

    const text = await response.text();
    try{
        return JSON.parse(text);
    } catch (error) {
        console.log(error.message);

        return {message: text};
    }
}
