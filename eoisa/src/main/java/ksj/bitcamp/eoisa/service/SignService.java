package ksj.bitcamp.eoisa.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import ksj.bitcamp.eoisa.dto.SignDTO;

public interface SignService {
	
	String signupService(SignDTO dto);
	int checkNicknameService(String nickname);
	int modifyUserProfileService(SignDTO dto);
	void insertEmailAuthInfoService(String username, String uuid);
	int verifyUserService(String username, String uuid);
	String findPasswordService(String username);
	String profileUploadService(MultipartHttpServletRequest mpsr);
	
}