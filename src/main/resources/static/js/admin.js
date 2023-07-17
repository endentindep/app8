function renderUsers(data) {
	let element = document.querySelector(".list h1");
	while (element.hasChildNodes()) {
		element.removeChild(element.firstChild)
	}
	for (const i in data) {
		let el = document.createElement("div");
		el.append(data[i]);
		element.append(el);
	}
}

fetch("http://localhost:8080/getUsersData")
	.then(response => response.json())
	.then(json => renderUsers(json));


document.querySelector(".one").addEventListener("submit", (e) => {
	e.preventDefault();
	let data = {
		name: e.target.querySelector("#name").value,
		password: e.target.querySelector("#password").value,
		role: e.target.querySelector("#role").value,
	}
	fetch("http://localhost:8080/admin", {
		method: "POST",
		headers: {
			"Content-Type": "application/json;charset=utf-8"
		},
		body: JSON.stringify(data)
	})
		.then(response => response.json())
		.then(json => renderUsers(json))
})

document.querySelector(".two").addEventListener("submit", (e) => {
	e.preventDefault();
	let data = {
		id: e.target.querySelector("#id").value,
	}
	fetch("http://localhost:8080/admin", {
		method: "POST",
		headers: {
			"Content-Type": "application/json;charset=utf-8"
		},
		body: JSON.stringify(data)
	})
		.then(response => response.json())
		.then(json => renderUsers(json))
})

document.querySelector(".three").addEventListener("submit", (e) => {
	e.preventDefault();
	let data = {
		id: e.target.querySelector("#id").value,
		name: e.target.querySelector("#name").value,
		password: e.target.querySelector("#password").value,
		role: e.target.querySelector("#role").value,
	}
	fetch("http://localhost:8080/admin", {
		method: "POST",
		headers: {
			"Content-Type": "application/json;charset=utf-8"
		},
		body: JSON.stringify(data)
	})
		.then(response => response.json())
		.then(json => renderUsers(json))
})