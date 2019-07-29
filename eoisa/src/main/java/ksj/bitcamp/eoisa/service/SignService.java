package ksj.bitcamp.eoisa.service;

import ksj.bitcamp.eoisa.dto.SignDTO;

public interface SignService {
	
	String signupService(SignDTO dto);
	int nicknameCheckService(String nickname);
	int modifyService(SignDTO dto);
	void emailAuthService(String username, String uuid);
	int verificationService(String username, String uuid);
	String findPasswordService(String username);
	
}
