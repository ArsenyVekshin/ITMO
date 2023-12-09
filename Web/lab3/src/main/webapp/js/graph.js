const figuresProperties = {
    strokeColor: 'green',
    highlightStrokeColor: 'pink',
    fillColor: 'pink',
    highlightFillColor: 'black',
    fixed: true

};
const pointProperties = function (visible, success) {
    return {
        strokeColor: success ? 'green': 'red',
        highlightStrokeColor: success ? '#5ee667' : '#f23d3d',
        fillColor: success ? 'green' : 'red',
        highlightFillColor: success ? '#5ee667' : '#f23d3d',
        name: '',
        size: 2,
        fixed: true,
        visible: visible
    }
};
const oldDotsProperties = {
    strokeColor: '#506e3b',
    highlightStrokeColor: '#7aa15f',
    fillColor: '#506e3b',
    highlightFillColor: '#7aa15f',
    name: '',
    size: 2,
    fixed: true
};
points = [];

function initBoard() {
    return JXG.JSXGraph.initBoard('jxgbox', {boundingbox: [-5, 5, 5, -5], axis:true});
}

let board = initBoard();


function setVerticesInvisible(figure) {
    for (let i = 0; i < figure.vertices.length - 1; i++) {
        figure.vertices[i].setAttribute({visible: false});
    }
}

function wrapCoordinate(coordinate) {
    return (Math.round(coordinate*100)/100).toString()
}

function createPoint(x, y, visible = true, success = true) {
    return board.create('point', [x, y], pointProperties(visible, success));
}

function createTriangle(R) {
    let p1 = createPoint(0, 0);
    let p2 = createPoint(0, R/2);
    let p3 = createPoint(R/2, 0);
    let triangle = board.create('polygon', [p1, p2, p3], figuresProperties);
    setVerticesInvisible(triangle);
    return triangle;
}

function createRectangle(R) {
    let p1 = createPoint(0, 0);
    let p2 = createPoint(-R, 0);
    let p3 = createPoint(-R, -R);
    let p4 = createPoint(0, -R);
    let rectangle = board.create('polygon', [p1, p2, p3, p4], figuresProperties);
    setVerticesInvisible(rectangle);
    return rectangle;
}

function createCircle(R) {
    let p1 = createPoint(0, 0, false);
    let p2 = createPoint(0, R, false);
    let p3 = createPoint(-R, 0, false);
    return board.create('sector', [p1, p2, p3], figuresProperties);
}

let getMouseCoords = function(e, i) {
    let cPos = board.getCoordsTopLeftCorner(e, i),
        absPos = JXG.getPosition(e, i),
        dx = absPos[0]-cPos[0],
        dy = absPos[1]-cPos[1];
    return new JXG.Coords(JXG.COORDS_BY_SCREEN, [dx, dy], board);
}

let down = function(e) {
    if (e.button === 2 || e.target.className === 'JXG_navigation_button') {
        return;
    }
    let canCreate = true, i, coords, el;

    if (e[JXG.touchProperty]) {
        // index of the finger that is used to extract the coordinates
        i = 0;
    }
    coords = getMouseCoords(e, i);
    R = app.getRValue().trim();
    if (canCreate && R) {
        app.processRequest(wrapCoordinate(coords.usrCoords[1]), wrapCoordinate(coords.usrCoords[2]), R);
    }
}

function drawPointsByR(r) {
    console.log("draw points by r")
    console.log(points);
    points.forEach(point => {
        createPoint(point.x * parseFloat(r)/point.r, point.y* parseFloat(r)/point.r, true, point.result); //* parseFloat(r)/point.r
    })
}

function clearGraphDots() {
    // Recreate board
    board = initBoard();
}

function onChangeRValue() {
    let R = app.validateR(app.getRValue(), false);

    if (R) {
        console.log("Change R: " + R);

        clearGraphDots();

        let triangle = createTriangle(R),
            rect = createRectangle(R),
            circle = createCircle(R);

        drawPointsByR(R);
    }
}

board.on("down", down);
