function sendForm(board, points, x, y, r) {

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
			let x = data.x, y = data.y, r = data.r, hit = data.hit;
			let point = createPoint(board, x, y, hit);
			point.showElement();
			points[r].push(point);
			addInTable(convertToHtmlTable(data));
		},
		error: function(data) {
			alert(data);
		}
	});
}

function check_r() {
	var r = document.querySelector('option:checked');
	if (parseFloat(r.value)) return true;
	return false;
}
