package ksj.bitcamp.eoisa.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import ksj.bitcamp.eoisa.dto.SignDTO;

@Repository
public class SignDAOImpl implements SignDAO
{
	@Autowired
	private SqlSession sqlSession;
	private String ns_sign = "ksj.bitcamp.eoisa.dto.SignDTO";
	
	@Override
	public String signup(SignDTO dto) {
		int check = sqlSession.selectOne(ns_sign + ".username_check", dto);
		
		if(dto.getPlatform().equals("EOISA")) {
			if(check == 0) {
				dto.setEnabled(0);
				sqlSession.insert(ns_sign + ".signup", dto);
				
				return "EOISA";
			} else {
				return "duplicated";
			}
		} else {
			if(check == 0) {
				dto.setEnabled(1);
				sqlSession.insert(ns_sign + ".signup", dto);
			}

			if(dto.getPlatform().equals("NAVER")) {
				return "NAVER";
			} else if (dto.getPlatform().equals("KAKAO")) {
				return "KAKAO";
			}
		}
		return null;
	}

	@Override
	public int nicknameCheck(String nickname) {
		if((int)sqlSession.selectOne(ns_sign + ".nickname_check", nickname) != 0) {
			return 0;
		} else {
			return 1;
		}
	}
	
	@Override
	public int modify(SignDTO dto) {
		return sqlSession.update(ns_sign + ".modify_userinfo", dto);
	}
	
	@Override
	public void emailAuth(String username, String uuid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("uuid", uuid);
		
		sqlSession.insert(ns_sign + ".email_auth", params);
	}
	
	@Override
	public int verification(String username, String uuid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("uuid", uuid);
		
		return sqlSession.update(ns_sign + ".verification", params);
	}
	
	@Override
	public String findPassword(String username) {
		int check = (int)sqlSession.selectOne(ns_sign + ".username_check", username);
		if(check == 1) {
			String uuid = UUID.randomUUID().toString().replace("-", "");
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", username);
			params.put("uuid", BCrypt.hashpw(uuid, BCrypt.gensalt()));
			
			sqlSession.update(ns_sign + ".temp_password", params);
			
			return uuid;
		} else {
			return "notexist";
		}
	}
}