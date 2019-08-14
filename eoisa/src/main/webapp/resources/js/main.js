$(document).ready(function() {
	
	// Hide Page Loading Overlay
	$(".loading-overlay").fadeOut(100);
	
	// Ajax CSRF set
	$.ajaxSetup({
		headers : {
			"X-CSRF-TOKEN" : $('meta[name = "_csrf_token"]').attr("content")
		},
		timeout : 10000,
		cache : false
	});
	
    // Sticky Navbar
    $(function() {
        var stickyToggle = function(sticky, stickyWrapper, scrollElement) {
            var stickyHeight = sticky.outerHeight();
            var stickyTop = stickyWrapper.offset().top;
            if (scrollElement.scrollTop() >= stickyTop) {
                stickyWrapper.height(stickyHeight);
                sticky.addClass("is-sticky");
            } else {
                sticky.removeClass("is-sticky");
                stickyWrapper.height('auto');
            }
        };

        $("[data-toggle='sticky-onscroll']").each(function() {
            var sticky = $(this);
            var stickyWrapper = $("<div>").addClass("sticky-wrapper");
            sticky.before(stickyWrapper);
            sticky.addClass("sticky");

            $(window).on("scroll.sticky-onscroll resize.sticky-onscrol", function() {
                stickyToggle(sticky, stickyWrapper, $(this));
            });

            stickyToggle(sticky, stickyWrapper, $(window));
        });
    });
    
    // Sidenav(Mobile) btn
    $(function() {
        $(document).on("click", ".btn-sidenav-open", function() {
            $(".sidenav").css("left", "0vw");
            $(".sidenav-overlay").show();
        });

        $(document).on("click", ".btn-sidenav-close", function() {
            $(".sidenav").css("left", "-70vw");
            $(".sidenav-overlay").hide();
        });
        
        $(document).on("click", ".sidenav-overlay", function() {
            $(".sidenav").css("left", "-70vw");
            $(".sidenav-overlay").hide();
        });
    });
	
    // Follow Sidebar
    $(function() {
        var md = new MobileDetect(window.navigator.userAgent);
        if(md.mobile() == null && $(document).width() > 576){
    		$("#filter-body").addClass("show");
    		
            var $sidebar = $(".follow"),
                $window = $(window),
                offset = $sidebar.offset(),
                topPadding = 45;

            $window.scroll(function() {
                if ($window.scrollTop() > (offset.top + 500)) {
                    $sidebar.stop().animate({
                        marginTop: $window.scrollTop() - offset.top + topPadding
                    }, 750);
                } else {
                    $sidebar.stop().animate({
                        marginTop: 0
                    }, 750);
                }
            });
        }
    });
    
 	// Scrolltop btn
    $(function() {
        $(window).scroll(function() {
            if ($(this).scrollTop() > 600) {
                $("#scrolltop").fadeIn();
            } else {
                $("#scrolltop").fadeOut();
            }
        });

        $("#scrolltop").click(function() {
            $("html, body").animate({
                scrollTop: 0
            }, 500);
            return false;
        });
    });
    
    // Ticker
    $(function() {
        $(".ticker").easyTicker({
            direction: "up",
            easing: "swing",
            speed: "slow",
            interval: 2500,
            height: 24,
            visible: 1,
            mousePause: 1,
        });
        
        $("#ranking").hover(function() {
			$(".ticker").stop().animate({ height: "220px"}, 250);
        }, function() {
			$(".ticker").stop().animate({ height: "24px"}, 250);
        });
    });
	
    // Navbar Dropdown
    $(function() {
    	$(document).on("click", ".dropdown .dropdown-menu", function(event) {
    		event.stopPropagation();
    	});
    	
        $(".dropdown").on("show.bs.dropdown", function() {
            $(this).find(".dropdown-menu").first().stop(true, true).fadeIn("fast");
        });

        $(".dropdown").on("hide.bs.dropdown", function() {
            $(this).find(".dropdown-menu").first().stop(true, true).fadeOut("fast");
        });
    });

    // Mobile Topnav Active Effect
    $(function() {            	
        var url = $(location).attr("href");
        
    	if(!url.includes("rank") && !url.includes("theme")) {
    		$(".nav-item.home").addClass("active");
    	} else if(url.includes("rank")) {
        	$(".nav-item.rank").addClass("active");
    	} else if(url.includes("theme")) {
    		$(".nav-item.theme").addClass("active");
    	} else {
    		$("#left-sidebar").removeClass("component-desktop");
        }
    });
                     
    // Search
    $(function() {
    	$(document).on("click", "#div-search .fa-search", function() {
    		$("#form-search").submit();
    	});
    	
        $("#form-search").submit(function() {
            if($(this).children("input[name = keyword]").val().trim().length < 2) {
                alert("검색어는 최소 두 글자 이상이어야 합니다.");
                return false;
            } else {
            	$(this).attr("action", "/search/" + $("input[name = keyword]").val());
            }
        });
    });

    // Clipboard Copy
    $(function() {                
        var clipboard = new ClipboardJS(".btn-share");

		clipboard.on("success", function(e) {
			e.clearSelection();
			alert("URL이 클립보드에 복사되었습니다.");
        });

        clipboard.on("error", function(e) {
            alert("URL 복사 중 문제가 발생하였습니다.");
        });
    });
    
    // Wishlist
    $(function() {                
    	$(document).on("click", ".btn-wish", function() {
    		var dealno = $(this).data("dealno");
    		var username = $("input[name = 'username']").val();
    		if(username == null) return false;
    		var params = { username: username, dealno: dealno };
    		
    		$.ajax({
    			type: "POST",
    			url: "/wishlist",
    			data: params,
    			success: function(result) {
    				if(result == "full") {
    					alert("최대 10개의 핫딜만 찜 목록에 추가할 수 있습니다.");
    					return false;
    				} else {
    					$('.btn-wish[data-dealno = "' + dealno + '"]').children().toggleClass("fas");
    				}
    			},
    			error: function() {
    				alert("찜 목록 추가 중 문제가 발생하였습니다.\n다시 시도해 주세요.");
    				return false;
    			}
    		});
    	});
    });
    
    // Modify Userinfo
    $(function() {
    	var nameCheck = true;
    	var passCheck = true;
    	
    	// Nickname Duplicate Check Button
    	$(document).on("change keyup paste", "#input-nickname", function() {
    		if($("#input-nickname").val() == $("input[name = 'nickname'").val()) {
    			$("#btn-namecheck").prop("disabled", true);
    			nameCheck = true;
    		} else if($("#input-nickname").val().trim() == "") { 
    			$("#btn-namecheck").prop("disabled", true);
    			nameCheck = false;
    		} else {
    			$("#btn-namecheck").prop("disabled", false);
    			nameCheck = false;
    		}
    	});
    	
    	// Nickname Duplicate Check
	    $(document).on("click", "#btn-namecheck", function() {
	    	var nickname = $("#input-nickname").val();
	    	$.post("/nickname_check", { nickname: nickname }, function(data) {
	    		if(data == "duplicated") {
	    			$(".message-namecheck").css("color", "#ff5a5f");
	    			$(".message-namecheck").html("이미 존재하는 닉네임입니다.");
	    			nameCheck = false;
	    		} else {
	    			$(".message-namecheck").css("color", "#8ce071");
	    			$(".message-namecheck").html("사용 가능한 닉네임입니다.");
	    			nameCheck = true;
	    		}
	    	});
	    });
	    
	    // Password Check
	    const passRegex = new RegExp("^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{4,}$");
    	$(document).on("change keyup paste", "#input-password", function() {
    		var password = $("#input-password").val();
    		if(passRegex.test(password)) {
    			$(".message-passwordcheck").css("color", "#8ce071").html("사용 가능한 비밀번호입니다.");
    			passCheck = true;
    		} else if(!passRegex.test(password) && password == "") {
    			$(".message-passwordcheck").html("");
    			passCheck = true;
    		} else {
    			$(".message-passwordcheck").css("color", "#ff5a5f").html("잘못된 비밀번호 형식입니다.");
    			passCheck = false;
    		}
    	});
    	
    	// Profile Image Upload
    	var profileCheck = false;
    	var uploadedFilePath = "";
    	var blob;
    	$(document).on("change", "#input-profile", function(event) {
    		var file = event.target.files[0];
    		console.log(file.type);
    		if(file.size > 5242880) {
    			alert("최대 5MB의 이미지만 허용됩니다. 다시 선택해 주세요.");
    			$("#input-profile").val("");
    			return false;
    		} else if(!file.type.match("image/jpeg|image/png")) {
    			alert("업로드할 수 없는 파일 형식입니다. 다시 선택해 주세요.");
    			$("#input-profile").val("");
    			return false;
    		} else {
    			var reader = new FileReader();
    			reader.onload = function(event) {
    				// Image Resizing
    				var image = new Image();
    				image.onload = function() {
    					var canvas = document.createElement("canvas");
    					var ctx = canvas.getContext("2d");
    					ctx.drawImage(image, 0, 0);

    					const MAX_WIDTH = 500;
    					const MAX_HEIGHT = 500;
    					var width = image.width;
    					var height = image.height;
    					if (width > height) {
    						if (width > MAX_WIDTH) {
    							height *= MAX_WIDTH / width;
    							width = MAX_WIDTH;
    						}
    					} else {
    						if (height > MAX_HEIGHT) {
    							width *= MAX_HEIGHT / height;
    							height = MAX_HEIGHT;
    						}
    					}
    					canvas.width = width;
    					canvas.height = height;
    					var ctx = canvas.getContext("2d");
    					ctx.drawImage(image, 0, 0, width, height);
                        
    					dataURL = canvas.toDataURL(file.type, 0.5);
    					$(".div-profile #profile-img").attr("src", dataURL);
    					blob = dataURItoBlob(dataURL);

    					profileCheck = true;
    				}
    				image.src = event.target.result;
    			}
    			reader.readAsDataURL(file);
    		}
    		
    		function dataURItoBlob(dataURI) {
    			var byteString;
    			if(dataURI.split(",")[0].indexOf("base64") >= 0) {
    				byteString = atob(dataURI.split(",")[1]);
    			} else {
    				byteString = unescape(dataURI.split(",")[1]);
    			}
    			var mimeString = dataURI.split(",")[0].split(":")[1].split(";")[0];

    			var ia = new Uint8Array(byteString.length);
    			for(var i=0; i<byteString.length; i++) {
    				ia[i] = byteString.charCodeAt(i);
    			}

    			return new Blob([ia], { type:mimeString });
    		}
    	});
    
    	$(document).on("click", ".div-profile button", function(event) {
    		$(".div-profile button").prop("disabled", true);
    		if(profileCheck) {
    			var formData = new FormData($("#form-profile")[0]);
    			formData.set("profile_pic", blob);
    			$.ajax({
    				url: "/profile_upload",
    				type: "POST",
    				data: formData,
    				processData: false,
    				contentType: false,
    				success: function(data) {
    					uploadedFilePath = data;
    					alert("프로필 사진이 업로드되었습니다.");
    				},
    				error: function(err) {
    					console.log(err);
    					alert("파일 첨부 중 문제가 발생하였습니다.\n다시 시도해 주세요.");
    					$(".div-profile button").prop("disabled", false);
    					return false;
    				}
    			});
    		} else {
    			alert("업로드된 사진이 없습니다.");
    			$(".div-profile button").prop("disabled", false);
    			return false;
    		}
    	});
    	
    	// Modify Submit
    	$(document).on("click", "#btn-complete", function(event) {
    		if(nameCheck && passCheck) {
    			var params = { 
    				username: $("input[name = 'username']").val(), 
    				nickname: $("#input-nickname").val(), 
    				password: $("#input-password").val(),
    				profile_pic: uploadedFilePath
    			};
    			modify(params);
    		} else if(nameCheck == false) {
    			alert("닉네임 중복 확인 여부를 확인해 주세요.");
    		} else if(passCheck == false) {
    			alert("비밀번호 형식을 확인해 주세요.");
    		}
    	});
    	
    	function modify(params) {
    		$.post("/modify", params, function(result) {
    			if(result == "success") {
    				alert("회원 정보가 정상적으로 수정되었습니다.\n변경 사항은 다음 로그인부터 적용됩니다.");
    			} else if(result == "fail") {
    				alert("회원 정보 수정에 실패하였습니다.\n다시 시도해 주세요.");
    				location.reload();
    			}
    			$("#modal-modify-userinfo").modal("hide");
    		}).fail(function() {
    			alert("회원 정보 수정 중 문제가 발생하였습니다.\n다시 시도해 주세요.");
    			location.reload();
    		});
    	};
	});
    
	// Ajax Infinite Scroll
	var path = window.location.href + "/";
	var param = "";
	var lastIndex = $(".total-page").data("value");
	var initIS = function() {
		$("#main-content-wrapper").infiniteScroll({
			path: function() {
				if(this.loadCount < (lastIndex - 1)) {
					var nextIndex = this.loadCount + 2;
					return path + nextIndex + param;
				}
			},
			append: "#main-content",
			checkLastPage: true,
			history: false
		});
	}
	initIS();
   	
   	$("#main-content-wrapper").on("request.infiniteScroll", function(event) {
   		$(".loading-indicator").show();
   	});
   	
   	$("#main-content-wrapper").on("load.infiniteScroll", function(event) {
   		$(".loading-indicator").hide();
   	});
    
    // Filtering Search
    $(function() {
        var scanFilterList = function() {
        	var selected = { regions:[], sites:[], shops:[], isended:[] };
	        $("ul.filter-menu li.filter-list").each(function() {
	            var key = $(this).data("key");
	            var value = $(this).data("value");
	
	            if(key == "region" && (selected.regions.indexOf(value) < 0) && $(this).hasClass("active")) {
	            	selected.regions.push(value);
	            } else if(key == "site" && (selected.sites.indexOf(value) < 0) && !$(this).hasClass("active")) {
	            	selected.sites.push(value);
	            } else if(key == "shop" && (selected.shops.indexOf(value) < 0) && !$(this).hasClass("active")) {
	            	selected.shops.push(value);
	            } else if(key == "isended" && (selected.isended.indexOf(value) < 0) && !$(this).hasClass("active")) {
	            	selected.isended.push(value);
	            }
	        });
		    return selected;
        }
        
        var minCheck = function() {
        	if($("li.filter-list.active[data-key = region]").length < 1) {
        		alert("지역 필터 조건은 최소 하나가 선택되어야 합니다.");
        		return false;
        	}
        }
        
        $.ajaxSettings.traditional = true;
        $(".filter-list").click(function() {
            $(this).toggleClass("active");
        	if(minCheck() == false) {
        		$(this).toggleClass("active");
        		return false;
        	}
            var params = scanFilterList();
            $.ajax({
            	type: "GET",
            	url: "/filter/1",
            	data: { region: params.regions, site: params.sites, shop: params.shops, isended: params.isended },
            	dataType: "html",
            	success: function(data) {
            		$("#main-content-wrapper").infiniteScroll("destroy");
            		$("#main-content").remove();
            		$(document).scrollTop(0);
            		$("#main-content-wrapper").html(data);
        			path = this.url.substr(0, this.url.indexOf("?") - 1);
            		if($("li.filter-list.active").length == 20) {
            			param = "";
            			$(".current-page-info").hide();
            		} else {
            			param = this.url.substr(this.url.indexOf("?"));
            		}
            		lastIndex = $(".total-page").data("value");
            		initIS(); // Reinitialize Infinite Scroll
            	},
            	error: function() {
            		alert("페이지 로드 중 문제가 발생하였습니다.\n다시 시도해 주세요.");
            		return false;
            	}
            });
        });
    });
    
    $(document).on("click", "#filter button", function() {
    	$(this).children().toggleClass("fa-angle-down fa-angle-up");
    });
});