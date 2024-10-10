package com.project.javabank.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.javabank.dto.UserDTO;
import com.project.javabank.mapper.UserMapper;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

	@Autowired
	private UserMapper userMapper;
	
	@GetMapping(value = { "/"})
	public String index() {
		return "login";
	}
	
	@RequestMapping("/join.do")
	public String join(){
		return "join";
	}
	
	@RequestMapping("/login.do")
	public String loginSuccess(HttpServletRequest req, Model model) {
	    // CSRF 토큰을 수동으로 추가하는 부분은 생략 가능
	    return "login";  // 로그인 성공 후 메인 페이지로 리다이렉트
	}
	
	@RequestMapping("/loginFail.do")
	public String loginFail(HttpServletRequest req) {
		req.setAttribute("msg", "로그인 실패 하였습니다");
	    req.setAttribute("url", "/login/login.do");
	    return "message";
	}
	
//	@RequestMapping("/login/join_membership.do")
//	public String join_membership(HttpServletRequest req) {
//	    int random = (int)(Math.random() * 10000);
//	    PropertyReader reader = new PropertyReader();
//	    String redirect_url = reader.getProperty("redirect_url");
//	    String kakao_login = reader.getProperty("kakao_login");
//	    req.setAttribute("redirect_url", redirect_url);
//	    req.setAttribute("kakao_login", kakao_login);
//	    req.setAttribute("random", random);
//	    return "/login/join_membership";
//	}
	
	
	@RequestMapping("/joinSuccess.do")
	public String joinSuccess(Model model,@ModelAttribute UserDTO dto, @RequestParam("email") String emailFirst,
            @RequestParam("emailDomain") String emailDomain) {
		String fullEmail = emailFirst + emailDomain;
	    dto.setUserEmail(fullEmail);
	    
	    // 로그로 비밀번호가 제대로 전달되는지 확인
	    //System.out.println("전달된 비밀번호: " + dto.getUserPw());

	    // 비밀번호가 null이거나 비어있는 경우 처리
	    if (dto.getUserPw() == null || dto.getUserPw().isEmpty()) {
	        throw new IllegalArgumentException("비밀번호가 입력되지 않았습니다.");
	    }
	    
		String userId = String.valueOf(dto.getUserId());
		String userPw = String.valueOf(dto.getUserPw());
		String userName = String.valueOf(dto.getUserName());
		String userBirth = String.valueOf(dto.getUserBirth());
		String userTel = String.valueOf(dto.getUserTel());
		String regDate = String.valueOf(dto.getUserRegDate());
		
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("userPw", userPw);
		params.put("userName", userName);
		params.put("userBirth", userBirth);
		params.put("userTel", userTel);
		params.put("fullEmail", fullEmail);
		//params.put("regDate", regDate);
		
		int res = userMapper.joinSuccess(params);
		if (res>0) {
	         model.addAttribute("msg", "회원가입이 성공하셨습니다.");
	         model.addAttribute("url", "login.do");
	      }else {
	    	 model.addAttribute("msg", "회원가입이 실패하셨습니다.");
	    	 model.addAttribute("url", "login.do");
	      }
		
	    return "message";
	}
	
	
	
}
