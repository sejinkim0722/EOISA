$.ajaxSetup({
	headers : {
		"X-CSRF-TOKEN" : $('meta[name = "_csrf_token"]').attr("content")
	},
	cache : false
});

//searching 관련
$(document).on("click", ".searching", function() {
	const searchVal = $(this).parent().siblings('input').val();
	$(".collapse").collapse("hide");
	$(this).parent().siblings('input').val('');
	if (searchVal.length == 0) {
		alert('검색어를 입력하세요!');
		return;
	}
	const stype = $(this).data('type');
	makeTitle(stype, 'searching');

	$('#content').empty();
	if (typeof table !== 'undefined') {
		table.destroy();
		$("#dataTable").empty();
	}
	getSearch(stype, searchVal);
});

function getSearch(stype, sVal) {
	const url = 'search/' + stype;
	let columns, columnDefs, colspan, genre, older;
	if (stype !== 'board') {
		switch (stype) {
		case 'member':
			colspan = 7;
			genre = stype;
			order = [ [ 1, "asc" ] ]
			columns = [ {
				data : 'username',
				title : '이메일'
			}, {
				data : 'authority',
				title : '권한'
			}, {
				data : 'nickname',
				title : '닉네임'
			}, {
				data : 'password',
				visible : false
			}, {
				data : 'profile_pic',
				title : '프로필사진'
			}, {
				data : 'platform',
				title : '가입경로'
			}, {
				data : 'enabled',
				title : '계정상태'
			}, {
				data : '',
				title : '수정'
			}, ];
			columnDefs = [
					{
						type : 'username',
						targets : 0,
						visible : true
					},
					{
						type : 'authority',
						targets : 1,
						render : function(data, type, row) {
							if (data === 'ADMIN') {
								data = '<select class="auth" name="authority"><option value="ADMIN" selected>ADMIN</option><option value="USER">USER</option></select>'
							} else {
								data = '<select class="auth" name="authority"><option value="ADMIN">ADMIN</option><option value="USER" selected>USER</option></select>'
							}
							return data;
						}
					},
					{
						type : 'nickname',
						targets : 2,
						render : function(data, type, row) {
							data = "<input class='nick' name='nickname' type='text' value='"
									+ data + "' size='30'>";
							return data;
						}
					},
					{
						type : 'profile_pic',
						targets : 4,
						render : function(data, type, row) {
							if(data == null || data == "") data = '"/resources/assets/profile.png"';
							data = '<img src=' + data + ' alt="profile_picture">';
							return data;
						}
					},
					{
						type : 'platform',
						targets : 5,
						visible : true
					},
					{
						type : 'enabled',
						targets : 6,
						render : function(data, type, row) {
							if (data == 0)
								data = '<select class="enb" name="enabled"><option value="0" selected>false</option><option value="1">true</option></select>'
							else
								data = '<select class="enb" name="enabled"><option value="0">false</option><option value="1" selected>true</option></select>'
							return data;
						}
					},
					{
						targets : 7,
						render : function(data, type, row) {
							data = '<a class="mody" data-genre=' + genre
									+ ' href="#">수정</a>';
							return data;
						}
					} ];
			break;
		case 'deal':
			genre = stype;
			colspan = 21;
			order = [ [ 13, "desc" ], [ 0, "desc" ] ];
			columns = [ {
				data : 'dealno',
				title : '딜번호'
			}, {
				data : 'goods_pic',
				title : '물품사진'
			}, {
				data : 'goods_title',
				title : '물품명'
			}, {
				data : 'isended',
				title : '종료여부'
			}, {
				data : 'site_src',
				title : '소스사이트 이름'
			}, {
				data : 'url_src',
				title : '소스사이트 주소'
			}, {
				data : 'site_buy',
				title : '구매사이트 이름'
			}, {
				data : 'category',
				title : '카테고리'
			}, {
				data : 'region',
				title : '국가'
			}, {
				data : 'price',
				title : '가격'
			}, {
				data : 'price_naver',
				title : '네이버 가격'
			}, {
				data : 'merit',
				title : '메리트'
			}, {
				data : 'deliever_fee',
				title : '배송비'
			}, {
				data : 'writetime',
				title : '작성시간'
			}, {
				data : 'replycount',
				title : '댓글수'
			}, {
				data : 'replycount_src',
				title : '댓글수(소스사이트)'
			}, {
				data : 'likeit_src',
				title : '추천수(소스사이트)'
			}, {
				data : 'dislikeit_src',
				title : '비추천수(소스사이트)'
			}, {
				data : 'viewcount',
				title : '조회수'
			}, {
				data : '',
				title : '댓글상세'
			}, {
				data : '',
				title : '삭제'
			} ];
			columnDefs = [
					{
						type : 'dealno',
						targets : 0,
						visible : true
					},
					{
						type : 'goods_pic',
						targets : 1,
						render : function(data, type, row) {
							if(data == null || data == "") data = '"/resources/assets/dealimg.png"';
							data = '<img src=' + data + ' alt="deal_picture">';
							return data;
						}
					},
					{
						type : 'goods_title',
						targets : 2,
						visible : true
					},
					{
						type : 'isended',
						targets : 3,
						render : function(data, type, row) {
							if (data !== '종료') {
								data = '진행중';
							}
							return data;
						}
					},
					{
						type : 'site_src',
						targets : 4,
						visible : true
					},
					{
						type : 'url_src',
						targets : 5,
						render : function(data, type, row) {
							data = '<a href=' + data + '>' + data + '</a>';
							return data;
						}
					},
					{
						type : 'site_buy',
						targets : 6,
						visible : true
					},
					{
						type : 'category',
						targets : 7,
						visible : true
					},
					{
						type : 'region',
						targets : 8,
						visible : true
					},
					{
						type : 'price',
						targets : 9,
						visible : true
					},
					{
						type : 'price_naver',
						targets : 10,
						visible : true
					},
					{
						type : 'merit',
						targets : 11,
						visible : true
					},
					{
						type : 'deliever_fee',
						targets : 12,
						visible : true
					},
					{
						type : 'writetime',
						targets : 13,
						visible : true
					},
					{
						type : 'replycount',
						targets : 14,
						render : function(data, type, row) {
							if (data == null) {
								data = 0;
							}
							return data;
						}
					},
					{
						type : 'replycount_src',
						targets : 15,
						render : function(data, type, row) {
							if (data == null) {
								data = 0;
							}
							return data;
						}
					},
					{
						type : 'likeit_src',
						targets : 16,
						render : function(data, type, row) {
							if (data == null) {
								data = 0;
							}
							return data;
						}
					},
					{
						type : 'dislikeit_src',
						targets : 17,
						render : function(data, type, row) {
							if (data == null) {
								data = 0;
							}
							return data;
						}
					},
					{
						type : 'viewcount',
						targets : 18,
						render : function(data, type, row) {
							if (data == null) {
								data = 0;
							}
							return data;
						}
					},
					{
						targets : 19,
						render : function(data, type, row) {
							data = '<a class="selrply" data-genre=' + genre
									+ ' href="#">댓글보기</a>';
							return data;
						}
					},
					{
						targets : 20,
						render : function(data, type, row) {
							data = '<a class="delete" data-genre=' + genre
									+ ' href="#">삭제</a>';
							return data;
						}
					} ];
			break;
		}

	} else {
		genre = stype + "/searching";
		colspan = 12;
		order = [ [ 7, "desc" ], [ 1, "desc" ] ];
		columns = [ {
			data : 'tb_title',
			title : '게시판명'
		}, {
			data : 'articleno',
			title : '글번호'
		}, {
			data : 'username',
			title : '글쓴이'
		}, {
			data : 'nickname',
			title : '별명'
		}, {
			data : 'profile_pic',
			title : '사진'
		}, {
			data : 'subject',
			title : '제목'
		}, {
			data : 'content',
			visible : false
		}, {
			data : 'writedate',
			title : '작성일'
		}, {
			data : 'replycount',
			title : '댓글수'
		}, {
			data : 'likeitcount',
			title : '좋아요수'
		}, {
			data : 'viewcount',
			title : '조회수'
		}, {
			data : '',
			title : '댓글보기'
		}, {
			data : '',
			title : '삭제'
		} ];
		columnDefs = [
				{
					type : 'tb_title',
					targets : 0,
					render : function(data, type, row) {
						if (data === 'NOTICEBOARD') {
							data = '공지 게시판';
						} else if (data === 'FREEBOARD') {
							data = '자유 게시판';
						} else {
							data = '후기 게시판';
						}
						return data;
					}
				},
				{
					type : 'articleno',
					targets : 1,
					visible : true
				},
				{
					type : 'username',
					targets : 2,
					visible : true
				},
				{
					type : 'nickname',
					targets : 3,
					visible : true
				},
				{
					type : 'profile_pic',
					targets : 4,
					render : function(data, type, row) {
						if(data == null || data == "") data = '"/resources/assets/profile.png"';
						data = '<img src=' + data + ' alt="profile_picture">';
						return data;
					}
				},
				{
					type : 'subject',
					targets : 5,
					render : function(data, type, row) {
						data = '<a class="selcon" data-genre=' + genre
								+ ' href="#">' + data + '</a>';
						return data;
					}
				},
				{
					type : 'writedate',
					targets : 7,
					render : function(data, type, row) {
						data = new Date(data).toLocaleString();
						return data;
					}
				},
				{
					type : 'replycount',
					targets : 8,
					render : function(data, type, row) {
						if (data == null) {
							data = 0;
						}
						return data;
					}
				},
				{
					type : 'viewcount',
					targets : 9,
					render : function(data, type, row) {
						if (data == null) {
							data = 0;
						}
						return data;
					}
				},
				{
					type : 'likeitcount',
					targets : 10,
					render : function(data, type, row) {
						if (data == null) {
							data = 0;
						}
						return data;
					}
				},
				{
					targets : 11,
					render : function(data, type, row) {
						data = '<a class="selrply" data-genre=' + genre
								+ ' href="#">댓글보기</a>';
						return data;
					}
				},
				{
					targets : 12,
					render : function(data, type, row) {
						data = '<a class="delete" data-genre=' + genre
								+ ' href="#">삭제</a>';
						return data;
					}
				} ];
	}
	$('#content')
			.append(
					'<table width="100%"class="table table-striped table-bordered dt-responsive nowrap" id="dataTable"><tfoot><td colspan='
							+ colspan + '></td></tfoot></table>');
	table = $('#dataTable')
			.DataTable(
					{
						"order" : order,
						lengthChange : true,
						"pageLength" : 10,
						"lengthMenu" : [ [ 10, 25, 50, 100 ],
								[ 10, 25, 50, 100 ] ],
						dom : '<"row"<"col-sm-6"l><"col-sm-6"f>>Brt<"row"<"col-sm-6"i><"col-sm-6"p>>',
						buttons : [ {
							extend : 'selectAll',
							text : '모두선택'
						}, {
							extend : 'selectNone',
							text : '선택해제'
						} ],
						select : {
							style : 'multi'
						},
						responsive : true,
						"scrollX" : true,
						ajax : {
							url : url,
							type : "POST",
							data : {
								sVal : sVal
							},
							dataSrc : ""
						},
						columns : columns,
						columnDefs : columnDefs,
						"footerCallback" : function(row, data, start, end,
								display) {
							var api = this.api(), data;
							if (stype === 'member') {
								$(api.column(0).footer())
										.html(
												'<button class="selMody" data-genre='
														+ genre
														+ ' type="button">선택수정</button>');
							} else {
								$(api.column(0).footer())
										.html(
												'<button class="selDel" data-genre='
														+ genre
														+ ' type="button">선택삭제</button>');
							}
						}
					});
}

//table 변수
var table;

//테이블 초기화시 제목변경
function makeTitle(type, column) {
	let textType, textColumn;
	switch (type) {
	case 'member':
		textType = '회원';
		switch (column) {
		case 'all':
			textColumn = '전체';
			break;
		case 'ADMIN':
			textColumn = '관리자별';
			break;
		case 'USER':
			textColumn = '사용자별';
			break;
		case 'searching':
			textColumn = '검색결과';
			break;
		}
		break;
	case 'board':
		textType = '게시판';
		switch (column) {
		case 'notice':
			textColumn = '공지사항';
			break;
		case 'free':
			textColumn = '자유게시판';
			break;
		case 'review':
			textColumn = '후기게시판';
			break;
		case 'searching':
			textColumn = '검색결과';
			break;
		}
		break;
	case 'deal':
		textType = '핫딜';
		console.log(column);
		switch (column) {
		case null:
			textColumn = '전체';
			break;
		case 'searching':
			textColumn = '검색결과';
			break;
		}
		break;
	}
	$('#page-title').html(textType + '  <small>' + textColumn + '</small>');
}

//테이블 초기화
function getTable(obj) {
	$(".collapse").collapse("hide");
	const ttype = $(obj).data('type');
	const tcolumn = $(obj).data('column');
	makeTitle(ttype, tcolumn); //제목 변경

	let url = "json";
	let columns, columnDefs, colspan, genre, older;
	url += '/' + ttype + '/' + tcolumn;
	switch (ttype) {
	case 'member':
		colspan = 7;
		genre = ttype;
		order = [ [ 1, "desc" ] ];
		columns = [ {
			data : 'username',
			title : '이메일'
		}, {
			data : 'authority',
			title : '권한'
		}, {
			data : 'nickname',
			title : '닉네임'
		}, {
			data : 'password',
			visible : false
		}, {
			data : 'profile_pic',
			title : '프로필사진'
		}, {
			data : 'platform',
			title : '가입경로'
		}, {
			data : 'enabled',
			title : '계정상태'
		}, {
			data : '',
			title : '수정'
		}, ];
		columnDefs = [
				{
					type : 'username',
					targets : 0,
					visible : true
				},
				{
					type : 'authority',
					targets : 1,
					render : function(data, type, row) {
						if (data === 'ADMIN') {
							data = '<select class="auth" name="authority"><option value="ADMIN" selected>ADMIN</option><option value="USER">USER</option></select>'
						} else {
							data = '<select class="auth" name="authority"><option value="ADMIN">ADMIN</option><option value="USER" selected>USER</option></select>'
						}
						return data;
					}
				},
				{
					type : 'nickname',
					targets : 2,
					render : function(data, type, row) {
						data = "<input class='nick' name='nickname' type='text' value='"
								+ data + "' size='30'>";
						return data;
					}
				},
				{
					type : 'password',
					targets : 3,
					render : function(data, type, row) {
						data = "<input class='pwd' name='password' type='text' value='"
								+ data + "' maxLength='50'>";
						return data;
					}
				},
				{
					type : 'profile_pic',
					targets : 4,
					render : function(data, type, row) {
						if(data == null || data == "") data = '"/resources/assets/profile.png"';
						data = '<img src=' + data + ' alt="profile_picture">';
						return data;
					}
				},
				{
					type : 'platform',
					targets : 5,
					visible : true
				},
				{
					type : 'enabled',
					targets : 6,
					render : function(data, type, row) {
						if (data == 0)
							data = '<select class="enb" name="enabled"><option value="0" selected>false</option><option value="1">true</option></select>'
						else
							data = '<select class="enb" name="enabled"><option value="0">false</option><option value="1" selected>true</option></select>'
						return data;
					}
				},
				{
					targets : 7,
					render : function(data, type, row) {
						data = '<a class="mody" data-genre=' + genre
								+ ' href="#">수정</a>';
						return data;
					}
				} ];
		break;
	case 'board':
		if (tcolumn == 'notice') {
			genre = tcolumn;
			colspan = 8;
			order = [ [ 5, "desc" ], [ 0, "desc" ] ];
			columns = [ {
				data : 'articleno',
				title : '글번호'
			}, {
				data : 'writer',
				title : '글쓴이'
			}, {
				data : 'subject',
				title : '제목'
			}, {
				data : 'content',
				visible : false
			}, {
				data : 'writedate',
				title : '작성일'
			}, {
				data : 'replycount',
				title : '댓글수'
			}, {
				data : 'viewcount',
				title : '조회수'
			}, {
				data : '',
				title : '댓글보기'
			}, {
				data : '',
				title : '삭제'
			} ];
			columnDefs = [
					{
						type : 'articleno',
						targets : 0,
						visible : true
					},
					{
						type : 'writer',
						targets : 1,
						visible : true
					},
					{
						type : 'subject',
						targets : 2,
						render : function(data, type, row) {
							data = '<a class="selcon" data-genre=' + genre
									+ ' href="#">' + data + '</a>';
							return data;
						}
					},
					{
						type : 'writedate',
						targets : 4,
						render : function(data, type, row) {
							data = new Date(data).toLocaleString();
							return data;
						}
					},
					{
						type : 'replycount',
						targets : 5,
						render : function(data, type, row) {
							if (data == null) {
								data = 0;
							}
							return data;
						}
					},
					{
						type : 'viewcount',
						targets : 6,
						render : function(data, type, row) {
							if (data == null) {
								data = 0;
							}
							return data;
						}
					},
					{
						targets : 7,
						render : function(data, type, row) {
							data = '<a class="selrply" data-genre=' + genre
									+ ' href="#">댓글보기</a>';
							return data;
						}
					},
					{
						targets : 8,
						render : function(data, type, row) {
							data = '<a class="delete" data-genre=' + genre
									+ ' href="#">삭제</a>';
							return data;
						}
					} ];
		} else {
			genre = ttype + '/' + tcolumn;
			colspan = 11;
			order = [ [ 6, "desc" ], [ 0, "desc" ] ];
			columns = [ {
				data : 'articleno',
				title : '글번호'
			}, {
				data : 'username',
				title : '글쓴이'
			}, {
				data : 'nickname',
				title : '별명'
			}, {
				data : 'profile_pic',
				title : '사진'
			}, {
				data : 'subject',
				title : '제목'
			}, {
				data : 'content',
				visible : false
			}, {
				data : 'writedate',
				title : '작성일'
			}, {
				data : 'replycount',
				title : '댓글수'
			}, {
				data : 'likeitcount',
				title : '좋아요수'
			}, {
				data : 'viewcount',
				title : '조회수'
			}, {
				data : '',
				title : '댓글보기'
			}, {
				data : '',
				title : '삭제'
			} ];
			columnDefs = [
					{
						type : 'articleno',
						targets : 0,
						visible : true
					},
					{
						type : 'username',
						targets : 1,
						visible : true
					},
					{
						type : 'nickname',
						targets : 2,
						visible : true
					},
					{
						type : 'profile_pic',
						targets : 3,
						render : function(data, type, row) {
							if(data == null || data == "") data = '"/resources/assets/profile.png"';
							data = '<img src=' + data + ' alt="profile_picture">';
							return data;
						}
					},
					{
						type : 'subject',
						targets : 4,
						render : function(data, type, row) {
							data = '<a class="selcon" data-genre=' + genre
									+ ' href="#">' + data + '</a>';
							return data;
						}
					},
					{
						type : 'writedate',
						targets : 6,
						render : function(data, type, row) {
							data = new Date(data).toLocaleString();
							return data;
						}
					},
					{
						type : 'replycount',
						targets : 7,
						render : function(data, type, row) {
							if (data == null) {
								data = 0;
							}
							return data;
						}
					},
					{
						type : 'viewcount',
						targets : 8,
						render : function(data, type, row) {
							if (data == null) {
								data = 0;
							}
							return data;
						}
					},
					{
						type : 'likeitcount',
						targets : 9,
						render : function(data, type, row) {
							if (data == null) {
								data = 0;
							}
							return data;
						}
					},
					{
						targets : 10,
						render : function(data, type, row) {
							data = '<a class="selrply" data-genre=' + genre
									+ ' href="#">댓글보기</a>';
							return data;
						}
					},
					{
						targets : 11,
						render : function(data, type, row) {
							data = '<a class="delete" data-genre=' + genre
									+ ' href="#">삭제</a>';
							return data;
						}
					} ];
		}
		break;
	case 'deal':
		genre = ttype;
		colspan = 20;
		order = [ [ 0, "desc" ] ];
		columns = [ {
			data : 'dealno',
			title : '딜번호'
		}, {
			data : 'goods_pic',
			title : '물품사진'
		}, {
			data : 'goods_title',
			title : '물품명'
		}, {
			data : 'isended',
			title : '종료여부'
		}, {
			data : 'site_src',
			title : '사이트 이름'
		}, {
			data : 'url_src',
			title : '사이트 URL'
		}, {
			data : 'site_buy',
			title : '쇼핑몰 이름'
		}, {
			data : 'category',
			title : '카테고리'
		}, {
			data : 'region',
			title : '지역'
		}, {
			data : 'price',
			title : '가격'
		}, {
			data : 'price_naver',
			title : '네이버 최저가'
		}, {
			data : 'merit',
			title : '메리트'
		}, {
			data : 'deliever_fee',
			title : '배송비'
		}, {
			data : 'writetime',
			title : '작성시간'
		}, {
			data : 'replycount',
			title : '댓글갯수'
		}, {
			data : 'replycount_src',
			title : '댓글수(소스사이트)'
		}, {
			data : 'likeit_src',
			title : '추천수(소스사이트)'
		}, {
			data : 'dislikeit_src',
			title : '비추천수(소스사이트)'
		}, {
			data : 'viewcount',
			title : '조회수'
		}, {
			data : '',
			visible: false
		}, {
			data : '',
			title : '수정/삭제'
		} ];
		columnDefs = [
				{
					type : 'dealno',
					targets : 0,
					visible : true
				},
				{
					type : 'goods_pic',
					targets : 1,
					render : function(data, type, row) {
						if(data == null || data == "") data = '"/resources/assets/dealimg.png"';
						data = '<img src=' + data + ' alt="deal_picture">';
						return data;
					}
				},
				{
					type : 'goods_title',
					targets : 2,
					visible : true
				},
				{
					type : 'isended',
					targets : 3,
					render : function(data, type, row) {
						if (data !== '종료') {
							data = '진행중';
						}
						return data;
					}
				},
				{
					type : 'site_src',
					targets : 4,
					visible : true
				},
				{
					type : 'url_src',
					targets : 5,
					render : function(data, type, row) {
						data = '<a href=' + data + '>' + data + '</a>';
						return data;
					}
				},
				{
					type : 'site_buy',
					targets : 6,
					visible : true
				},
				{
					type : 'category',
					targets : 7,
					visible : true
				},
				{
					type : 'region',
					targets : 8,
					visible : true
				},
				{
					type : 'price',
					targets : 9,
					visible : true
				},
				{
					type : 'price_naver',
					targets : 10,
					visible : true
				},
				{
					type : 'merit',
					targets : 11,
					visible : true
				},
				{
					type : 'deliever_fee',
					targets : 12,
					visible : true
				},
				{
					type : 'writetime',
					targets : 13,
					visible : true
				},
				{
					type : 'replycount',
					targets : 14,
					render : function(data, type, row) {
						if (data == null) {
							data = 0;
						}
						return data;
					}
				},
				{
					type : 'replycount_src',
					targets : 15,
					render : function(data, type, row) {
						if (data == null) {
							data = 0;
						}
						return data;
					}
				},
				{
					type : 'likeit_src',
					targets : 16,
					render : function(data, type, row) {
						if (data == null) {
							data = 0;
						}
						return data;
					}
				},
				{
					type : 'dislikeit_src',
					targets : 17,
					render : function(data, type, row) {
						if (data == null) {
							data = 0;
						}
						return data;
					}
				},
				{
					type : 'viewcount',
					targets : 18,
					render : function(data, type, row) {
						if (data == null) {
							data = 0;
						}
						return data;
					}
				},
				{
					targets : 19,
					render : function(data, type, row) {
						data = '<a class="selrply" data-genre=' + genre
								+ ' href="#">댓글보기</a>';
						return data;
					}
				},
				{
					targets : 20,
					render : function(data, type, row) {
						data = '<a class="delete" data-genre=' + genre
								+ ' href="#">삭제</a>';
						return data;
					}
				} ];
		break;
	}

	if (typeof table !== 'undefined') {
		table.destroy();
		$("#dataTable").empty();
	}
	
	//화면지우고 새로운 테이블 만드는 부분
	$('#content').empty();
	$('#content')
			.append(
					'<table width="100%"class="table table-striped table-bordered dt-responsive nowrap" id="dataTable"><tfoot><td colspan='
							+ colspan + '></td></tfoot></table>');
	table = $('#dataTable')
			.DataTable(
					{
						"order" : order,
						lengthChange : true,
						"pageLength" : 10,
						"lengthMenu" : [ [ 10, 25, 50, 100 ],
								[ 10, 25, 50, 100 ] ],
						dom : '<"row"<"col-sm-6"l><"col-sm-6"f>>Brt<"row"<"col-sm-6"i><"col-sm-6"p>>',
						buttons : [ {
							extend : 'selectAll',
							text : '모두선택'
						}, {
							extend : 'selectNone',
							text : '선택해제'
						} ],
						select : {
							style : 'multi'
						},
						responsive : true,
						"scrollX" : true,
						ajax : {
							url : url,
							type : "POST",
							contentType : "application/json; charset=UTF-8",
							dataSrc : ''
						},
						columns : columns,
						columnDefs : columnDefs,
						"footerCallback" : function(row, data, start, end,
								display) {
							var api = this.api(), data;
							if (ttype === 'member') {
								$(api.column(0).footer())
										.html(
												'<button class="selMody" data-genre='
														+ genre
														+ ' type="button">선택수정</button>');
							} else {
								$(api.column(0).footer())
										.html(
												'<button class="selDel" data-genre='
														+ genre
														+ ' type="button">선택삭제</button>');
							}
						}
					});
}

//테이블 동작
//1. 멤버동작
function commentAction() {
	const chkval = $(this).val();
	const td = this.parentElement;
	let comment = '';
	switch (this.className) {
	case 'auth':
		comment = '정말 권한을 변경하시겠습니까?';
		break;
	case 'enb':
		comment = '정말 계정상태를 변경하시겠습니까?';
		break;
	case 'nick':
		comment = '정말 별명을 변경하시겠습니까?';
		break;
	case 'pwd':
		comment = '정말 비밀번호를 변경하시겠습니까?';
		break;
	}
	if (!confirm(comment)) {
		table.cell(td).data(table.cell(td).data()).draw();
	} else {
		table.cell(td).data(chkval).draw();
	}
}
$(document).on("change", ".auth", commentAction);
$(document).on("change", ".enb", commentAction);
$(document).on("change", ".nick", commentAction);
$(document).on("change", ".pwd", commentAction);
//2. 게시판동작

//테이블 수정,삭제함수
$(document).on("click", ".mody", crudAjax);
$(document).on("click", ".delete", crudAjax);
$(document).on("click", ".selMody", crudAjax);
$(document).on("click", ".selDel", crudAjax);

//댓글과 내용보기
$(document).on("click", ".selcon", getModal);
$(document).on("click", ".selrply", getModal);

function getModal() {
	const clName = this.className;
	const genre = $(this).data('genre');
	const no = (table.row(this.parentElement.parentElement).data())['articleno'];
	var setting = "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400";
	var target = genre + clName;
}
//메뉴 리스폰시브
//통합 AJAX
function crudAjax() {
	const clName = this.className;
	const genre = $(this).data("genre");
	let jsonObj = {
		"" : ""
	};
	if (clName === 'mody' || clName === 'delete') {
		jsonObj = [ table.row(this.parentElement.parentElement).data() ];
	} else if (clName === 'selMody' || clName === 'selDel') {
		const datas = table
				.rows(
						document
								.querySelectorAll('.dataTables_scrollBody > table > tbody > tr.selected'))
				.data();
		let arr = [];
		for (var i = 0; i < datas.length; i++) {
			arr.push(datas[i]);
		}
		jsonObj = arr;
		if (jsonObj.length < 1) {
			alert('선택사항이 없습니다.');
			return;
		}
	}
	const url = genre + '/' + clName;
	$.ajax({
		url : url,
		type : "POST",
		contentType : "application/json",
		dataType : "text",
		data : JSON.stringify(jsonObj),
		success : function(data) {
			table.ajax.reload();
		},
		error : function(data) {
			alert('실패');
		}
	});
}