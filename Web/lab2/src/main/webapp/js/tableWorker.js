function initialize_table(points) {
    for (let row of resultsTable.rows) {
        var x = parseFloat(row.cells.item(0).innerText);
        var y = parseFloat(row.cells.item(1).innerText);
        var r = parseInt(row.cells.item(2).innerText);
        var hit = row.cells.item(5).innerText === 'true';
        points.push([x,y,r]);
    }
}

function clean_table() {
    $.ajax({
        type: "POST",
        url: "ControllerServlet",
        data: {"clean": 'true'},
        success: function (response) {
            resultsTable.innerHTML = '';
        },
        error: function (response) {
            alert(response);
        }
    });
    pointsContainer=[];
}

function addInTable(data) {
    let row = resultsTable.insertRow(0);
    row.insertCell().innerHTML = data.x;
    row.insertCell().innerHTML = data.y;
    row.insertCell().innerHTML = data.r;
    row.insertCell().innerHTML = data.clientDate;
    row.insertCell().innerHTML = data.scriptWorkingTime;
    row.insertCell().innerHTML = data.hit;
}
// function addInTable(data) {
//     resultsTable.append(data);
// }

function convertToHtmlTable(data) {
    return "<tr>" +
        "<td>" + data.x + "</td>" +
        "<td>" + data.y + "</td>" +
        "<td>" + data.r + "</td>" +
        "<td>" + data.clientDate + "</td>" +
        "<td>" + data.scriptWorkingTime + " ms</td>" +
        "<td>" + data.hit + "</td>" +
        "</tr>";
}