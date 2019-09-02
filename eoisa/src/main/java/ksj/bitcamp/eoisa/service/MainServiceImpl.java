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
	public void crawlingService(MainDTO dto) {
		dao.crawling(dto);
	}

	@Override
	public int pagingService(String title, int pageNum) {
		return dao.paging(title, pageNum);
	}

	@Override
	public int searchPagingService(String keyword, int pageNum) {
		return dao.searchPaging(keyword, pageNum);
	}

	@Override
	public int filterPagingService(int pageNum, MultiValueMap<String, List<String>> filters) {
		return dao.filterPaging(pageNum, filters);
	}

	@Override
	public List<MainDTO> getDealPageService(int pageNum) {
		return dao.getDealPage(pageNum);
	}

	@Override
	public List<MainDTO> getFilteredPageService(int pageNum, MultiValueMap<String, List<String>> filters) {
		return dao.getFilteredPage(pageNum, filters);
	}

	@Override
	public List<MainDTO> getRankPageService() {
		return dao.getRankPage();
	}

	@Override
	public List<Map<String, Integer>> getRankingService() {
		return dao.getRanking();
	}

	@Override
	public List<MainDTO> getSearchResultService(String keyword, int pageNum) {
		return dao.getSearchResult(keyword, pageNum);
	}

	@Override
	public List<MainDTO> getThemePageService(String title, int pageNum) {
		return dao.getThemePage(title, pageNum);
	}

	@Override
	public int manageWishlistService(MainDTO dto) {
		return dao.manageWishlist(dto);
	}

	@Override
	public List<MainDTO> getWishlistService(String username) {
		return dao.getWishlist(username);
	}

	@Override
	public String viewcountIncreaseService(int dealno) {
		return dao.viewcountIncrease(dealno);
	}
	
}