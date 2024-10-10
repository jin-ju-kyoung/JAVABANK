package com.project.javabank.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.project.javabank.dto.UserDTO;
import com.project.javabank.mapper.UserMapper;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	JavaMailSender mailSender;
	
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
	
	//회원가입 유효성검사
	@ResponseBody
	@RequestMapping("/checkId.ajax")
	public String checkId(String userId) {
		try {
			int checkIdres = userMapper.checkId(userId);
			if(checkIdres == 0) {
				return "OK";
			}else {
				return "ERROR";
			}
		} catch(Exception e) {
			System.out.println("아이디 중복확인 에러");
			e.printStackTrace();
			return "ERROR";
		}		
		
	}
	
	@ResponseBody
	@RequestMapping("/sendEmail.ajax")
	public String sendEmail(HttpServletResponse resp, String mail1, String mail2) {
		try {
			String email = mail1 + mail2;
			
			// 인증코드
			Random random = new Random();
			String certiCode = String.valueOf(random.nextInt(900000) + 100000);
			
			// 쿠키
			Cookie cookie = new Cookie("certiCode", certiCode);
			cookie.setMaxAge(3*60);
			cookie.setPath("/");
			resp.addCookie(cookie);
			
			// 이메일 전송 로직
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			helper.setFrom("admin@javabank.com");
			helper.setTo(email);
	        helper.setSubject("JavaBank 이메일 인증번호입니다.");
	        helper.setText("안녕하세요!! JavaBank 입니다.\n\n 이메일 인증번호 : " + certiCode
	                + " \n\n 회원가입을 진행 하시려면 인증번호를 해당 칸에 입력해주세요.\n 이용해주셔서 감사합니다." + "\n\n --JavaBank--");
	        mailSender.send(msg);	              
			
			return "OK";
		}catch(Exception e){
			e.printStackTrace();
			return "ERROR";
		}
	}
	
	@ResponseBody
	@RequestMapping("/confirmCode.ajax")
	public String confirmCode(HttpServletRequest req, String inputCode) {
		Cookie [] cookies = req.getCookies();
		 if(cookies != null) {
			 for(Cookie cookie : cookies) {
				 if(cookie.getName().contentEquals("certiCode")) {
					 if(cookie.getValue().equals(inputCode)) {
						 return "OK";
					 }
				 }
			 }
		 }
		 return "ERROR";
		
	}
	
	
	
	
}
