package ksj.bitcamp.eoisa.dao;

import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import ksj.bitcamp.eoisa.dto.MainDTO;

public interface MainDAO {
	
	void crawling(MainDTO dto);
	int paging(String title, int pageNum);
	int searchPaging(String keyword, int pageNum);
	int filterPaging(int pageNum, MultiValueMap<String, List<String>> filters);
	List<MainDTO> getDealPage(int pageNum);
	List<MainDTO> getFilteredPage(int pageNum, MultiValueMap<String, List<String>> filters);
	List<MainDTO> getRankPage();
	List<Map<String, Integer>> getRanking();
	List<MainDTO> getSearchResult(String keyword, int pageNum);
	List<MainDTO> getThemePage(String title, int pageNum);
	int manageWishlist(MainDTO dto);
	List<MainDTO> getWishlist(String username);
	String viewcountIncrease(int dealno);
	
}
