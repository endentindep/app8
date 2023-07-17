(async function () {
	let response = await fetch("http://localhost:8080/getUserData")
		.then(response => response.text())
		.then(text => document.querySelector(".list h1").innerHTML = text);
})()