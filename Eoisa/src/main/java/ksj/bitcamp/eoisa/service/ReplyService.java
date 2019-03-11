package ksj.bitcamp.eoisa.service;

import java.util.List;

import ksj.bitcamp.eoisa.dto.ReplyDTO;

public interface ReplyService 
{
	int insertService(ReplyDTO dto);
	List<ReplyDTO> listService(int dealno);
	int deleteService(int replyno);
	int modifyService(ReplyDTO dto);
	int likeitService(ReplyDTO dto);
}
