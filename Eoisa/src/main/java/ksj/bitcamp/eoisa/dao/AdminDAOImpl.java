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
	private String ns_main = "ksj.bitcamp.eoisa.dto.MainDTO";
	private String ns_board = "ksj.bitcamp.eoisa.dto.BoardDTO";
	private String ns_notice = "ksj.bitcamp.eoisa.dto.NoticeBoardDTO";
	private String ns_sign = "ksj.bitcamp.eoisa.dto.SignDTO";

	public List<SignDTO> getMemberList(String column) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("column", column);
		return sqlSession.selectList(ns_sign + ".list_all", params);
	}

	public List<NoticeBoardDTO> getNoticeList() {
		return sqlSession.selectList(ns_notice + ".list_all");
	}

	public List<BoardDTO> getFreeList() {
		return sqlSession.selectList(ns_board + ".getFreeList");
	}

	public List<BoardDTO> getReviewList() {
		return sqlSession.selectList(ns_board + ".getReviewList");
	}

	public List<MainDTO> getDealList() {
		return sqlSession.selectList(ns_main + ".list_all");
	}

	public int getMemberCount() {
		return sqlSession.selectOne(ns_sign + ".count_all");
	}

	public int getNoticeCount() {
		return sqlSession.selectOne(ns_notice + ".count_all");
	}

	public int getFreeCount() {
		return sqlSession.selectOne(ns_board + ".getFreeCount");
	}

	public int getReviewCount() {
		return sqlSession.selectOne(ns_board + ".getReviewCount");
	}

	public int getDealCount() {
		return sqlSession.selectOne(ns_main + ".count_all");
	}

	public int updateMember(List<SignDTO> list) {
		int i = 0;
		for (SignDTO dto : list) {
			i += sqlSession.update(ns_sign + ".modify", dto);
		}
		return i;
	}

	public int delReview(List<BoardDTO> list) {
		return sqlSession.delete(ns_board + ".delete_review", list);
	}

	public int delFree(List<BoardDTO> list) {
		return sqlSession.delete(ns_board + ".delete_free", list);
	}

	public int delNotice(List<NoticeBoardDTO> list) {
		return sqlSession.delete(ns_notice + ".delete_notice", list);
	}

	public int deleteDeal(List<MainDTO> list) {
		return sqlSession.delete(ns_main + ".delete_deal", list);
	}

	public int deleteSearch(List<BoardDTO> list) {
		return sqlSession.delete(ns_board + ".delSearch", list);
	}

	// search
	public List<SignDTO> searchMember(String sVal) {
		return sqlSession.selectList(ns_sign + ".search", sVal);
	}

	public List<MainDTO> searchDeal(String sVal) {
		return sqlSession.selectList(ns_main + ".search", sVal);
	}

	public List<BoardDTO> searchAllBoard(String sVal) {
		return sqlSession.selectList(ns_board + ".search", sVal);
	}
}
