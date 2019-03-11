package ksj.bitcamp.eoisa.service;

import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import ksj.bitcamp.eoisa.dto.MainDTO;

public interface MainService 
{
	void crawling_algumonService(MainDTO dto);
	int paginationService(String title, int pageNum);
	int searchPaginationService(String keyword, int pageNum);
	int filterPaginationService(int pageNum, MultiValueMap<String, List<String>> filters);
	List<MainDTO> dealService(int pageNum);
	List<MainDTO> filterService(int pageNum, MultiValueMap<String, List<String>> filters);
	List<MainDTO> rankpageService();
	List<Map<String, Integer>> rankingService();
	List<MainDTO> searchService(String keyword, int pageNum);
	List<MainDTO> themeService(String title, int pageNum);
	int manageWishlistService(MainDTO dto);
	List<MainDTO> wishlistService(String username);
	String linkService(int dealno);
}
