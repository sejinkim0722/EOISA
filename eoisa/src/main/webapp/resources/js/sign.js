$(document).ready(function() {
	
	// Ajax CSRF set
	$.ajaxSetup({
		headers : { "X-CSRF-TOKEN": $('meta[name = "_csrf_token"]').attr("content") },
		timeout: 10000,
		cache: false
	});
	
	// Hide Message Box
	$(".message-box").delay(3000).fadeOut("slow");
	
	// Init Popover
	$("input").popover({
		container: "body",
		placement: "top",
		viewport: { selector: ".container" }
	});
	
	// Hide Popover
	$("input").focusout(function() {
		$(this).popover("hide");
	});

	// Toggle Sign form
	$(function() {
	    $(".message a").click(function(){
			$(".register-form").animate({ height: "toggle", opacity: "toggle" }, "slow");
			$(".signin-form").fadeToggle();
	    });
	 
		$(".find a").click(function(){
			$(".findpw-form").animate({ height: "toggle", opacity: "toggle" }, "slow");
			$(".signin-form").fadeToggle();
		});
	});
	
	var emailCheck = false;
	var nameCheck = false;
	var passCheck = false;
	
	const emailRegex = new RegExp("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+(?:[A-Z]{2}|com|org|net|gov|mil|biz|info|ac.kr|name|aero|jobs|museum)");
	const passRegex = new RegExp("^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{4,}$");
    
	// Email(username) Check
	$(function() {
		$(document).on("change keyup paste", "#input-signup-email", function() {
    		var email = $("#input-signup-email").val();
    		if(emailRegex.test(email)) {
    			$("#input-signup-email").css("border", "2px solid seagreen");
    			emailCheck = true;
    		} else {
    			$("#input-signup-email").css("border", "2px solid crimson");
    			emailCheck = false;
    		}
		});
	});
	
	// Nickname Check
	$(function() {
    	$(document).on("change keyup paste", "#input-signup-nickname", function() {
			if($("#input-signup-nickname").val().trim().length >= 2) {
				$("#input-signup-nickname").popover("hide").css("border", "2px solid seagreen");
	    		nameCheck = true;
			} else {
				$("#input-signup-nickname").popover("show").css("border", "2px solid crimson");
				nameCheck = false;
			}
    	});
	});
	
    // Password Check
	$(function() {
		$(document).on("change keyup paste", "#input-signup-password", function() {
			var password = $("#input-signup-password").val();
			if(passRegex.test(password)) {
				$("#input-signup-password").popover("hide").css("border", "2px solid seagreen");
				passCheck = true;
			} else {
				$("#input-signup-password").popover("show").css("border", "2px solid crimson");
				passCheck = false;
			}
		});
	});
	
	// Signin Submit
	$(function() {
		$(document).on("click", "#btn-signin", function(event) {
			if(emailRegex.test($("#input-signin-email").val()) && passRegex.test($("#input-signin-password").val())) {
				$(".signin-form").submit();
			} else {
				alert("이메일 주소와 비밀번호를 다시 확인해 주세요.");
				return false;
			}
		});
	});
    
	// Signup Submit
	$(function() {
    	$(document).on("click", "#btn-signup", function(event) {
    		if(emailCheck && nameCheck && passCheck) {
    			$("#btn-signup").prop("disabled", true);
    			$.post("/nickname_check", { nickname: $("#input-signup-nickname").val() }, function(data) {
    				if(data == "duplicated") {
    					alert("중복된 닉네임이 존재합니다. 다른 닉네임을 사용해 주세요.");
    					$("#btn-signup").prop("disabled", false);
    					return false;
    				} else {
    					$("#btn-signup").text("잠시만 기다려 주세요 …");
    					var params = { 
    						username: $("#input-signup-email").val(),
    						nickname: $("#input-signup-nickname").val(),
    						password: $("#input-signup-password").val(),
    						platform: $("#input-signup-platform").val() 
    					};
    					$.post("/signup", params, function() {
    						location.href="/sign/success";
    					}).fail(function() {
    						alert("회원가입 처리 중 문제가 발생하였습니다.\n다시 시도해 주세요.");
    						location.reload();
    						return false;
    					});
    				}
    			});
    		} else if(emailCheck == false) {
    			alert("이메일 주소를 확인해 주세요.");
    			return false;
    		} else if(nameCheck == false) {
    			alert("닉네임을 확인해 주세요.");
    			return false;
    		} else if(passCheck == false) {
    			alert("비밀번호를 확인해 주세요.");
    			return false;
    		}
    	});
	});
	
	// Find Password
	$(function() {
    	$(document).on("click", "#btn-findpw", function(event) {
    		if(emailRegex.test($("#input-findpw-email").val())) {
        		$("#btn-findpw").prop("disabled", true).text("잠시만 기다려 주세요 …");
        		$(".findpw-form").submit();
    		} else {
    			alert("올바른 이메일 주소 형식이 아닙니다.");
    			return false;
    		}
    	});
	});
});