function getTableBySession(){
	$.ajax({
		type: 'GET',
		url: 'ControllerServlet',
		data: {'getTable' : 'true'},
		success: function(data) {
			console.log("got from server:");
			console.log(data);
			initSessionTable(data);
		},
		error: function(data) {
			alert(data);
		}
	});
}

function sendForm(x, y, r) {
	$.ajax({
		type: 'POST',
		url: 'ControllerServlet',
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
			console.log("KURWA post req")
		}
	});
}

function clean_table() {
	/*$.ajax({
		type: "DELETE",
		url: "ControllerServlet?clean=true",
		data: null,
		success: function (response) {
			resultsTable.innerHTML = '';
			pointsContainer=[];
			//location.reload();
			redrawGraph();
		},
		error: function (response) {
			alert(response);
		}
	});*/
	fetch('http://localhost:8080/web_lab_2-1.0-SNAPSHOT/ControllerServlet?clean=true', {method:"DELETE"})
		.then(data => {
			console.log(data);
			resultsTable.innerHTML = '';
			pointsContainer=[];
			//location.reload();
			redrawGraph();
		})
		.catch(error => {
			// Handle any errors
			console.error(error);
		});
}



function initSessionTable(data){
	if(data === null || data.length === null){
		return;
	}
	data.forEach(point => {
		addInTable(point);
	});
	initialize_table(pointsContainer);
}
