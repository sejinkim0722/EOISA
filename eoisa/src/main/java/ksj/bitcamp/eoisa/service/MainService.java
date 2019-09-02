package ksj.bitcamp.eoisa.service;

import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import ksj.bitcamp.eoisa.dto.MainDTO;

public interface MainService {
	
	void crawlingService(MainDTO dto);
	int pagingService(String title, int pageNum);
	int searchPagingService(String keyword, int pageNum);
	int filterPagingService(int pageNum, MultiValueMap<String, List<String>> filters);
	List<MainDTO> getDealPageService(int pageNum);
	List<MainDTO> getFilteredPageService(int pageNum, MultiValueMap<String, List<String>> filters);
	List<MainDTO> getRankPageService();
	List<Map<String, Integer>> getRankingService();
	List<MainDTO> getSearchResultService(String keyword, int pageNum);
	List<MainDTO> getThemePageService(String title, int pageNum);
	int manageWishlistService(MainDTO dto);
	List<MainDTO> getWishlistService(String username);
	String viewcountIncreaseService(int dealno);
	
}