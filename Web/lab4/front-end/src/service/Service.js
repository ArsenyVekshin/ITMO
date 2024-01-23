const API_URL = 'http://localhost:8080/'
const USER_URL = API_URL + 'user'
const HIT_URL = API_URL + 'point'

export async function authorizeRequest(userState) {
    const response = await fetch(USER_URL + "/auth"
                                            + "?login=" + userState.username
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
    let token = localStorage.getItem('userToken');

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
                                            + "?login=" + userState.username
                                            +"&password=" + userState.password,
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
    let token = localStorage.getItem('userToken'); 

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
    let token = localStorage.getItem('userToken');

    const response = await fetch(HIT_URL + "?x=" + point.x + "&y=" + point.y + "&r=" + point.r,  {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'auth-token': token
        },
    });

    const text = await response.text();
    try{
        console.log(JSON.parse(text));
        return JSON.parse(text); //TODO: JSON точки с сервера ||табличка + отрисовка
    } catch (error) {
        console.log(error.message);

        return {message: text};
    }
}

export async function clearPointsTable() {
    let token = localStorage.getItem('userToken');

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
