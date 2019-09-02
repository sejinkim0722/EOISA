package ksj.bitcamp.eoisa.dao;

import java.util.List;

import ksj.bitcamp.eoisa.dto.BoardDTO;
import ksj.bitcamp.eoisa.dto.MainDTO;
import ksj.bitcamp.eoisa.dto.NoticeBoardDTO;
import ksj.bitcamp.eoisa.dto.SignDTO;

public interface AdminDAO {
	
	List<SignDTO> getUserList(String column);
	List<NoticeBoardDTO> getNoticeList();
	List<BoardDTO> getFreeList();
	List<BoardDTO> getReviewList();
	List<MainDTO> getDealList();
	int getUserCount();
	int getNoticeCount();
	int getFreeCount();
	int getReviewCount();
	int getDealCount();
	int updateUser(List<SignDTO> list);
	int delReview(List<BoardDTO> list);
	int delFree(List<BoardDTO> list);
	int delNotice(List<NoticeBoardDTO> list);
	int deleteDeal(List<MainDTO> list);
	int deleteSearch(List<BoardDTO> list);
	List<SignDTO> searchMember(String sVal);
	List<MainDTO> searchDeal(String sVal);
	List<BoardDTO> searchAllBoard(String sVal);
	
}
