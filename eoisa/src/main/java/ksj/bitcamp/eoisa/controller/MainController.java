package ksj.bitcamp.eoisa.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.MultiValueMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ksj.bitcamp.eoisa.dto.MainDTO;
import ksj.bitcamp.eoisa.service.MainService;

@RestController
@EnableAsync
@EnableScheduling
public class MainController {
	
	@Autowired
	private MainService service;

	@Async
	@Scheduled(fixedDelay = 600000)
	public void crawling_algumon() {
		MainDTO dto = new MainDTO();
		service.crawlingService(dto);
	}

	// Main Page
	@RequestMapping(value = "/")
	public ModelAndView defaultMain() throws Exception {
		return main(1);
	}

	@RequestMapping(value = "/{pageNum}")
	public ModelAndView main(@PathVariable int pageNum) {
		int totalPage = service.pagingService(null, pageNum);
		List<MainDTO> deal = service.getDealPageService(pageNum);
		List<Map<String, Integer>> ranking = service.getRankingService();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<MainDTO> wishlist = service.getWishlistService(authentication.getName());

		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");
		mv.addObject("deal", deal);
		mv.addObject("ranking", ranking);
		mv.addObject("totalPage", totalPage);
		mv.addObject("wishlist", wishlist);

		return mv;
	}

	// Deal Site Redirect & Increase Viewcount
	@RequestMapping(value = "/deal/{dealno}")
	public RedirectView deal(@PathVariable int dealno) {
		return new RedirectView(service.viewcountIncreaseService(dealno));
	}

	// Filtering Page
	@RequestMapping(value = "/filter/{pageNum}")
	public ModelAndView filter(@PathVariable int pageNum, @RequestParam MultiValueMap<String, List<String>> filters) {
		int totalPage = service.filterPagingService(pageNum, filters);
		List<MainDTO> filter = service.getFilteredPageService(pageNum, filters);
		List<Map<String, Integer>> ranking = service.getRankingService();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<MainDTO> wishlist = service.getWishlistService(authentication.getName());

		ModelAndView mv = new ModelAndView();
		mv.setViewName("append");
		mv.addObject("deal", filter);
		mv.addObject("ranking", ranking);
		mv.addObject("totalPage", totalPage);
		mv.addObject("wishlist", wishlist);

		return mv;
	}

	// Rank Page
	@RequestMapping(value = "/rank")
	public ModelAndView rankpage() {
		List<MainDTO> rankpage = service.getRankPageService();
		List<Map<String, Integer>> ranking = service.getRankingService();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<MainDTO> wishlist = service.getWishlistService(authentication.getName());

		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");
		mv.addObject("deal", rankpage);
		mv.addObject("ranking", ranking);
		mv.addObject("wishlist", wishlist);

		return mv;
	}

	// Search Page
	@RequestMapping(value = "/search/{keyword}")
	public ModelAndView defaultSearch(@PathVariable("keyword") String keyword) throws Exception {
		return search(keyword, 1);
	}

	@RequestMapping(value = "/search/{keyword}/{pageNum}")
	public ModelAndView search(@PathVariable("keyword") String keyword, @PathVariable("pageNum") int pageNum) {
		int totalPage = service.searchPagingService(keyword, pageNum);
		List<MainDTO> search = service.getSearchResultService(keyword, pageNum);
		List<Map<String, Integer>> ranking = service.getRankingService();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<MainDTO> wishlist = service.getWishlistService(authentication.getName());

		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");
		mv.addObject("keyword", keyword);
		mv.addObject("deal", search);
		mv.addObject("ranking", ranking);
		mv.addObject("totalPage", totalPage);
		mv.addObject("wishlist", wishlist);

		return mv;
	}

	// Theme Deal Page
	@RequestMapping(value = "/theme/{title}")
	public ModelAndView defaultTheme(@PathVariable("title") String title) throws Exception {
		return theme(title, 1);
	}

	@RequestMapping(value = "/theme/{title}/{pageNum}")
	public ModelAndView theme(@PathVariable("title") String title, @PathVariable("pageNum") int pageNum) {
		int totalPage = service.pagingService(title, pageNum);
		List<MainDTO> theme = service.getThemePageService(title, pageNum);
		List<Map<String, Integer>> ranking = service.getRankingService();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<MainDTO> wishlist = service.getWishlistService(authentication.getName());

		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");
		mv.addObject("deal", theme);
		mv.addObject("ranking", ranking);
		mv.addObject("totalPage", totalPage);
		mv.addObject("wishlist", wishlist);

		return mv;
	}

	// Manage Wishlist
	@PostMapping(value = "/wishlist")
	public ResponseEntity<String> wishlist(MainDTO dto) {
		int result = service.manageWishlistService(dto);

		if (result == 1) {
			return new ResponseEntity<>("success", HttpStatus.OK);
		} else if (result == 0) {
			return new ResponseEntity<>("full", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Site Info Page
	@RequestMapping(value = "/info")
	public ModelAndView info() {
		return new ModelAndView("info");
	}

	// Access Denied Page
	@RequestMapping(value = "/denied")
	public ModelAndView denied() {
		return new ModelAndView("denied");
	}
	
}