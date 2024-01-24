import JXGBoard from 'jsxgraph-react-js'
import {useDispatch, useSelector} from "react-redux";
import {useState} from "react";

import {checkPoint} from "../../service/Service";

import "../../resources/Graph.css"
import {addHit} from "../../store/userSlice";
import {showError} from "../../store/errorSlice";

const drawnObjects = [];
let lastClickProcess = null;


const Graph = () => {
    const dispatch = useDispatch();

    const userInfo = useSelector(state => state.user);
    const [board, setBoard] = useState(null);

    const figuresProperties = {
        strokeColor: '#4b61cf',
        highlightStrokeColor: '#5a73ed',
        fillColor: '#4b61cf',
        highlightFillColor: '#5a73ed',
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

    const createPoint = (x, y, visible = true, success = true) => {
        return board.create('point', [x, y], pointProperties(visible, success));
    }
    const setVerticesInvisible = (figure) => {
        for (let i = 0; i < figure.vertices.length - 1; i++) {
            figure.vertices[i].setAttribute({visible: false});
        }
    }
    const wrapCoordinate = (coordinate) => {
        return Math.round(coordinate*100)/100
    }

    const createTriangle = (R) => {
        let p1 = createPoint(0, 0, false);
        let p2 = createPoint(0, R, false);
        let p3 = createPoint(R, 0, false);
        let triangle = board.create('polygon', [p1, p2, p3], figuresProperties);
        setVerticesInvisible(triangle);

        drawnObjects.push(triangle);
        return triangle;
    }

    const createRectangle = (R) => {
        let p1 = createPoint(0, 0, false);
        let p2 = createPoint(R, 0, false);
        let p3 = createPoint(R, -R, false);
        let p4 = createPoint(0, -R, false);
        let rectangle = board.create('polygon', [p1, p2, p3, p4], figuresProperties);
        setVerticesInvisible(rectangle);

        drawnObjects.push(rectangle);
        return rectangle;
    }

    const createCircle = (R) => {
        let p1 = createPoint(0, 0, false);
        let p2 = createPoint(-R, 0, false);
        let p3 = createPoint(0, -R, false);
        let circle = board.create('sector', [p1, p2, p3], figuresProperties);

        drawnObjects.push(circle);
        return circle;
    }

    const drawPointsByR = (R) => {
        console.log("Draw by r: " + R);

        userInfo.hits.forEach(point => {
            drawnObjects.push(
                createPoint(point.x * parseFloat(R)/point.r, point.y * parseFloat(R)/point.r, true, point.hit)
            );
        })
    }

    const createArea = () => {
        // Update listeners
        board.off('down', lastClickProcess);
        board.on('down', clickProcess);
        lastClickProcess = clickProcess;
        // Clear board
        board.removeObject(drawnObjects);

        createTriangle(userInfo.r);
        createRectangle(userInfo.r);
        createCircle(userInfo.r);
        drawPointsByR(userInfo.r);
    }

    const clickProcess = async (e) => {
        if (e.button === 2 || e.target.tagName === 'SPAN') return;

        const coords = board.getUsrCoordsOfMouse(e);
        const hit = {x: wrapCoordinate(coords[0]), y: wrapCoordinate(coords[1]), r: userInfo.r}

        const response = await checkPoint(hit);

        if (response.message) {
            dispatch(showError({ detail: response.message }))
            return;
        }

        dispatch(addHit(response));
    };

    const logicJS = (brd) => {
        console.log("Set board")
        setBoard(brd);
    }

    if (board) createArea(board);

    return (
        <div className="graphBlock col-md">
            <JXGBoard
                logic={logicJS}
                boardAttributes={{boundingbox: [-4, 4, 4, -4], axis: true}}
                style={{
                    width: "400px",
                    height: "400px"
                }}
            />
        </div>
    );
}


export default Graph;
