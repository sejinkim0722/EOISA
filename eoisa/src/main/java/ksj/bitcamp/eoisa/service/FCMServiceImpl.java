package ksj.bitcamp.eoisa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksj.bitcamp.eoisa.dao.FcmDAO;

@Service
public class FCMServiceImpl implements FCMService {
	
	@Autowired
	private FcmDAO dao;

	@Override
	public void pushMessagingService() {
		dao.pushMessaging();
	}

	@Override
	public void manageTokenService(String request, String clientToken) {
		dao.manageToken(request, clientToken);
	}
	
}