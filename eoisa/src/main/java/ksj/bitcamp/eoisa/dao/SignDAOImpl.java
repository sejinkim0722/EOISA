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
public class SignDAOImpl implements SignDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	private static final String NS_SIGN = "ksj.bitcamp.eoisa.dto.SignDTO";

	@Override
	public String signup(SignDTO dto) {
		int check = sqlSession.selectOne(NS_SIGN + ".userCheck", dto);

		if(dto.getPlatform().equals("EOISA")) {
			if (check == 0) {
				dto.setEnabled(0);
				sqlSession.insert(NS_SIGN + ".userInsert", dto);

				return "EOISA";
			} else {
				return "duplicated";
			}
		} else {
			if (check == 0) {
				dto.setEnabled(1);
				sqlSession.insert(NS_SIGN + ".userInsert", dto);
			}

			if (dto.getPlatform().equals("NAVER")) {
				return "NAVER";
			} else if (dto.getPlatform().equals("KAKAO")) {
				return "KAKAO";
			}
		}
		
		return null;
	}

	@Override
	public int checkNickname(String nickname) {
		if((int)sqlSession.selectOne(NS_SIGN + ".userCheck", nickname) != 0) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int modifyUserProfile(SignDTO dto) {
		return sqlSession.update(NS_SIGN + ".modifyProfile", dto);
	}

	@Override
	public void insertEmailAuthInfo(String username, String uuid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("uuid", uuid);

		sqlSession.insert(NS_SIGN + ".emailAuthInsert", params);
	}

	@Override
	public int verifyUser(String username, String uuid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("uuid", uuid);

		return sqlSession.update(NS_SIGN + ".userVerify", params);
	}

	@Override
	public String findPassword(String username) {
		int check = (int)sqlSession.selectOne(NS_SIGN + ".userCheck", username);
		
		if(check == 1) {
			String uuid = UUID.randomUUID().toString().replace("-", "");

			Map<String, String> params = new HashMap<String, String>();
			params.put("username", username);
			params.put("uuid", BCrypt.hashpw(uuid, BCrypt.gensalt())); // Encrypt Temp Password

			sqlSession.update(NS_SIGN + ".tempPasswordUpdate", params);

			return uuid;
		} else {
			return "notexist";
		}
	}
	
}