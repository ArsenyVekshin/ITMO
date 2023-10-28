function validate_values(x, y, r) {
	[_x, _y, _r] = validateAndParse(x, y, r);
	return _x!==null && _y!==null && _r!==null;
}

function validateAndParse(x, y, r) {
	let parsedX, parsedY, parsedR;

	parsedX = parseInt(x);
	if (isNaN(parsedX)) {
		alert("Please input correct X value");
		return [null,null,null];
	}

	parsedY = parseFloat(y);
	if (isNaN(parsedY)) {
		alert("Please input correct Y value");
		return [null,null,null];
	}

	parsedR = parseInt(r);
	if (isNaN(parsedX)) {
		alert("Please input correct R value");
		return [null,null,null];
	}

	console.log(x, y, r, "->", parsedX, parsedY, parsedR);
	return [parsedX, parsedY, parsedR]

}