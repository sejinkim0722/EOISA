package ksj.bitcamp.eoisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksj.bitcamp.eoisa.dao.ReplyDAO;
import ksj.bitcamp.eoisa.dto.ReplyDTO;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Autowired
	private ReplyDAO dao;

	@Override
	public int insertReplyService(ReplyDTO dto) {
		return dao.insertReply(dto);
	}

	@Override
	public List<ReplyDTO> getReplylistService(int dealno) {
		return dao.getReplylist(dealno);
	}

	@Override
	public int deleteReplyService(int replyno) {
		return dao.deleteReply(replyno);
	}

	@Override
	public int modifyReplyService(ReplyDTO dto) {
		return dao.modifyReply(dto);
	}

	@Override
	public int manageReplyLikeitService(ReplyDTO dto) {
		return dao.manageReplyLikeit(dto);
	}
	
}