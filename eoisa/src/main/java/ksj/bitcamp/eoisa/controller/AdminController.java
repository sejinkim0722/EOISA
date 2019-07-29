package ksj.bitcamp.eoisa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ksj.bitcamp.eoisa.dto.BoardDTO;
import ksj.bitcamp.eoisa.dto.MainDTO;
import ksj.bitcamp.eoisa.dto.NoticeBoardDTO;
import ksj.bitcamp.eoisa.dto.SignDTO;
import ksj.bitcamp.eoisa.service.AdminService;

@RestController
@RequestMapping("/admin/*")
public class AdminController {
	
	@Autowired
	private AdminService service;
	
	@RequestMapping(value = "/main")
	public ModelAndView adMain() {
		ModelAndView mv = new ModelAndView("admin");
		long noticeCount=service.getMemberCount();
		long freeCount=service.getFreeCount();
		long reviewCount=service.getReviewCount();
		mv.addObject("memberCount",service.getMemberCount());
		//mv.addObject("noticeCount", noticeCount);
		//mv.addObject("freeCount", freeCount);
		//mv.addObject("reviewCount", reviewCount);
		mv.addObject("boardCount", noticeCount+freeCount+reviewCount);
		mv.addObject("dealCount", service.getDealCount());
		return mv;
	}
	
	//search용 결과
	@RequestMapping(value = "/search/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object searchRes(@PathVariable(value = "type")String type, @RequestParam(value = "sVal") String sVal) {
		sVal = "%"+sVal+"%";
		if(type.equals("member")) {
			return service.searchMemberlist(sVal);
		} else if(type.equals("board")) {
			return service.searchBoardlist(sVal);
		} else if(type.equals("deal")) {
			return service.searchDeallist(sVal);
		}
		return null;
	}
		
	//테이블 select용 url
	@RequestMapping(value = "/json/{type}/{column}", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object selectRes(@PathVariable(value = "type") String type,@PathVariable(value = "column")String column) {
		if(type.equals("member")) {
			return service.getMemberAll(column);
		} else if(type.equals("board")) {
			if(column.equals("notice")) {
				return service.getNoticeAll();
			} else {
				return makeBoard(column);
			}
		} else if(type.equals("deal")) {
			return service.getDealAll();
		}
		return null;
	}
	
	public List<BoardDTO> makeBoard(String column){
		List<BoardDTO> list;
		if(column.equals("free")) {
			list=service.getFreeAll();
		} else {
			list=service.getReviewAll();
		}
		
		return list;
	}
	////////////////
	
	/*member 전용 crud*/
	@RequestMapping(value="/member/{crud}", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)//produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody String memberModify(@PathVariable(value = "crud") String crud, @RequestBody List<SignDTO> list) {
		service.updateMember(list);
		return "good";
	}
	
	/*board 전용crud*/
	//notice
	@RequestMapping(value="/notice/{crud}", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)//produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody String noticeCrud(@PathVariable(value = "crud") String crud, @RequestBody List<NoticeBoardDTO> list) {
		//crud별 분류
		service.deleteNotice(list);
		return "good";
	}
	
	//free, review
	@RequestMapping(value="/board/{type}/{crud}", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)//produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody String boardCrud(@PathVariable(value="type") String type, @PathVariable(value = "crud") String crud, @RequestBody List<BoardDTO> list) {
		if(type.equals("searching")) {
			service.deleteSearch(list);
		}else {
			service.deleteBoard(list, type);
		}
		return "good";
	} 
	
	/*deal 전용crud*/
	@RequestMapping(value="/deal/{crud}", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)//produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody String dealCrud(@PathVariable(value = "crud") String crud, @RequestBody List<MainDTO> list) {
		//crud별 분류
		service.deleteDeal(list);
		return "good";
	}
	
}