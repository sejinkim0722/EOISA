package ksj.bitcamp.eoisa.dao;

import ksj.bitcamp.eoisa.dto.SignDTO;

public interface SignDAO 
{
	String signup(SignDTO dto);
	int nicknameCheck(String nickname);
	int modify(SignDTO dto);
	void emailAuth(String username, String uuid);
	int verification(String username, String uuid);
	String findPassword(String username);
}
