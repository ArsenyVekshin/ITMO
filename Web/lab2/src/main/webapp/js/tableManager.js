function clean_table() {
    $.ajax({
        type: "POST",
        url: "ControllerServlet",
        data: {"clean": 'true'},
        success: function (response) {
            let tBody = document.querySelector('#results-content');
            tBody.innerHTML = '';
        },
        error: function (response) {
            alert(response);
        }
    });
}

function addInTable(data) {
    $('#results-content').append(data);
}

function map2html(data){
    return "<tr>" +
        "<td>" + data.x + "</td>" +
        "<td>" + data.y + "</td>" +
        "<td>" + data.r + "</td>" +
        "<td>" + data.clientDate + "</td>" +
        "<td>" + data.scriptWorkingTime + " ms</td>" +
        "<td>" + data.result + "</td>" +
        "</tr>";
}

