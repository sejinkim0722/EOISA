package ksj.bitcamp.eoisa.dao;

public interface FcmDAO {
	
	void pushMessaging();
	void manageToken(String request, String clientToken);
	
}
