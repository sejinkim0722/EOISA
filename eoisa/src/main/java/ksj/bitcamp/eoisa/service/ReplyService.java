package ksj.bitcamp.eoisa.service;

import java.util.List;

import ksj.bitcamp.eoisa.dto.ReplyDTO;

public interface ReplyService {
	
	int insertReplyService(ReplyDTO dto);
	List<ReplyDTO> getReplylistService(int dealno);
	int deleteReplyService(int replyno);
	int modifyReplyService(ReplyDTO dto);
	int manageReplyLikeitService(ReplyDTO dto);
	
}