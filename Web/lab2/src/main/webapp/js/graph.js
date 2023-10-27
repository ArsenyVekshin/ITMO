const graphCanvas = document.getElementById("graph_canvas");
const canvasRect = this.graphCanvas.getBoundingClientRect();
const ctx = this.graphCanvas.getContext('2d');

const one = 30;
const width = graphCanvas.width;
const height = graphCanvas.height;


$(document).ready(function () {
    $('#r_select').on('change', );

    $('#submit-button').click(function (event) {
        var x = document.querySelectorAll('input[type="checkbox"]:checked');
        var y = document.getElementById('y_text');
        var r = document.querySelector('option:checked');
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

    $('#clear-button').click(function (event) {
        clean_table();
        redrawGraph(choosen.r);
    });

    $('#btnClean').click(function (event) {
        r_selector.each(function () {
            let idxRadius = $(this).val();
            board.removeObject(pointsByRadius[idxRadius]);
            pointsByRadius[idxRadius] = [];
        });
        clean_table();
    });

    board.on("down", function (event) {
        if (event.button === 2 || event.target.className === 'JXG_navigation_button') {
            return;
        }
        if (check_r()) {
            let coords = board.getUsrCoordsOfMouse(event);
            sendForm(board, pointsByRadius, coords[0], coords[1], document.querySelector('option:checked').value);
        } else {
            var alrt = document.getElementById('alert');
            alrt.innerHTML = "<strong>You should choose R</strong>"
        }
    });
});

function redrawGraph(r) {
    ctx.beginPath();
    ctx.clearRect(0, 0, this.graphCanvas.width, this.graphCanvas.height);

    //рисуем основные элементы графика
    drawFunction(r);
    drawAxis();

    //рисуем все точки выбранные на панели
    for(let _x in this.choosen.x) {
        if(this.choosen.y != null){
            drawPoint(this.choosen.x[_x], this.choosen.y, 0, 0, 0);
        }
    }
}

function drawAxis(){
    ctx.strokeStyle = 'rgb(80,80,80)';
    ctx.fillStyle = 'rgb(80,80,80)';

    // Вертикали
    ctx.moveTo(0, height / 2);
    ctx.lineTo(width, height / 2);
    ctx.moveTo(width / 2, 0);
    ctx.lineTo(width / 2, height);
    ctx.stroke();
    ctx.closePath();

    ctx.strokeStyle = 'rgb(0, 0, 0)';
    ctx.beginPath();
    let i = 0;
    let lSize = 6;
    while (i * one <= width / 2) {
        ctx.moveTo(width / 2 + i * one, height / 2 + lSize / 2);
        ctx.lineTo(width / 2 + i * one, height / 2 - lSize / 2);
        ctx.moveTo(width / 2 + -i * one, height / 2 + lSize / 2);
        ctx.lineTo(width / 2 + -i * one, height / 2 - lSize / 2);
        i++;
    }
    i = 0;
    while (i * one <= height / 2) {
        ctx.moveTo(width / 2 - lSize / 2, height / 2 + i * one);
        ctx.lineTo(width / 2 + lSize / 2, height / 2 + i * one);
        ctx.moveTo(width / 2 - lSize / 2, height / 2 + -i * one);
        ctx.lineTo(width / 2 + lSize / 2, height / 2 + -i * one);
        i++;
    }

    ctx.stroke();
    ctx.closePath();
}

function drawFunction(r){
    if (r !== null) {
        ctx.strokeStyle = 'rgb(233,197,255)';
        ctx.fillStyle = 'rgb(233,197,255)';
        ctx.beginPath();
        let r = this.choosen.r * one;
        // Прямоугольник
        ctx.fillRect(width / 2, height / 2, r, -1*r/2);

        // Треугольник
        ctx.moveTo(width / 2, height / 2);
        ctx.lineTo(width / 2, height / 2 - r / 2);
        ctx.lineTo(width / 2 - r, height / 2);

        // Круг
        ctx.moveTo(width / 2, height / 2);
        ctx.arc(width / 2, height / 2, r, -3 * Math.PI / 2, -1 * Math.PI, false);

        ctx.fill();
        ctx.closePath();
    }
}

function drawPoint(x, y, r, g, b){
    x *= one;
    y *= one;
    ctx.beginPath();
    ctx.fillStyle = "rgb(" + r + "," + g + "," + b + ")";
    ctx.arc((width / 2) + x, (height / 2) - y, 2, 0, 2 * Math.PI);
    ctx.fill();
    ctx.closePath();
}
