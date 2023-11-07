function sendForm(points, x, y, r) {

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
			console.log(data);
			if(data.hit === "true"){
				points.push([data.x, data.y, data.r]);
			}
			addInTable(convertToHtmlTable(data));
		},
		error: function(data) {
			alert(data);
		}
	});
}

