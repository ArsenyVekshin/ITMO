const graphCanvas = document.getElementById("graph_canvas");
const canvasRect = graphCanvas.getBoundingClientRect();
const ctx = graphCanvas.getContext('2d');

const one = 30;
const width = graphCanvas.width;
const height = graphCanvas.height;

function redrawGraph(r) {
    ctx.beginPath();
    ctx.clearRect(0, 0, graphCanvas.width, graphCanvas.height);

    //рисуем основные элементы графика
    drawFunction(r);
    drawAxis();

    //рисуем точки из истории с этим радиусом
    drawPreviousPoints(r);

    //рисуем все точки выбранные на панели
    for(let _x in choosen.x) {
        if(choosen.y != null){
            drawPoint(choosen.x[_x], choosen.y, 0, 0, 0);
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
        let r = choosen.r * one;
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

function drawPreviousPoints(r){
    console.log(pointsContainer);
    if(r==null || pointsContainer.length === 0) return;
    pointsContainer.forEach(point =>{
        if(point[2]==r){
            drawPoint(point[0], point[1], 0, 153, 0);
        }
    });
}