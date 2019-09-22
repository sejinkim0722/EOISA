package ksj.bitcamp.eoisa.controller;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.github.scribejava.core.model.OAuth2AccessToken;

import ksj.bitcamp.eoisa.dto.SignDTO;
import ksj.bitcamp.eoisa.oauth.KakaoAccessToken;
import ksj.bitcamp.eoisa.oauth.KakaoUserInfo;
import ksj.bitcamp.eoisa.oauth.NaverLoginBO;
import ksj.bitcamp.eoisa.service.SignService;

@RestController
public class SignController {
	
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
		switch(status) {
			case "form":
				mv.setViewName("sign");
				break;
			case "success":
				mv.setViewName("sign");
				mv.addObject("status", status);
				break;
			case "fail":
				mv.setViewName("sign");
				mv.addObject("status", status);
				break;
		}

		return mv;
	}

	// Eoisa Signup & Social Signin
	@PostMapping(value = "/signup")
	public ModelAndView signup(SignDTO dto) {
		String signupResult = service.signupService(dto);
		ModelAndView mv = new ModelAndView();
		
		switch(signupResult) {
			case "EOISA":
				service.insertEmailAuthInfoService(dto.getUsername(), UUID.randomUUID().toString().replace("-", ""));
				mv.setViewName("sign");
				mv.addObject("status", signupResult);
				break;
			case "NAVER":
			case "KAKAO":
				mv.setViewName("autosign");
				mv.addObject("username", dto.getUsername());
				mv.addObject("password", "socialSignIn");
				mv.addObject("request", "signin");
				break;
			case "duplicated":
				mv.setViewName("sign");
				mv.addObject("status", signupResult);
				break;
		}

		return mv;
	}

	// Email Verification
	@RequestMapping(value = "/verification/{username}/{uuid}")
	public ModelAndView verifyUser(@PathVariable("username") String username, @PathVariable("uuid") String uuid) {
		int result = service.verifyUserService(username, uuid);

		ModelAndView mv = new ModelAndView();
		if(result == 1) {
			mv.addObject("result", "success");
		} else if(result == 0) {
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

		ModelAndView mv = new ModelAndView();
		mv.setViewName("autosign");
		mv.addObject("username", userinfo.path("id").asText());
		mv.addObject("password", "socialSignIn");
		mv.addObject("nickname", userinfo.path("properties").path("nickname").asText());
		mv.addObject("profile_pic", userinfo.path("properties").path("profile_image").asText());
		mv.addObject("platform", "KAKAO");
		mv.addObject("request", "signup");

		return mv;
	}

	// Naver Signin
	@RequestMapping("/naverurl")
	public String getNaverUrl(HttpSession session) {
		return naverLoginBO.getAuthorizationUrl(session);
	}

	@RequestMapping(value = "/naversignin", produces = "application/text;charset=UTF-8")
	public ModelAndView naverSignin(@RequestParam("code") String code, @RequestParam String state, HttpSession session) throws Exception {
		OAuth2AccessToken oAuthToken = naverLoginBO.getAccessToken(session, code, state);
		ObjectNode userInfo = new ObjectMapper().readValue(naverLoginBO.getUserProfile(oAuthToken), ObjectNode.class);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("autosign");
		mv.addObject("username", userInfo.path("response").path("id").asText());
		mv.addObject("password", "socialSignIn");
		mv.addObject("nickname", userInfo.path("response").path("nickname").asText());
		mv.addObject("profile_pic", userInfo.path("response").path("profile_image").asText());
		mv.addObject("platform", "NAVER");
		mv.addObject("request", "signup");

		return mv;
	}

	// Nickname Check
	@PostMapping(value = "/nicknamecheck")
	public ResponseEntity<String> checkNickname(@RequestParam String nickname) {
		int result = service.checkNicknameService(nickname);

		if(result == 1) {
			return new ResponseEntity<>("ok", HttpStatus.OK);
		} else if(result == 0) {
			return new ResponseEntity<>("duplicated", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Modify Userinfo
	@PostMapping(value = "/modifyinfo")
	public ResponseEntity<String> modifyUserProfile(SignDTO dto) {
		if(dto.getPassword() != "") dto.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt())); // Password Encryption

		return service.modifyUserProfileService(dto) >= 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>("fail", HttpStatus.OK);
	}

	// Profile Picture Upload
	@PostMapping(value = "/profileupload")
	public String profileUpload(MultipartHttpServletRequest mpsr) {
		return service.profileUploadService(mpsr);
	}
	
}