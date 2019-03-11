package ksj.bitcamp.eoisa.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.github.scribejava.core.model.OAuth2AccessToken;

import ksj.bitcamp.eoisa.dto.SignDTO;
import ksj.bitcamp.eoisa.oauth.KakaoAccessToken;
import ksj.bitcamp.eoisa.oauth.KakaoUserInfo;
import ksj.bitcamp.eoisa.oauth.NaverLoginBO;
import ksj.bitcamp.eoisa.service.SignService;

@RestController
public class SignController
{	
	private NaverLoginBO naverLoginBO;
	
	@Autowired
	private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
		this.naverLoginBO = naverLoginBO;
	}
	
	@Autowired
	private SignService service;
	
	// Eoisa Sign Form
	@RequestMapping(value = "/sign/{status}")
	public ModelAndView signin(@PathVariable("status") String status) {
		ModelAndView mv = new ModelAndView();
		if(status.equals("form")) {
			mv.setViewName("sign");
		} else if(status.equals("success")) {
			mv.setViewName("sign");
			mv.addObject("status", status);
		} else if(status.equals("fail")) {
			mv.setViewName("sign");
			mv.addObject("status", status);
		}
		
		return mv;
	}
	
	// Eoisa Sign Up & Social Sign
	@PostMapping(value = "/signup")
	public ModelAndView signup(SignDTO dto) {
		dto.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt())); // Password Encryption

		String signupResult = service.signupService(dto);
		ModelAndView mv = new ModelAndView();
		if(signupResult.equals("EOISA")) {
			String uuid = UUID.randomUUID().toString().replace("-", "");
			service.emailAuthService(dto.getUsername(), uuid);
			
			mv.setViewName("sign");
			mv.addObject("status", signupResult);
		} else if(signupResult.equals("duplicated")) {
			mv.setViewName("sign");
			mv.addObject("status", signupResult);
		} else if(signupResult.equals("NAVER") || signupResult.equals("KAKAO")) {
			mv.setViewName("autosign");
			if(signupResult.equals("NAVER")) {
				mv.addObject("password", "social_naver");
			} else if(signupResult.equals("KAKAO")) {
				mv.addObject("password", "social_kakao");
			}
			mv.addObject("username", dto.getUsername());
			mv.addObject("request", "signin");
		}
		return mv;
	}
	
	// Email Verification
	@RequestMapping(value = "/verification/{username}/{uuid}")
	public ModelAndView verification(@PathVariable("username") String username, @PathVariable("uuid") String uuid) {
		int result = service.verificationService(username, uuid);
		
		ModelAndView mv = new ModelAndView();
		if(result == 1) {
			mv.addObject("result", "success");
		} else if(result == 0){
			mv.addObject("result", "fail");
		}
		mv.setViewName("verification");
		
		return mv;
	}
	
	// Find Password
	@PostMapping(value = "/findpw")
	public ModelAndView findPassword(@RequestParam String username) {
		String result = service.findPasswordService(username);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("result", result);
		mv.setViewName("verification");
		
		return mv;
	}
	
	// Kakao Signin
	@RequestMapping(value = "/kakaosignin", produces = "application/json;charset=UTF-8")
	public ModelAndView kakaoSignin(@RequestParam("code") String code) {
        JsonNode token = KakaoAccessToken.getKakaoAccessToken(code);
        JsonNode userinfo = KakaoUserInfo.getKakaoUserInfo(token.get("access_token"));
        
        System.out.println(userinfo);
        
        ModelAndView mv = new ModelAndView();
        mv.setViewName("autosign");
		mv.addObject("username", userinfo.path("id").asText());
		mv.addObject("password", "social_kakao");
		mv.addObject("nickname", userinfo.path("properties").path("nickname").asText());
		mv.addObject("profile_pic", userinfo.path("properties").path("profile_image").asText());
		mv.addObject("platform", "KAKAO");
		mv.addObject("request", "signup");
		
		return mv;
	}
	
	// Naver Signin
    @RequestMapping("/naver_url")
    public String getNaverUrl(HttpSession session) {
        return naverLoginBO.getAuthorizationUrl(session);
    }
    
    @RequestMapping(value = "/naversignin", produces = "application/text;charset=UTF-8")
    public ModelAndView naverSignin(@RequestParam("code") String code, @RequestParam String state, HttpSession session) throws Exception {
    	OAuth2AccessToken oauthToken = naverLoginBO.getAccessToken(session, code, state);
    	ObjectNode userInfo = new ObjectMapper().readValue(naverLoginBO.getUserProfile(oauthToken), ObjectNode.class);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("autosign");
		mv.addObject("username", userInfo.path("response").path("id").asText());
		mv.addObject("password", "social_naver");
		mv.addObject("nickname", userInfo.path("response").path("nickname").asText());
		mv.addObject("profile_pic", userInfo.path("response").path("profile_image").asText());
		mv.addObject("platform", "NAVER");
		mv.addObject("request", "signup");
		
		return mv;
    }
    
	// Nickname Check
	@PostMapping(value = "/nickname_check")
	public ResponseEntity<String> nicknameCheck(@RequestParam String nickname) {
		int result = service.nicknameCheckService(nickname);
		
		if(result == 1) {
			return new ResponseEntity<>("ok", HttpStatus.OK);
		} else if(result == 0) {
			return new ResponseEntity<>("duplicated", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Modify Userinfo
	@PostMapping(value = "/modify")
	public ResponseEntity<String> modify(SignDTO dto) {
		if(dto.getPassword() != "") dto.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt())); // Password Encryption
		
		return service.modifyService(dto) >= 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>("fail", HttpStatus.OK);
	}
	
	// Profile Picture Upload
	@PostMapping(value = "/profile_upload")
	public String profileUpload(MultipartHttpServletRequest mpsr) {
		String originalPath = "/var/eoisa/profile/";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String uploadDate = sdf.format(new Date());
		String uploadPath = originalPath + uploadDate + "/";
		String saveFilename = "";
		
		File dir = new File(uploadPath);
		if(!dir.exists()) dir.mkdirs();
		
		Iterator<String> files = mpsr.getFileNames();
		while(files.hasNext()) {
			String uploadFile = files.next();
			
			MultipartFile mf = mpsr.getFile(uploadFile);
			saveFilename = UUID.randomUUID().toString().replace("-", "");
			
			try {
				mf.transferTo(new File(uploadPath + saveFilename));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return "https://eoisa.ml/resources/profile/" + uploadDate + "/" + saveFilename;
	}
}