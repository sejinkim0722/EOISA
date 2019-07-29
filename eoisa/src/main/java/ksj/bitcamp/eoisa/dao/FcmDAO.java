package ksj.bitcamp.eoisa.dao;

public interface FcmDAO {
	
	void push();
	void manageToken(String request, String clientToken);
	
}
