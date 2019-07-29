package ksj.bitcamp.eoisa.dto;

public class SignDTO {
	
	private String username;
	private String nickname;
	private String password;
	private String profile_pic;
	private int enabled;
	private String platform;
	private String authority;

	public SignDTO() {}

	public SignDTO(String username, String nickname, String password, String profile_pic, int enabled, String platform, String authority) {
		this.username = username;
		this.nickname = nickname;
		this.password = password;
		this.profile_pic = profile_pic;
		this.enabled = enabled;
		this.platform = platform;
		this.authority = authority;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfile_pic() {
		return profile_pic;
	}

	public void setProfile_pic(String profile_pic) {
		this.profile_pic = profile_pic;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
}