function sendForm(x, y, r) {

	$.ajax({
		url: 'ControllerServlet',
		type: 'POST',
		data: {
			'x': x,
			'y': y,
			'r': r,
			'offset': new Date().getTimezoneOffset()
		},
		success: function(data) {
			pointsContainer.push([data.x, data.y, data.r, data.hit]);
			addInTable(data);
			redrawGraph();
		},
		error: function(data) {
			alert(data);
		}
	});
}

function clean_table() {
	$.ajax({
		type: "DELETE",
		url: "ControllerServlet",
		data: {"clean": 'true'},
		success: function (response) {
			resultsTable.innerHTML = '';
			pointsContainer=[];
			redrawGraph();
		},
		error: function (response) {
			alert(response);
		}
	});

}


