package ksj.bitcamp.eoisa.dao;

import ksj.bitcamp.eoisa.dto.SignDTO;

public interface SignDAO {
	
	String signup(SignDTO dto);
	int checkNickname(String nickname);
	int modifyUserProfile(SignDTO dto);
	void insertEmailAuthInfo(String username, String uuid);
	int verifyUser(String username, String uuid);
	String findPassword(String username);
	
}
