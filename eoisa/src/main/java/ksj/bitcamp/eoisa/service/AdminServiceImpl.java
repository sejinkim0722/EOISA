package ksj.bitcamp.eoisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksj.bitcamp.eoisa.dao.AdminDAO;
import ksj.bitcamp.eoisa.dto.BoardDTO;
import ksj.bitcamp.eoisa.dto.MainDTO;
import ksj.bitcamp.eoisa.dto.NoticeBoardDTO;
import ksj.bitcamp.eoisa.dto.SignDTO;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminDAO dao;

	public List<SignDTO> getMemberAll(String column) {

		return dao.getMemberList(column);
	}

	public List<NoticeBoardDTO> getNoticeAll() {

		return dao.getNoticeList();
	}

	public List<BoardDTO> getFreeAll() {
		return dao.getFreeList();
	}

	public List<BoardDTO> getReviewAll() {
		return dao.getReviewList();
	}

	public List<MainDTO> getDealAll() {
		return dao.getDealList();
	}

	public int getMemberCount() {
		return dao.getMemberCount();
	}

	public int getNoticeCount() {
		return dao.getNoticeCount();
	}

	public int getFreeCount() {
		return dao.getFreeCount();
	}

	public int getReviewCount() {
		return dao.getReviewCount();
	}

	public int getDealCount() {
		return dao.getDealCount();
	}

	public int updateMember(List<SignDTO> list) {

		return dao.updateMember(list);
	}

	public int deleteBoard(List<BoardDTO> list, String type) {
		if (type.equals("review")) {
			return dao.delReview(list);
		} else {
			return dao.delFree(list);
		}
	}

	public int deleteNotice(List<NoticeBoardDTO> list) {
		return dao.delNotice(list);
	}

	public int deleteDeal(List<MainDTO> list) {
		return dao.deleteDeal(list);
	}

	public int deleteSearch(List<BoardDTO> list) {
		return dao.deleteSearch(list);
	}

	// search
	public List<SignDTO> searchMemberlist(String sVal) {
		return dao.searchMember(sVal);
	}

	public List<MainDTO> searchDeallist(String sVal) {
		return dao.searchDeal(sVal);
	}

	public List<BoardDTO> searchBoardlist(String sVal) {
		return dao.searchAllBoard(sVal);
	}
	
}