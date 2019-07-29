package ksj.bitcamp.eoisa.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import ksj.bitcamp.eoisa.dao.MainDAO;
import ksj.bitcamp.eoisa.dto.MainDTO;

@Service
public class MainServiceImpl implements MainService {
	
	@Autowired
	private MainDAO dao;

	@Override
	public void crawling_algumonService(MainDTO dto) {
		dao.crawling_algumon(dto);
	}

	@Override
	public int paginationService(String title, int pageNum) {
		return dao.pagination(title, pageNum);
	}

	@Override
	public int searchPaginationService(String keyword, int pageNum) {
		return dao.searchPagination(keyword, pageNum);
	}

	@Override
	public int filterPaginationService(int pageNum, MultiValueMap<String, List<String>> filters) {
		return dao.filterPagination(pageNum, filters);
	}

	@Override
	public List<MainDTO> dealService(int pageNum) {
		return dao.deal(pageNum);
	}

	@Override
	public List<MainDTO> filterService(int pageNum, MultiValueMap<String, List<String>> filters) {
		return dao.filter(pageNum, filters);
	}

	@Override
	public List<MainDTO> rankpageService() {
		return dao.rankpage();
	}

	@Override
	public List<Map<String, Integer>> rankingService() {
		return dao.ranking();
	}

	@Override
	public List<MainDTO> searchService(String keyword, int pageNum) {
		return dao.search(keyword, pageNum);
	}

	@Override
	public List<MainDTO> themeService(String title, int pageNum) {
		return dao.theme(title, pageNum);
	}

	@Override
	public int manageWishlistService(MainDTO dto) {
		return dao.manageWishlist(dto);
	}

	@Override
	public List<MainDTO> wishlistService(String username) {
		return dao.wishlist(username);
	}

	@Override
	public String linkService(int dealno) {
		return dao.link(dealno);
	}
	
}
