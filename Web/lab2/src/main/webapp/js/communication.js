function sendForm(x, y, r) {

	$.ajax({
		url: 'ControllerServlet',
		type: 'GET',
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
		type: "POST",
		url: "ControllerServlet",
		data: {"clean": 'true'},
		success: function (response) {
			resultsTable.innerHTML = '';
			pointsContainer=[];
			redrawGraph();
		},
		error: function (response) {
			console.log("ERROR:" ,response);
			resultsTable.innerHTML = '';
			pointsContainer=[];
			alert(response);
		}
	});

}


