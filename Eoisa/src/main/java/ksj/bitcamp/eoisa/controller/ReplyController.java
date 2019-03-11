package ksj.bitcamp.eoisa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ksj.bitcamp.eoisa.dto.ReplyDTO;
import ksj.bitcamp.eoisa.service.ReplyService;

@RestController
@RequestMapping("/replies/")
public class ReplyController 
{
	@Autowired
	private ReplyService service;
	
	@PostMapping(value = "/new", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> insert(@RequestBody ReplyDTO dto) {
		int insertCount = service.insertService(dto);
		
		return insertCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/lists/{dealno}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<List<ReplyDTO>> list(@PathVariable("dealno") int dealno) {
		return new ResponseEntity<>(service.listService(dealno), HttpStatus.OK);
	}
	
	@DeleteMapping(value ="/{replyno}", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> delete(@PathVariable("replyno") int replyno) {
		return service.deleteService(replyno) == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(method = { RequestMethod.PUT, RequestMethod.PATCH },
		value = "/{replyno}", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> modify(@RequestBody ReplyDTO dto, @PathVariable("replyno") int replyno) {
		dto.setReplyno(replyno);
		
		return service.modifyService(dto) == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/likeit", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> likeit(@RequestBody ReplyDTO dto) {
		int result = service.likeitService(dto);
		
		if(result == 1) {
			return new ResponseEntity<>("success", HttpStatus.OK);
		} else if(result == 0) {
			return new ResponseEntity<>("checked", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}