package ksj.bitcamp.eoisa.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

@Repository
public class FcmDAOImpl implements FcmDAO 
{
	private static final String PROJECT_ID = "project-eoisa";
	private static final String SERVER_KEY = "";
	private static final String BASE_URL = "https://fcm.googleapis.com";
	private static final String BASE_URL_IID = "https://iid.googleapis.com/iid/v1/";
	private static final String FCM_SEND_ENDPOINT = "/v1/projects/" + PROJECT_ID + "/messages:send";
	private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
	private static final String[] SCOPES = { MESSAGING_SCOPE };
	
	@Autowired
	private SqlSession sqlSession;
	private String ns_main = "ksj.bitcamp.eoisa.dto.MainDTO";

	@Override
	@SuppressWarnings("unchecked")
	public void push() {		
		try {
			int current = sqlSession.selectOne(ns_main + ".count_all");
			TimeUnit.MILLISECONDS.sleep(60000);
			int after = sqlSession.selectOne(ns_main + ".count_all");
			
			if(after > current) {
	            HttpHeaders headers = new HttpHeaders();
	            headers.add("content-type" , MediaType.APPLICATION_JSON_UTF8_VALUE);
	            headers.add("Authorization", "Bearer " + getAccessToken());
	            
	            JSONObject notification = new JSONObject();
	            notification.put("title", "어머이건사야해");
	            notification.put("body", "새로운 핫딜이 등록되었습니다.");
	            
	            JSONObject fcm_options = new JSONObject();
	            fcm_options.put("link", "https://eoisa.ml");

	            JSONObject webpush = new JSONObject();
	            webpush.put("fcm_options", fcm_options);
	            
	            JSONObject message = new JSONObject();
	            message.put("topic", "new");
	            message.put("notification", notification);
	            message.put("webpush", webpush);
	            
	            JSONObject jsonParams = new JSONObject();
	            jsonParams.put("message", message);
	            
	            HttpEntity<JSONObject> httpEntity = new HttpEntity<JSONObject>(jsonParams, headers);
	            RestTemplate rt = new RestTemplate();            
	            
	            ResponseEntity<String> res = rt.exchange(BASE_URL + FCM_SEND_ENDPOINT
	            		,HttpMethod.POST
	                    ,httpEntity
	                    ,String.class);
	            
	            if (res.getStatusCode() == HttpStatus.OK) {
	                System.out.println("Push sent Successfully : HTTP Status " + res.getStatusCode().toString());
	            } else {
	            	System.out.println("Push send Failure : HTTP Status " + res.getStatusCode().toString());
	            }
			} else {
	            System.out.println("New Hotdeals does not exists");
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private String getAccessToken() throws IOException {
		GoogleCredential googleCredential = GoogleCredential
			.fromStream(new FileInputStream("/var/eoisa/project-eoisa.json"))
			.createScoped(Arrays.asList(SCOPES));
		googleCredential.refreshToken();
		return googleCredential.getAccessToken();
	}
	
	public void manageToken(String request, String clientToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type" , MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Authorization", "key=" + SERVER_KEY);
		
        HttpEntity<JSONObject> httpEntity = new HttpEntity<JSONObject>(headers);
        RestTemplate rt = new RestTemplate();
        
        if(request.equals("subscribe")) {
        	rt.exchange(BASE_URL_IID + clientToken + "/rel/topics/new", HttpMethod.POST, httpEntity , String.class);
        } else if(request.equals("unsubscribe")) {
            rt.exchange(BASE_URL_IID + clientToken + "/rel/topics/new", HttpMethod.DELETE, httpEntity, String.class);
        }
	}
}