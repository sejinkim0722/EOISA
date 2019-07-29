package ksj.bitcamp.eoisa.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ksj.bitcamp.eoisa.dto.ReplyDTO;

@Repository
public class ReplyDAOImpl implements ReplyDAO {
	
	@Autowired
	private SqlSession sqlSession;
	private String ns_reply = "ksj.bitcamp.eoisa.dto.ReplyDTO";

	@Override
	public int insert(ReplyDTO dto) {
		sqlSession.update(ns_reply + ".count_up", dto.getDealno());
		return sqlSession.insert(ns_reply + ".insert", dto);
	}

	@Override
	public List<ReplyDTO> list(int dealno) {
		return sqlSession.selectList(ns_reply + ".list", dealno);
	}

	@Override
	public int delete(int replyno) {
		sqlSession.update(ns_reply + ".count_down", replyno);
		return sqlSession.delete(ns_reply + ".delete", replyno);
	}

	@Override
	public int modify(ReplyDTO dto) {
		return sqlSession.update(ns_reply + ".modify", dto);
	}

	@Override
	public int likeit(ReplyDTO dto) {
		if ((int) sqlSession.selectOne(ns_reply + ".likeit_check", dto) == 1) {
			return 0;
		} else {
			sqlSession.insert(ns_reply + ".likeit", dto);
			sqlSession.update(ns_reply + ".likeit_count_up", dto);
			return 1;
		}
	}
	
}
