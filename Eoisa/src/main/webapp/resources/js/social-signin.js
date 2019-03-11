// Social Signin Popup
$(document).ready(function() {
	window.name = "parent";
	$(document).on("click", "#social-signin-naver", function(event) {
		$.get("/naver_url", function(data){
    		window.open(data, "naver_signin", "width=400, height=750");
    		return false;
		});
	});
	
	$(document).on("click", "#social-signin-kakao", function(event) {
		window.open("https://kauth.kakao.com/oauth/authorize?client_id=&redirect_uri=https://eoisa.ml/kakaosignin&response_type=code", "kakao_signin", "width=400, height=700");
		return false;
	});
});