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
	
	private static final String NS_REPLY = "ksj.bitcamp.eoisa.dto.ReplyDTO";

	@Override
	public int insertReply(ReplyDTO dto) {
		sqlSession.update(NS_REPLY + ".replyCountUp", dto.getDealno());
		return sqlSession.insert(NS_REPLY + ".replyInsert", dto);
	}

	@Override
	public List<ReplyDTO> getReplylist(int dealno) {
		return sqlSession.selectList(NS_REPLY + ".replyList", dealno);
	}

	@Override
	public int deleteReply(int replyno) {
		sqlSession.update(NS_REPLY + ".replyCountDown", replyno);
		return sqlSession.delete(NS_REPLY + ".replyDelete", replyno);
	}

	@Override
	public int modifyReply(ReplyDTO dto) {
		return sqlSession.update(NS_REPLY + ".replyModify", dto);
	}

	@Override
	public int manageReplyLikeit(ReplyDTO dto) {
		if ((int) sqlSession.selectOne(NS_REPLY + ".likeitCount", dto) == 1) {
			return 0;
		} else {
			sqlSession.insert(NS_REPLY + ".likeitInsert", dto);
			sqlSession.update(NS_REPLY + ".likeitCountUp", dto);
			
			return 1;
		}
	}
	
}