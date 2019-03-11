package ksj.bitcamp.eoisa.dto;

public class ReplyDTO 
{
	private int replyno;
	private int dealno;
	private String username;
	private String nickname;
	private String profile_pic;
	private String writedate;
	private String content;
	private int likeitcount;
	private int depth;
	private int ref;
	
	// Likeit
	private int idx;
	private int likeit;

	public ReplyDTO() {}

	public ReplyDTO(int replyno, int dealno, String username, String nickname, String profile_pic, String writedate,
			String content, int likeitcount, int depth, int ref) {
		this.replyno = replyno;
		this.dealno = dealno;
		this.username = username;
		this.nickname = nickname;
		this.profile_pic = profile_pic;
		this.writedate = writedate;
		this.content = content;
		this.likeitcount = likeitcount;
		this.depth = depth;
		this.ref = ref;
	}
	
	// Likeit
	public ReplyDTO(int idx, String username, int replyno, int likeit) {
		this.idx = idx;
		this.username = username;
		this.replyno = replyno;
		this.likeit = likeit;
	}

	public int getReplyno() {
		return replyno;
	}

	public void setReplyno(int replyno) {
		this.replyno = replyno;
	}

	public int getDealno() {
		return dealno;
	}

	public void setDealno(int dealno) {
		this.dealno = dealno;
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

	public String getWritedate() {
		return writedate;
	}

	public void setWritedate(String writedate) {
		this.writedate = writedate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLikeitcount() {
		return likeitcount;
	}

	public void setLikeitcount(int likeitcount) {
		this.likeitcount = likeitcount;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getRef() {
		return ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getLikeit() {
		return likeit;
	}

	public void setLikeit(int likeit) {
		this.likeit = likeit;
	}
}