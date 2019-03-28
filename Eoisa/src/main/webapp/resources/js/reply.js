// REST Ajax Reply
$(document).ready(function() { 	
	
	// Show List
	$(document).on("click", ".btn-toggle", function() {
		var dealno = $(this).data("dealno");
		showList(dealno);
		$("div.reply-box-" + dealno).delay(250).slideToggle();
	});
	
	function showList(dealno) {
		var ul = $(document).find(".reply-list-" + dealno);
		replyService.list({ dealno: dealno }, function(data) {
			str = "";
			if(data == null || data.length == 0) {
				ul.html('<div style="text-align: center;"><p class="text-black-50">댓글이 없습니다.</p></div>');
				return;
			}
			for(var i=0, len=data.length || 0; i<len; i++) {
				var reply = "reply";
				if(data[i].depth == 1) reply = "re-reply";
				var username = $("input[name = 'username']").val();
				var modal = 'data-toggle=\"modal\" data-target=\"#modal-signin\"';
				if(username != null) modal = '';
				if(data[i].profile_pic == null) data[i].profile_pic = '/resources/assets/profile.png';
				
				str += '<li class=\"' + reply + '\" data-replyno=' + data[i].replyno + '>';
				str += '	<div class="reply-thumbnail"><img src="' + data[i].profile_pic + '" class="rounded-circle"></div>';
				str += '	<div class="reply-body">';
				str += '		<div class="reply-content">';
				str += '			<p><strong>' + data[i].nickname + '</strong> <small class="text-black-50">' + data[i].writedate + '</small></p>';
				str += '			<p class="text-dark">' + data[i].content + '</p>';
				str += '		</div>';
				str += '	</div>';
				str += '	<div class="reply-opinion">';
				str += '		<p>';
				if(data[i].depth == 0) str += '<button type="button" class="btn btn-xs btn-re-reply" data-toggle="collapse" data-target=\".reply-reinput[data-replyno=\'' + data[i].replyno + '\']\"><small class="text-muted"><i class="fas fa-reply"></i>대댓글　</small></button>';
				str += '<button type="button" class="btn btn-xs btn-likeit-reply" ' + modal + ' data-replyno=\'' + data[i].replyno + '\' data-dealno=\'' + data[i].dealno + '\'><small class="text-muted"><i class="far fa-thumbs-up"></i>좋아요　</small></button><span class="badge badge-pill badge-success">' + data[i].likeitcount + '</span>';
				if(username == data[i].username) str += '<button type="button" class="btn btn-xs btn-delete-reply" data-replyno=\'' + data[i].replyno + '\' data-dealno=\'' + data[i].dealno + '\'><small class="text-muted"><i class="fas fa-eraser"></i>삭제</small></button>';
				str += '		</p>';
				str += '	</div>';
				str += '	<div class="reply-reinput collapse" data-replyno=\"' + data[i].replyno + '\">';
				str += '		<form data-replyno=\"' + data[i].replyno + '\" data-dealno=\"' + data[i].dealno + '\"><textarea name="re-reply" rows="1" maxlength="500" spellcheck="false" required ' + modal + '></textarea></form>';
				if(username != null) str += '<button class="btn btn-submit">확인</button>';
				str += '	</div>';
				str += '</li>';
				if(i < len - 1) str += '<hr>';
			}
			ul.html(str);
		});
	}
	
	// Delete
	$(document).on("click", ".btn-delete-reply", function() {
		var replyno = $(this).data("replyno");
		var dealno = $(this).data("dealno");
		if(confirm("정말로 댓글을 삭제하시겠습니까?")) {
			replyService.remove(replyno);
			setTimeout(function() { 
				showList(dealno) 
			}, 250);
		} else {
			return false;
		}
	});
	
	// Submit
	$(document).on("click", ".btn-submit", function() {		
		var content = $(this).prev().find("textarea").val();
		if(content.trim() == '') {
			alert("댓글 내용을 입력해 주세요."); 
			return false;
		}
		if(confirm("정말로 댓글을 작성하시겠습니까?") == false) return false;
		
		var username = $("input[name = 'username']").val();
		var nickname = $("input[name = 'nickname']").val();
		var profile_pic = $("input[name = 'profile_pic']").val();
		var dealno = $(this).prev().data("dealno");
		var ref = $(this).prev().data("replyno");
		var depth = 0;
		if(ref != null) depth = 1;
		var reply = { dealno: dealno, username: username, nickname: nickname, profile_pic: profile_pic, content: content, ref: ref, depth: depth };
		
		replyService.insert(reply, function() {
			$("textarea").val("");
			setTimeout(function() { 
				showList(dealno); 
				$("div.reply-box-" + dealno).show(); 
			}, 250);
		});
	});
	
	// Likeit
	$(document).on("click", ".btn-likeit-reply", function() {	
		var username = $("input[name = 'username']").val();
		var replyno = $(this).data("replyno");
		var dealno = $(this).data("dealno");
		var param = { username: username, replyno: replyno };
		
		if(username == null) return false;
		replyService.likeit(param, function(result) {
			if(result == "checked") {
				alert("이미 좋아요한 댓글입니다."); 
				return false;
			} else {
				setTimeout(function() { 
					showList(dealno) 
				}, 250);
			}
		});
	});
	
	// Services
	var replyService = (function() {
		function insert(reply, callback, error) {
			$.ajax({
				type: "POST",
				url: "/replies/new",
				data: JSON.stringify(reply),
				contentType: "application/json;charset=utf-8",
				success: function(result, status, xhr) {
					if(callback) {
						callback(result);
					}
				},
				error: function(status, xhr, err) {
					if(error) {
						error(err);
					} 
				}
			});
		}
		
		function list(param, callback, error) {
			var dealno = param.dealno;
			
			$.getJSON("/replies/lists/" + dealno + ".json", function(data) {
				if(callback) {
					callback(data);
				}
			}).fail(function(xhr, status, err) {
				if(error) {
					error(err);
				}
			});
		}
		
		function remove(replyno, callback, error) {
			$.ajax({
				type: "DELETE",
				url: "/replies/" + replyno,
				success: function(result, status, xhr) {
					if(callback) {
						callback(result);
					}
				},
				error: function(status, xhr, err) {
					if(error) {
						error(err);
					}
				}
			});
		}
		
	  	function modify(reply, callback, error) {
	  		$.ajax({
	  			type: "PATCH",
	  			url: "/replies/" + reply.replyno,
	  			data: JSON.stringify(reply),
	  			contentType: "application/json;charset=utf-8",
	  			success: function(result, status, xhr) {
	  				if(callback) {
	  					callback(result);
	  				}
	  			},
	  			error: function(status, xhr, err) {
	  				if(error) {
	  					error(err);
	  				}
	  			}
	  		});
	  	}
	  	
	  	function likeit(param, callback, error) {
			$.ajax({
				type: "POST",
				url: "/replies/likeit",
				data: JSON.stringify(param),
				contentType: "application/json;charset=utf-8",
				success: function(result, status, xhr) {
					if(callback) {
						callback(result);
					}
				},
				error: function(status, xhr, err) {
					if(error) {
						error(err);
					} 
				}
			});
	  	}
		
		return { 
			insert : insert,
			list : list,
			remove : remove,
			modify : modify,
			likeit : likeit
		};
	})();
});