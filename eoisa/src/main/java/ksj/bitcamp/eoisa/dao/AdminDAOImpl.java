package ksj.bitcamp.eoisa.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ksj.bitcamp.eoisa.dto.BoardDTO;
import ksj.bitcamp.eoisa.dto.MainDTO;
import ksj.bitcamp.eoisa.dto.NoticeBoardDTO;
import ksj.bitcamp.eoisa.dto.SignDTO;

@Repository
public class AdminDAOImpl implements AdminDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	private static final String NS_MAIN = "ksj.bitcamp.eoisa.dto.MainDTO";
	private static final String NS_BOARD = "ksj.bitcamp.eoisa.dto.BoardDTO";
	private static final String NS_NOTICE = "ksj.bitcamp.eoisa.dto.NoticeBoardDTO";
	private static final String NS_SIGN = "ksj.bitcamp.eoisa.dto.SignDTO";

	public List<SignDTO> getUserList(String column) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("column", column);
		
		return sqlSession.selectList(NS_SIGN + ".userList", params);
	}

	public List<NoticeBoardDTO> getNoticeList() {
		return sqlSession.selectList(NS_NOTICE + ".noticeList");
	}

	public List<BoardDTO> getFreeList() {
		return sqlSession.selectList(NS_BOARD + ".freeList");
	}

	public List<BoardDTO> getReviewList() {
		return sqlSession.selectList(NS_BOARD + ".reviewList");
	}

	public List<MainDTO> getDealList() {
		return sqlSession.selectList(NS_MAIN + ".dealList");
	}

	public int getUserCount() {
		return sqlSession.selectOne(NS_SIGN + ".userCount");
	}

	public int getNoticeCount() {
		return sqlSession.selectOne(NS_NOTICE + ".noticeCount");
	}

	public int getFreeCount() {
		return sqlSession.selectOne(NS_BOARD + ".freeCount");
	}

	public int getReviewCount() {
		return sqlSession.selectOne(NS_BOARD + ".reviewCount");
	}

	public int getDealCount() {
		return sqlSession.selectOne(NS_MAIN + ".dealCount");
	}

	public int updateUser(List<SignDTO> list) {
		int count = 0;
		for (SignDTO dto : list) count += sqlSession.update(NS_SIGN + ".userModify", dto);

		return count;
	}

	public int delReview(List<BoardDTO> list) {
		return sqlSession.delete(NS_BOARD + ".reviewDelete", list);
	}

	public int delFree(List<BoardDTO> list) {
		return sqlSession.delete(NS_BOARD + ".freeDelete", list);
	}

	public int delNotice(List<NoticeBoardDTO> list) {
		return sqlSession.delete(NS_NOTICE + ".noticeDelete", list);
	}

	public int deleteDeal(List<MainDTO> list) {
		return sqlSession.delete(NS_MAIN + ".dealDelete", list);
	}

	public int deleteSearch(List<BoardDTO> list) {
		return sqlSession.delete(NS_BOARD + ".boardDelete", list);
	}

	// search
	public List<SignDTO> searchMember(String sVal) {
		return sqlSession.selectList(NS_SIGN + ".searchResult", sVal);
	}

	public List<MainDTO> searchDeal(String sVal) {
		return sqlSession.selectList(NS_MAIN + ".searchResult", sVal);
	}

	public List<BoardDTO> searchAllBoard(String sVal) {
		return sqlSession.selectList(NS_BOARD + ".searchResult", sVal);
	}
	
}