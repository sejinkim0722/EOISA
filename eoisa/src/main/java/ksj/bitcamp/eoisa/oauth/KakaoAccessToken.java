package ksj.bitcamp.eoisa.oauth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class KakaoAccessToken {
	
	public static JsonNode getKakaoAccessToken(String code) {
		final String requestUrl = "https://kauth.kakao.com/oauth/token";
		final List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("grant_type", "authorization_code"));
		params.add(new BasicNameValuePair("client_id", ""));
		params.add(new BasicNameValuePair("redirect_uri", "https://eoisa.ml/kakaosignin"));
		params.add(new BasicNameValuePair("code", code));

		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(requestUrl);
		JsonNode returnNode = null;

		try {
			post.setEntity(new UrlEncodedFormEntity(params));
			final HttpResponse response = client.execute(post);
			ObjectMapper mapper = new ObjectMapper();

			returnNode = mapper.readTree(response.getEntity().getContent());
		} catch (UnsupportedEncodingException ue) {
			ue.printStackTrace();
		} catch (ClientProtocolException cp) {
			cp.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}

		return returnNode;
	}
	
}