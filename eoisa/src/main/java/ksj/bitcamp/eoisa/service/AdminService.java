package ksj.bitcamp.eoisa.service;

import java.util.List;

import ksj.bitcamp.eoisa.dto.BoardDTO;
import ksj.bitcamp.eoisa.dto.MainDTO;
import ksj.bitcamp.eoisa.dto.NoticeBoardDTO;
import ksj.bitcamp.eoisa.dto.SignDTO;

public interface AdminService {
	
	List<SignDTO> getMemberAll(String column);
	List<NoticeBoardDTO> getNoticeAll();
	List<BoardDTO> getFreeAll();
	List<BoardDTO> getReviewAll();
	List<MainDTO> getDealAll();
	int getMemberCount();
	int getNoticeCount();
	int getFreeCount();
	int getReviewCount();
	int getDealCount();
	int updateMember(List<SignDTO> list);
	int deleteBoard(List<BoardDTO> list, String type);
	int deleteNotice(List<NoticeBoardDTO> list);
	int deleteDeal(List<MainDTO> list);
	int deleteSearch(List<BoardDTO> list);
	
	//search
	List<SignDTO> searchMemberlist(String sVal);
	List<MainDTO> searchDeallist(String sVal);
	List<BoardDTO> searchBoardlist(String sVal);
	
}