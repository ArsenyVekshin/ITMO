"use strict";
class Application {
    graphCanvas = document.getElementById("graph_canvas");
    canvasRect = this.graphCanvas.getBoundingClientRect();
    ctx = this.graphCanvas.getContext('2d');
    resultsDataKey = "results";

    components = {
        x: document.querySelectorAll('input[class="x"]'),
        y: document.getElementById("y-value"),
        r: document.querySelectorAll('input[class="r"]'),
        submit: document.getElementById("submit-button")
    };
    choosen = {
        x: [],
        y: null,
        r: null
    };


    resultsTable = document.getElementById("results-content");
    sessionStorage = window.sessionStorage;

    constructor() {
        document.addEventListener('click', (ev)=>this.handleClick(ev));
        this.initTableResults();
        this.components.x.forEach( cbx=> cbx.addEventListener("focusout", this.checkEnteredX.bind(this)));
        this.components.y.addEventListener('focusout', this.checkEnteredY.bind(this));
        this.components.r.forEach( rd=> rd.addEventListener("focusout", this.checkEnteredR.bind(this)));
        const form = document.getElementById("form");
        form.addEventListener("submit", this.submitActions.bind(this));
        this.initFields();
        this.drawGraph();
        console.log(this.canvasRect.left);
    }

    initFields(){
        this.components.x.forEach( cbx=> cbx.checked = false);
        this.components.y.value = "";
        this.components.r.forEach( rd=> rd.checked = false);
    }


    updateSubmitLock(){
        this.components.submit.disabled = this.choosen.x.length == 0 || this.choosen.y == null || this.choosen.r == null;
    }

    checkEnteredX(){
        this.choosen.x = [];
        for (let cbx of this.components.x){
            if(cbx.checked) {
                this.choosen.x.push(cbx.value);
            }
        }
        this.updateSubmitLock();
        this.drawGraph();
    }

    checkEnteredY(){
        const yMin = -3.0;
        const yMax = 5.0;
        let y = this.components.y.value;
        let parsedY;
        this.choosen.y = null;
        console.log("CHECK Y", y)

        if(isNaN(y.trim()) || !y.match('[\-\+]?([0-5]?.[0-9]*)')){
            this.drawGraph();
            console.log("regular_err", y);
            this.updateSubmitLock();
            return;
        }
        parsedY = parseFloat(y);
        if (isNaN(parsedY) || yMin > parsedY || parsedY > yMax) {
            console.log("interval_err", y);
            this.updateSubmitLock();
            this.drawGraph();
            return;
        }
        this.choosen.y = y;
        this.updateSubmitLock();
        this.drawGraph();
    }

    checkEnteredR(){
        this.choosen.r = null;
        for (let rd of this.components.r) {
            if (rd.checked){
                this.choosen.r = rd.value;
            }
        }
        console.log("r=", this.choosen.r);
        this.updateSubmitLock();
        this.drawGraph();
    }


    validateAndParse(x, y, r) {
        let parsedX, parsedY, parsedR;

        parsedX = parseInt(x);
        if (isNaN(x.trim()) || isNaN(parsedX)) {
            alert("Please input correct X value");
            return [null,null,null];
        }

        parsedY = parseFloat(y);
        if (isNaN(y.trim()) || isNaN(parsedY)) {
            alert("Please input correct Y value");
            return [null,null,null];
        }

        parsedR = parseInt(r);
        if (isNaN(r.trim()) || isNaN(parsedX)) {
            alert("Please input correct R value");
            return [null,null,null];
        }

        console.log(x, y, r, "->", parsedX, parsedY, parsedR);
        return [parsedX, parsedY, parsedR]

    }

    async checkEnteredPoint(_x, _y, _r) {
        console.log("parsing point", _x, _y, _r)
        const [x, y, r] = this.validateAndParse(_x, _y, _r);
        if (x !== null && y !== null && r !== null) {
            try {
                console.log("pre sent");
                const response = await fetch("src/php/process.php" + "?x=" + x + "&y=" + y + "&r=" + r);
                console.log("response= ", response);

                const json = await response.json();
                if (response.status === 200) {
                    var data = [x, y, r, json.now, json.script_time, json.result];
                    addTableResults(data);
                } else {
                    alert("Server error: " + json.message);
                }
            } catch (error) {
                console.log(error);
                alert("Server unreachable :(\nTry again later");
            }
        }
    }

    submitActions() {
        this.components.submit.textContent = "Checking...";
        this.components.submit.disabled = true;

        for(let _x in this.choosen.x) {
            console.log("parsing point", this.choosen.x[_x], this.choosen.y, this.choosen.r)
            this.checkEnteredPoint(this.choosen.x[_x], this.choosen.y, this.choosen.r);
            }

        this.components.submit.disabled = false;
        this.components.submit.textContent = "Check";
    }


    initTableResults() {
        var data = this.sessionStorage.getItem(this.resultsTable);

        if (data === null) return;

        data.split(";").forEach(rowData => {
            var row = this.resultsTable.insertRow();

            rowData.split(",").forEach(cellData =>
                row.insertCell().innerHTML = cellData
            )
        })
    }

    addTableResults(rowData) {
        var row = this.resultsTable.insertRow(0);

        document.querySelectorAll('td[style="color: greenyellow;"]').forEach(cell => cell.removeAttribute("style"));
        document.querySelectorAll('td[style="color: red;"]').forEach(cell => cell.removeAttribute("style"));

        rowData.forEach(cellData => {
            var cell = row.insertCell();
            cell.innerHTML = cellData;

            if (cellData === "area") {
                cell.style = "color: greenyellow;";
            } else if (cellData === "miss") {
                cell.style = "color: red;";
            }
        });

        var lastData = this.sessionStorage.getItem(this.resultsDataKey);
        this.sessionStorage.setItem(this.resultsDataKey, rowData.toString() + (lastData ? ";" + lastData : ""));
    }

    drawGraph() {
        let one = 30;
        let width = this.graphCanvas.width;
        let height = this.graphCanvas.height;

        this.ctx.beginPath();
        this.ctx.clearRect(0, 0, this.graphCanvas.width, this.graphCanvas.height);

        if (this.choosen.r !== null) {
            this.ctx.strokeStyle = 'rgb(233,197,255)';
            this.ctx.fillStyle = 'rgb(233,197,255)';
            this.ctx.beginPath();
            let r = this.choosen.r * one;
            // Прямоугольник
            this.ctx.fillRect(width / 2, height / 2, r, -1*r/2);

            // Треугольник
            this.ctx.moveTo(width / 2, height / 2);
            this.ctx.lineTo(width / 2, height / 2 - r / 2);
            this.ctx.lineTo(width / 2 - r, height / 2);

            // Круг
            this.ctx.moveTo(width / 2, height / 2);
            this.ctx.arc(width / 2, height / 2, r, -3 * Math.PI / 2, -1 * Math.PI, false);

            this.ctx.fill();
            this.ctx.closePath();
        }

        this.ctx.strokeStyle = 'rgb(80,80,80)';
        this.ctx.fillStyle = 'rgb(80,80,80)';

        // Вертикали
        this.ctx.moveTo(0, height / 2);
        this.ctx.lineTo(width, height / 2);
        this.ctx.moveTo(width / 2, 0);
        this.ctx.lineTo(width / 2, height);
        this.ctx.stroke();
        this.ctx.closePath();

        this.ctx.strokeStyle = 'rgb(0, 0, 0)';
        this.ctx.beginPath();
        let i = 0;
        let lSize = 6;
        while (i * one <= width / 2) {
            this.ctx.moveTo(width / 2 + i * one, height / 2 + lSize / 2);
            this.ctx.lineTo(width / 2 + i * one, height / 2 - lSize / 2);
            this.ctx.moveTo(width / 2 + -i * one, height / 2 + lSize / 2);
            this.ctx.lineTo(width / 2 + -i * one, height / 2 - lSize / 2);
            i++;
        }
        i = 0;
        while (i * one <= height / 2) {
            this.ctx.moveTo(width / 2 - lSize / 2, height / 2 + i * one);
            this.ctx.lineTo(width / 2 + lSize / 2, height / 2 + i * one);
            this.ctx.moveTo(width / 2 - lSize / 2, height / 2 + -i * one);
            this.ctx.lineTo(width / 2 + lSize / 2, height / 2 + -i * one);
            i++;
        }

        this.ctx.stroke();
        this.ctx.closePath();


        for(let _x in this.choosen.x) {
            if(this.choosen.y != null){
                let x = this.choosen.x[_x] * one;
                let y = this.choosen.y * one;
                this.ctx.beginPath();
                this.ctx.fillStyle = "rgb(0, 0, 0)";
                this.ctx.arc((width / 2) + x, (height / 2) - y, 2, 0, 2 * Math.PI);
                this.ctx.fill();
                this.ctx.closePath();
            }
        }
    }

    handleClick(event) {
        // Получаем координаты точки, куда нажал пользователь
        let x = event.clientX;
        let y = event.clientY;
        let one = 30;

        if (x > this.canvasRect.left && x < this.canvasRect.right &&
            y < this.canvasRect.bottom && y > this.canvasRect.top) {

            //пересчитываем значения в локальные координаты
            x = (x - this.canvasRect.left - (this.canvasRect.width/2))/one;
            y = ((this.canvasRect.height/2) - y + this.canvasRect.top)/one;

            console.log("click point = ", x, y);

            if(this.choosen.r !== null){
                this.checkEnteredPoint(x.toString(), y.toString(), this.choosen.r);
            }

        }
    }

}
const app = new Application();