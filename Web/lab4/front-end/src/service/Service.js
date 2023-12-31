const API_URL = 'http://127.0.0.0:9000/api/'
const AUTH_URL = API_URL + 'auth/';
const HIT_URL = API_URL + 'hits/'

export async function authorizeRequest(userState) {
    const userInfo = { username: userState.username, password: userState.password }

    const response = await fetch(AUTH_URL + (userState.login ? "login" : "signup"), {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(userInfo),
    });

    const text = await response.text();
    try{
        return JSON.parse(text);
    } catch (error) {
        console.log(error.message);

        return {message: text};
    }
}


export async function getHitsRequest(token) {
    try {
        const response = await fetch(HIT_URL, {
            method: 'GET',
            headers: {
                'Authorization': "Bearer " + token
            }
        });
        return await response.json();
    } catch (error) {
        console.log(error);

        return {error: error.name, message: error.message};
    }
}

export async function addHitRequest(hit, token) {
    const response = await fetch(HIT_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': "Bearer " + token
        },
        body: JSON.stringify(hit),
    });

    const text = await response.text();

    try{
        return JSON.parse(text);
    } catch (error) {
        console.log(error.message);
        return {message: text};
    }
}

export async function clearHitsRequest(token) {
    try {
        const response = await fetch(HIT_URL, {
            method: 'DELETE',
            headers: {
                'Authorization': "Bearer " + token
            },
        });
        if (response.status !== 200)
            return await response.json();
        return {success: true};
    } catch (error) {
        console.log(error);

        return {error: error.name, message: error.message};
    }
}