const API_URL = 'http://localhost:8080/'
const USER_URL = API_URL + 'user'
const HIT_URL = API_URL + 'point'

export async function authorizeRequest(userState) {
    const userInfo = { username: userState.username, password: userState.password }

    const response = await fetch(USER_URL + "/auth"
                                            + "?login=" + userState.login
                                            + "&password=" + userState.password,
{
        method: 'GET',
        headers: {'Content-Type': 'application/json'},
    });

    const text = await response.text();
    try{
        return JSON.parse(text);  //TODO: ПОЛУЧЕННЫЙ ТОКЕН {message: token}
    } catch (error) {
        console.log(error.message);

        return {message: text};
    }
}

export async function logoutRequest() {
    let token = "ABOBA"; //TODO: значение токена из браузера

    const response = await fetch(USER_URL + "/logout",  {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'auth-token': token
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

export async function registerRequest(userState) {
    const response = await fetch(USER_URL + "/reg"
                                            + "?login=" + userState.login+"&password="
                                            + userState.password,
{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
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


export async function getPointsTable() {
    let token = "ABOBA"; //TODO: значение токена из браузера

    const response = await fetch(HIT_URL,  {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'auth-token': token
        },
    });

    const text = await response.text();
    try{
        return JSON.parse(text); //TODO: JSON таблицы с сервера
    } catch (error) {
        console.log(error.message);

        return {message: text};
    }
}

export async function checkPoint(point) {
    let token = "ABOBA"; //TODO: значение токена из браузера

    const response = await fetch(HIT_URL + "?x=" + point.x + "&y=" + point.y + "&y=" + point.r,  {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'auth-token': token
        },
    });

    const text = await response.text();
    try{
        return JSON.parse(text); //TODO: JSON точки с сервера
    } catch (error) {
        console.log(error.message);

        return {message: text};
    }
}

export async function clearPointsTable() {
    let token = "ABOBA"; //TODO: значение токена из браузера

    const response = await fetch(HIT_URL,  {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'auth-token': token
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
