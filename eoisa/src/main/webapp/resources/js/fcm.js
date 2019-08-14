$(document).ready(function() {
	// Initialize Firebase
	var config = {
		apiKey: "",
		projectId: "project-eoisa",
		messagingSenderId: "73767431531"
	};
	firebase.initializeApp(config);
	
	const messaging = firebase.messaging();
	
	navigator.serviceWorker.register("/resources/js/firebase-messaging-sw.js")
	.then((registration) => {
		messaging.useServiceWorker(registration);
	})
	
	messaging.onMessage(function(payload) {
		console.log("Message : ", payload);
		var options = {
			body: payload.notification.body,
			icon: "/resources/assets/favicon.png"
		}
		var notification = new Notification(payload.notification.title, options);
		
		notification.onclick = function(event) {
			location.reload();
		}
	});

	$(document).on("change", ".toggle input[type = 'checkbox']", function() {
		if(localStorage.notify == "off" || localStorage.notify == null) {
			messaging.requestPermission()
			.then(function() {
				// Permission Granted
				localStorage.notify = "on";
				$(".toggle.btn").removeClass("btn-light off").addClass("btn-danger");
				alert("핫딜 알림 기능이 활성화되었습니다.");
				return messaging.getToken();
			})
			.then(function(token) {
				$.post("/fcm/token", { 
					request: "subscribe", 
					token: token 
				});
			})
			.catch(function(err) {
				// Permission Denied
				localStorage.notify = "off";
				$(".toggle.btn").removeClass("btn-danger").addClass("btn-light off");
				alert("알림을 거부하셨습니다.\n이후 다시 핫딜 알림을 활성화하시려면,\n브라우저의 알림 설정을 직접 변경하셔야 합니다.");
			});
		} else if(localStorage.notify == "on") {
			localStorage.notify = "off";
			$(".toggle.btn").removeClass("btn-danger").addClass("btn-light off");
			messaging.getToken().then(function(currentToken) {
				$.post("/fcm/token", { 
					request: "unsubscribe",
					token: currentToken
				});
			});
		}
	});

	if(localStorage.notify == "on") {
		// Push Notification State ON
		$(".toggle.btn").removeClass("btn-light off").addClass("btn-danger");
	} else if(localStorage.notify == "off") {
		// Push Notification State OFF
		$(".toggle.btn").removeClass("btn-danger").addClass("btn-light off");
	}
});