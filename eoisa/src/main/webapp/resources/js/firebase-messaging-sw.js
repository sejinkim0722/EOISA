importScripts("https://www.gstatic.com/firebasejs/5.8.2/firebase-app.js");
importScripts("https://www.gstatic.com/firebasejs/5.8.2/firebase-messaging.js");

var config = {
	apiKey: "",
	projectId: "project-eoisa",
	messagingSenderId: "73767431531"
};
firebase.initializeApp(config);

const messaging = firebase.messaging();