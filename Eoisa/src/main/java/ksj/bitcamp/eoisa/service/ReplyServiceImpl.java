package ksj.bitcamp.eoisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksj.bitcamp.eoisa.dao.ReplyDAO;
import ksj.bitcamp.eoisa.dto.ReplyDTO;

@Service
public class ReplyServiceImpl implements ReplyService 
{
	@Autowired
	private ReplyDAO dao;

	@Override
	public int insertService(ReplyDTO dto) {
		return dao.insert(dto);
	}

	@Override
	public List<ReplyDTO> listService(int dealno) {
		return dao.list(dealno);
	}

	@Override
	public int deleteService(int replyno) {
		return dao.delete(replyno);
	}

	@Override
	public int modifyService(ReplyDTO dto) {
		return dao.modify(dto);
	}

	@Override
	public int likeitService(ReplyDTO dto) {
		return dao.likeit(dto);
	}
}
