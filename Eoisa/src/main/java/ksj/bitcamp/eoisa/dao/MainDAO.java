package ksj.bitcamp.eoisa.dao;

import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import ksj.bitcamp.eoisa.dto.MainDTO;

public interface MainDAO 
{
	void crawling_algumon(MainDTO dto);
	int pagination(String title, int pageNum);
	int searchPagination(String keyword, int pageNum);
	int filterPagination(int pageNum, MultiValueMap<String, List<String>> filters);
	List<MainDTO> deal(int pageNum);
	List<MainDTO> filter(int pageNum, MultiValueMap<String, List<String>> filters);
	List<MainDTO> rankpage();
	List<Map<String, Integer>> ranking();
	List<MainDTO> search(String keyword, int pageNum);
	List<MainDTO> theme(String title, int pageNum);
	int manageWishlist(MainDTO dto);
	List<MainDTO> wishlist(String username);
	String link(int dealno);
}
