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



components.x.forEach(cbx => cbx.addEventListener("change", checkEnteredX));
function checkEnteredX(){
    this.choosen.x = [];
    for (let cbx of this.components.x){
        if(cbx.checked) {
            this.choosen.x.push(cbx.value);
        }
    }
    this.updateSubmitLock();
    this.drawGraph();
}

components.y.onblur = function checkEnteredY(){
    const yMin = -3.0;
    const yMax = 5.0;
    let y = this.components.y.value;
    let parsedY;
    this.choosen.y = null;
    console.log("CHECK Y", y)

    if(isNaN(y.trim()) || !y.match('[\-\+]?([0-5]?.[0-9]*)')){
        this.updateSubmitLock();
        return;
    }
    parsedY = parseFloat(y);
    if (isNaN(parsedY) || yMin > parsedY || parsedY > yMax) {
        this.updateSubmitLock();
        return;
    }
    this.choosen.y = y;
    this.updateSubmitLock();
}

components.r.forEach(rd => rd.addEventListener("change", checkEnteredR));
function checkEnteredR(){
    choosen.r = null;
    for (let rd of components.r) {
        if (rd.checked){
            choosen.r = rd.value;
        }
    }
    console.log("r=", this.choosen.r);
    updateSubmitLock();
    redrawGraph(choosen.r);
}


function submitActions() {
    components.submit.textContent = "Checking...";
    components.submit.disabled = true;

    for(let _x in this.choosen.x) {
        let [x, y, r] = validate_values(_x, choosen.y, choosen.r)
        var result = validate_values(x, y, r);
        var alrt = document.getElementById('alert');
        if (result !== "") {
            alrt.innerHTML = "<strong>" + result + "</strong>";
        } else {
            sendForm(board, pointsByRadius, _x, y.value.replace(",", "."), r.value);
        }
    }

    components.submit.disabled = false;
    components.submit.textContent = "Check";
}

$('#submit-button').click(function (event) {
    choosen.x.forEach()
    for(let _x in this.choosen.x) {
        console.log("parsing point", this.choosen.x[_x], this.choosen.y, this.choosen.r)
        this.checkEnteredPoint(this.choosen.x[_x], this.choosen.y, this.choosen.r);
    }



    let [x, y, r] = validate_values(choosen.x, choosen.y, choosen.r)
    var result = validate_values(x, y, r);
    var alrt = document.getElementById('alert');
    if (result !== "") {
        alrt.innerHTML = "<strong>" + result + "</strong>";
    } else {
        x.forEach(function (xNumber) {
            sendForm(board, pointsByRadius, xNumber.value, y.value.replace(",", "."), r.value);
        });
    }
});