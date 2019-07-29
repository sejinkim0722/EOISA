$(document).ready(function() {
	// Page Loading Overlay
	$(".loading-overlay").fadeOut(100);
	
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

		$('[data-toggle="sticky-onscroll"]').each(function() {
			var sticky = $(this);
			var stickyWrapper = $('<div>').addClass('sticky-wrapper');
			sticky.before(stickyWrapper);
			sticky.addClass('sticky');

			$(window).on('scroll.sticky-onscroll resize.sticky-onscroll', function() {
				stickyToggle(sticky, stickyWrapper, $(this));
			});

			stickyToggle(sticky, stickyWrapper, $(window));
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
				return true;
			}
		});
	});
    
	// Quick Link
	$(function() {
		var link = $('#navbar a.dot');

		link.on('click', function(e) {
			var target = $($(this).attr('href'));
			$('html, body').animate({
				scrollTop: target.offset().top - 45
			}, 700);
		});
	});


	$(window).scroll(function() {
		var bottom_of_object = $("#gr-1").offset().top + $("#gr-1").outerHeight() - 300;
		var bottom_of_object2 = $("#gr-2").offset().top + $("#gr-2").outerHeight() - 300;
		var bottom_of_object3 = $("#gr-3").offset().top + $("#gr-3").outerHeight() - 300;
		var bottom_of_object4 = $("#gr-4").offset().top + $("#gr-4").outerHeight() - 300;
		var bottom_of_object5 = $("#gr-5").offset().top + $("#gr-5").outerHeight() - 300;
		var bottom_of_object6 = $("#gr-6").offset().top + $("#gr-6").outerHeight() - 300;
		var bottom_of_window = $(window).scrollTop() + $(window).height();
        
		if(bottom_of_window > bottom_of_object*0.5){
			$('#border').animate({'opacity':'1'}, 1200);
		}
		if(bottom_of_window > bottom_of_object2){
			$('#border2').animate({'opacity':'1'}, 1200);
		}
		if(bottom_of_window > bottom_of_object3){
			$('#border3').animate({'opacity':'1'}, 1200);
		}
		if(bottom_of_window > bottom_of_object4){
			$('#border4').animate({'opacity':'1'}, 1200);
		}
		if(bottom_of_window > bottom_of_object5){
			$('#border5').animate({'opacity':'1'}, 1200);
		}
		if(bottom_of_window > bottom_of_object6){
			$('#border6').animate({'opacity':'1'}, 1200);
		}
	});
});