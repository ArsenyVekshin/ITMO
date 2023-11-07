var components = {
    x: document.querySelectorAll('input[class="x"]'),
    y: document.getElementById("y-value"),
    r: document.querySelectorAll('input[class="r"]'),
    submit: document.getElementById("submit-button")
};

var choosen = {
    x: [],
    y: null,
    r: null
};

// массив попавших точек для каждого радиуса
var pointsContainer = [];
var resultsTable = document.getElementById("results-content");


$(document).ready(function () {
    initialize_table(pointsContainer);
    redrawGraph(choosen.r);
});

components.x.forEach(cbx => cbx.addEventListener("change", checkEnteredX));
function checkEnteredX(){
    choosen.x = [];
    for (let cbx of components.x){
        if(cbx.checked) {
            choosen.x.push(cbx.value);
        }
    }
    updateSubmitLock();
    redrawGraph(choosen.r);
}

components.y.onblur = function checkEnteredY(){
    const yMin = -3.0;
    const yMax = 5.0;
    let y = components.y.value;
    let parsedY;
    choosen.y = null;
    console.log("CHECK Y", y)

    if(isNaN(y.trim()) || !y.match('[\-\+]?([0-5]?.[0-9]*)')){
        updateSubmitLock();
        return;
    }
    parsedY = parseFloat(y);
    if (isNaN(parsedY) || yMin > parsedY || parsedY > yMax) {
        updateSubmitLock();
        return;
    }
    choosen.y = y;
    updateSubmitLock();
}

components.r.forEach(rd => rd.addEventListener("change", checkEnteredR));
function checkEnteredR(){
    choosen.r = null;
    for (let rd of components.r) {
        if (rd.checked){
            choosen.r = rd.value;
        }
    }
    console.log("r=", choosen.r);
    updateSubmitLock();
    redrawGraph(choosen.r);
}

$('#submit-button').click(function() {
    choosen.x.forEach(_x =>{
        let [x, y, r] = validateAndParse(_x, choosen.y, choosen.r)
        let result = validate_values(x, y, r);
        if (result) {
            sendForm(pointsContainer, x, y, r);
        }
    });
});

$('#clear-button').click(function (event) {
    clean_table();
    redrawGraph(choosen.r);
});


document.addEventListener('click', (ev)=>this.handleClick(ev));
function handleClick(event) {

    // Получаем координаты точки, куда нажал пользователь
    let x = event.clientX.toFixed(3);
    let y = event.clientY.toFixed(3);
    let one = 30;

    if (x > canvasRect.left && x < canvasRect.right &&
        y < canvasRect.bottom && y > canvasRect.top) {

        //пересчитываем значения в локальные координаты
        x = (x - canvasRect.left - (canvasRect.width/2))/one;
        y = ((canvasRect.height/2) - y + canvasRect.top)/one;

        if(choosen.r !== null){
            sendForm(x.toFixed(3).toString(), y.toFixed(3).toString(), choosen.r);
        }

    }
}

function updateSubmitLock(){
    components.submit.disabled = choosen.x.length == 0 || choosen.y == null || choosen.r == null;
}