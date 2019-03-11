package ksj.bitcamp.eoisa.dto;

public class BoardDTO 
{
	private int articleno;
	private String username;
	private String nickname;
	private String profile_pic;
	private String subject;
	private String content;
	private String writeString;
	private int replycount;
	private int likeitcount;
	private int viewcount;
	private String tb_title;

	public BoardDTO() {}

	public BoardDTO(int articleno, String username, String nickname, String profile_pic, String subject,
			String content, String writeString, int replycount, int likeitcount, int viewcount, String tb_title) {
		this.articleno = articleno;
		this.username = username;
		this.nickname = nickname;
		this.profile_pic = profile_pic;
		this.subject = subject;
		this.content = content;
		this.writeString = writeString;
		this.replycount = replycount;
		this.likeitcount = likeitcount;
		this.viewcount = viewcount;
		this.tb_title = tb_title;
	}

	public int getArticleno() {
		return articleno;
	}

	public void setArticleno(int articleno) {
		this.articleno = articleno;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getProfile_pic() {
		return profile_pic;
	}

	public void setProfile_pic(String profile_pic) {
		this.profile_pic = profile_pic;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriteString() {
		return writeString;
	}

	public void setWriteString(String writeString) {
		this.writeString = writeString;
	}

	public int getReplycount() {
		return replycount;
	}

	public void setReplycount(int replycount) {
		this.replycount = replycount;
	}

	public int getLikeitcount() {
		return likeitcount;
	}

	public void setLikeitcount(int likeitcount) {
		this.likeitcount = likeitcount;
	}

	public int getViewcount() {
		return viewcount;
	}

	public void setViewcount(int viewcount) {
		this.viewcount = viewcount;
	}

	public String getTb_title() {
		return tb_title;
	}

	public void setTb_title(String tb_title) {
		this.tb_title = tb_title;
	}
}