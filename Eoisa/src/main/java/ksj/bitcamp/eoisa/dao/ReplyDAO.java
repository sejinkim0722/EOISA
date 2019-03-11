package ksj.bitcamp.eoisa.dao;

import java.util.List;

import ksj.bitcamp.eoisa.dto.ReplyDTO;

public interface ReplyDAO 
{
	int insert(ReplyDTO dto);
	List<ReplyDTO> list(int dealno);
	int delete(int replyno);
	int modify(ReplyDTO dto);
	int likeit(ReplyDTO dto);
}
