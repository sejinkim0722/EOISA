package ksj.bitcamp.eoisa.dao;

import java.util.List;

import ksj.bitcamp.eoisa.dto.ReplyDTO;

public interface ReplyDAO {
	
	int insertReply(ReplyDTO dto);
	List<ReplyDTO> getReplylist(int dealno);
	int deleteReply(int replyno);
	int modifyReply(ReplyDTO dto);
	int manageReplyLikeit(ReplyDTO dto);
	
}
