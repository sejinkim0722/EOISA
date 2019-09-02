package ksj.bitcamp.eoisa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksj.bitcamp.eoisa.dao.SignDAO;
import ksj.bitcamp.eoisa.dto.SignDTO;
import ksj.bitcamp.eoisa.email.MailHandler;

@Service
public class SignServiceImpl implements SignService {
	
	@Autowired
	private SignDAO dao;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public String signupService(SignDTO dto) {
		return dao.signup(dto);
	}

	@Override
	public int checkNicknameService(String nickname) {
		return dao.checkNickname(nickname);
	}

	@Override
	public int modifyUserProfileService(SignDTO dto) {
		return dao.modifyUserProfile(dto);
	}

	@Transactional
	@Override
	public void insertEmailAuthInfoService(String username, String uuid) {
		dao.insertEmailAuthInfo(username, uuid);

		try {
			MailHandler sendMail = new MailHandler(mailSender);
			sendMail.setSubject("[어머이건사야해] 회원가입 이메일 인증 메일입니다.");
			sendMail.setText(new StringBuffer()
								.append("<div style='max-width: 750px; padding: 30px; border-radius: 3px; text-align: left;'>")
								.append("<img src='https://eoisa.ml/resources/assets/logo.png' width='200px' alt='logo'>")
								.append("<p><font size='6px' color='#565a5c'><strong>이메일 인증을 위한<br>링크 주소입니다.</strong></font></p>")
								.append("<hr style='margin-top: 30px; margin-bottom: 50px;'>")
								.append("<div style='border: 1px solid #ced1cc; padding: 10px;'><h3><font color='#565a5c'>해당 <a href='https://eoisa.ml/verification/"
										+ username + "/" + uuid
										+ "' target='_blank' style='text-decoration: none;'>링크</a>를 클릭하시면 인증 절차가 완료됩니다.</font></h3></div>")
								.append("<hr style='margin-top: 50px; margin-bottom: 30px;'></div>")
								.toString());
			sendMail.setFrom("sejinkim0722@gmail.com", "어머이건사야해");
			sendMail.setTo(username);
			sendMail.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int verifyUserService(String username, String uuid) {
		return dao.verifyUser(username, uuid);
	}

	@Override
	@Transactional
	public String findPasswordService(String username) {
		if (dao.findPassword(username).equals("notexist")) {
			return "notexist";
		} else {
			try {
				MailHandler sendMail = new MailHandler(mailSender);
				sendMail.setSubject("[어머이건사야해] 임시 비밀번호가 발급되었습니다.");
				sendMail.setText(new StringBuffer()
									.append("<div style='max-width: 750px; padding: 30px; border-radius: 3px; text-align: left;'>")
									.append("<img src='https://eoisa.ml/resources/assets/logo.png' width='200px' alt='logo'>")
									.append("<p><font size='6px' color='#565a5c'><strong>임시 비밀번호가<br>발급되었습니다.</strong></font></p>")
									.append("<hr style='margin-top: 30px; margin-bottom: 50px;'>")
									.append("<div style='border: 1px solid #ced1cc; padding: 10px;'><h3><font color='#007a87'>"
											+ dao.findPassword(username) + "</font></h3></div>")
									.append("<br>")
									.append("<h4><font color='#565a5c'>로그인하신 후 회원정보수정 페이지에서 비밀번호를 반드시 변경하세요.</font></h4>")
									.append("<hr style='margin-top: 50px; margin-bottom: 30px;'></div>")
									.toString());
				sendMail.setFrom("sejinkim0722@gmail.com", "어머이건사야해");
				sendMail.setTo(username);
				sendMail.send();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return "exist";
		}
	}
	
}