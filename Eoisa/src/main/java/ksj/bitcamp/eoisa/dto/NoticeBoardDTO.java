package ksj.bitcamp.eoisa.dto;

public class NoticeBoardDTO 
{
	private int articleno;
	private String writer;
	private String subject;
	private String content;
	private String writedate;
	private int replycount;
	private int viewcount;

	public NoticeBoardDTO() {}

	public NoticeBoardDTO(int articleno, String writer, String subject, String content, String writedate,
			int replycount, int viewcount) {
		this.articleno = articleno;
		this.writer = writer;
		this.subject = subject;
		this.content = content;
		this.writedate = writedate;
		this.replycount = replycount;
		this.viewcount = viewcount;
	}

	public int getArticleno() {
		return articleno;
	}

	public void setArticleno(int articleno) {
		this.articleno = articleno;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
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

	public String getWritedate() {
		return writedate;
	}

	public void setWritedate(String writedate) {
		this.writedate = writedate;
	}

	public int getReplycount() {
		return replycount;
	}

	public void setReplycount(int replycount) {
		this.replycount = replycount;
	}

	public int getViewcount() {
		return viewcount;
	}

	public void setViewcount(int viewcount) {
		this.viewcount = viewcount;
	}
}
