"use strict";

class Application {
    components = {
        x: document.getElementById("x-value"),
        y: document.getElementById("y-value"),
        r: document.getElementById("r-value"),
        submit: document.getElementById("submit-button")
    };

    resultsTable = document.getElementById("results-content");
    sessionStorage = window.sessionStorage;
    gmt = Intl.DateTimeFormat().resolvedOptions().timeZone;

    constructor() {
        this.initYValue();
        this.initRValue();
        this.initTableResults();

        const y_checkboxes = document.getElementsByClassName("y"),
            form = document.getElementById("form");

        Array.from(y_checkboxes).forEach(cbx =>
            cbx.addEventListener("change", this.setYValue.bind(this))
        );

        form.addEventListener("submit", this.submitActions.bind(this));
    }

    initYValue(){
        const selectedY = document.getElementById(`x${this.components.x.value}`);
        if (selectedY) {
            selectedY.classList.add("choosen");
        } else {
            this.components.y.value = "";
        }
    }

    initRValue(){
        //selectElement.options[selectElement.selectedIndex].value
        const selectedR = document.getElementById("r");
        var valueR = selectedR.value;

        console.log(valueR);

        if (valueR) {
            selectedR.classList.add("choosen");
            this.components.r.value = valueR;
        } else {
            this.components.r.value = "";
        }
    }

    setYValue(e){
        const selectedYCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');

        this.components.r.value = "";
        selectedYCheckboxes.forEach(cbx =>
            this.components.y.value += cbx.value
        );
        console.log("y=" + this.components.y.value);
    }

    validateAndParse(x, y, r) {
        const xMin = -5.0;
        const xMax = 5.0;
        const yValues = [-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5];
        const rValues = [1, 1.5, 2, 2.5, 3];

        let parsedX, parsedY, parsedR;

        parsedX = parseFloat(x);
        if (isNaN(x.trim()) || isNaN(parsedX) || xMin > parsedX || parsedX > xMax) {
            alert("Please input correct X value");
            return [null,null,null];
        }

        parsedY = parseInt(y);
        if (isNaN(y.trim()) || isNaN(parsedY) || !yValues.includes(parsedY)) {
            alert("Choose only one checkbox");
            return [null,null,null];
        }

        parsedR = parseFloat(r);
        if (isNaN(r.trim()) || isNaN(parsedR) || !rValues.includes(parsedR)) {
            alert("Please input correct R value");
            return [null,null,null];
        }
        console.log(x, y, r, "->", parsedX, parsedY, parsedR);
        return [parsedX, parsedY, parsedR]

    }

    async submitActions(e){
        e.preventDefault();
        this.initRValue();
        this.components.submit.textContent = "Checking...";
        this.components.submit.disabled = true

        const [x, y, r] = this.validateAndParse(this.components.x.value, this.components.y.value, this.components.r.value);
        if (x !== null && y !== null && r !== null) {
            try {
                console.log("pre sent");
                const response = await fetch("src/php/process.php" + "?x=" + x + "&y=" + y + "&r=" + r);
                console.log("response= ", response);

                const json = await response.json();
                if (response.status === 200) {
                    var data = [x, y, r, json.now, json.script_time, json.result];
                    this.addTableResults(data);
                } else {
                    alert("Server error: " + json.message);
                }
            } catch (error) {
                console.log(error);
                alert("Server unreachable :(\nTry again later");
            }
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

        var lastData = this.sessionStorage.getItem(resultsDataKey);
        this.sessionStorage.setItem(resultsDataKey, rowData.toString() + (lastData ? ";" + lastData : ""));
    }

}

const app = new Application();