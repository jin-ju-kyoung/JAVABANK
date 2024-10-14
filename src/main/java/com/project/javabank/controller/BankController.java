package com.project.javabank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.javabank.dto.DepositDTO;
import com.project.javabank.dto.ProductDTO;
import com.project.javabank.mapper.BankMapper;

import jakarta.servlet.http.HttpSession;

@Controller
public class BankController {

	@Autowired
	private BankMapper bankMapper;
	
	@GetMapping("/bankMain.do")
	public String bankMain(@AuthenticationPrincipal UserDetails user, Model model) {
			// 로그인 정보 꺼내기
			String loginId = user.getUsername();
			model.addAttribute("loginId", loginId);
			model.addAttribute("loginRoles", user.getAuthorities());
			
			// 입출금 계좌 정보 조회
		    List<DepositDTO> accountList = bankMapper.getAccountsByUserId(loginId);
			
		    if (accountList.isEmpty()) {
		        model.addAttribute("hasAccount", false); // 계좌가 없으면 false 설정
		    } else {
		        model.addAttribute("hasAccount", true); // 계좌가 있으면 true 설정
		        model.addAttribute("accountList", accountList); // 계좌 목록을 모델에 추가
		    }
		    //System.out.println(accountList);
		    
		    //예금 계좌 정보 조회 
		    List<ProductDTO> depositList = bankMapper.getDepositsByUserId(loginId);
			
		    if (depositList.isEmpty()) {
		        model.addAttribute("hasDeposit", false); // 계좌가 없으면 false 설정
		    } else {
		        model.addAttribute("hasDeposit", true); // 계좌가 있으면 true 설정
		        model.addAttribute("depositList", depositList); // 계좌 목록을 모델에 추가
		    }
		    
		    //적금 계좌 정보 조회
		    List<ProductDTO> InstallmentSavingsList = bankMapper.getSavingsByUserId(loginId);
			
		    if (InstallmentSavingsList.isEmpty()) {
		        model.addAttribute("hasSavings", false); // 계좌가 없으면 false 설정
		    } else {
		        model.addAttribute("hasSavings", true); // 계좌가 있으면 true 설정
		        model.addAttribute("InstallmentSavingsList", InstallmentSavingsList); // 계좌 목록을 모델에 추가
		    }
		    
			return "index";
	}
	
	//입출금계좌개설페이지이동
	@RequestMapping("/addAccount.do")
	public String addAccount(Model model, @AuthenticationPrincipal UserDetails user) {
		model.addAttribute("loginId", user.getUsername());
		
		return"add_account";
	}
	
	// 입출금계좌개설
	@RequestMapping("/addAccountOk.do")
	public String addAccountOk(Model model, 
	                           @AuthenticationPrincipal UserDetails user,
	                           @RequestParam("depositPw") String depositPw, 
	                           @RequestParam("transferLimit") String transferLimit) {

		// 이체 한도 값을 숫자로 변환
	    int accountLimit;
	    try {
	        accountLimit = Integer.parseInt(transferLimit.replaceAll(",", ""));  // 쉼표 제거 후 숫자 변환
	    } catch (NumberFormatException e) {
	        model.addAttribute("error", "유효하지 않은 이체 한도 값입니다.");
	        return "error";  // 잘못된 값일 경우 오류 페이지로 이동
	    }

	    // 계좌 번호 랜덤 생성
	    String accountNumber = bankMapper.generateAccountNumber();

	    // 사용자의 계좌 수 조회
	    boolean hasAccount = bankMapper.checkIfUserHasAccount(user.getUsername());

	    // 주계좌 여부 결정
	    String mainAccount = hasAccount ? "N" : "Y";  // 첫 계좌라면 주계좌로 설정

	    // 계좌 정보 Map에 저장
	    Map<String, Object> params = new HashMap<>();
	    params.put("depositPw", depositPw);              // 비밀번호
	    params.put("accountLimit", accountLimit);        // 이체 한도 (숫자 값)
	    params.put("userId", user.getUsername());        // 사용자 아이디
	    params.put("accountNumber", accountNumber);     // 계좌 번호
	    params.put("mainAccount", mainAccount);          // 주계좌 여부

	    // 데이터 저장 로직 호출
	    int res = bankMapper.createAccountWithTransaction(params);

	    // 계좌 생성 후 "add_account" 페이지로 이동
	    model.addAttribute("accountNumber", accountNumber);
	    model.addAttribute("mainAccount", mainAccount);

	    if (res > 0) {
	         model.addAttribute("msg", "계좌가 성공적으로 생성되었습니다.");
	         model.addAttribute("url", "accountList.do");
	    } else {
	         model.addAttribute("msg", "계좌 생성에 실패했습니다.");
	         model.addAttribute("url", "accountList.do");
	    }

	    return "message";  // 결과를 표시할 메시지 페이지로 이동
	}

		
		//입출금계좌리스트 페이지 이동
		@RequestMapping("/accountList.do")
		public String accountList(Model model, @AuthenticationPrincipal UserDetails user) {
			model.addAttribute("loginId", user.getUsername());
			
			
			
			return"account_list";
		}
		
	
	//정기적금계좌개설
	@RequestMapping("/addInstallmentSaving.do")
	public String addInstallmentSaving(Model model, Authentication authentication) {
		return"add_installment_saving";
	}
	
	//정기예금계좌개설
	@RequestMapping("/addDeposit.do")
	public String addDeposit(Model model, Authentication authentication) {
		return"add_deposit";
	}
	
	//입출금 송금 1단계 계좌 입력페이지
	@RequestMapping("/transfer.do")
	public String transfer(Model model, HttpSession session,
					@RequestParam("category") String category,
				    @RequestParam("depositAccount") String depositAccount,
				    @RequestParam("balance") String balance,
				    @RequestParam("deltaAmount") String deltaAmount) {
		
		session.setAttribute("depositAccount", depositAccount);
        session.setAttribute("balance", balance);
		
//		Map<String, Object> params = new HashMap<>();
//		params.put("category", category);
//		params.put("depositAccount", depositAccount);
//		params.put("balance", balance);
//		params.put("type", type);
//		params.put("deltaAmount", deltaAmount);
//		
		
		
		return "transfer";
	}
	
	//입출금 송금 2단계 금액입력
	@RequestMapping("/transferMoney.do")
	public String transferMoney(@RequestParam("bankName") String bankName,
	        @RequestParam("transferredAccount") String transferredAccount,
	        HttpSession session) {
		session.setAttribute("bankName", bankName);
        session.setAttribute("transferredAccount", transferredAccount);
        
		return "transfer_money";
	}
	
	//입출금 송금 3단계 송금정보 디비로 전송
		@RequestMapping("/transferMoneyOk.do")
		public String transferMoneyOk(@RequestParam("deltaAmount") String deltaAmount,
		        HttpSession session, Model model) {
			
			String balanceStr = (String) session.getAttribute("balance");
			// balance와 deltaAmount를 숫자로 변환
		    double balance = Double.parseDouble(balanceStr); // balance는 세션에 저장된 원래 잔액
		    double delta = Double.parseDouble(deltaAmount);  // deltaAmount는 이체할 금액

		    // balance에서 deltaAmount를 차감
		    double newBalance = balance - delta;
			
			Map<String, Object> params = new HashMap<>();
			params.put("bankName", session.getAttribute("bankName"));
			params.put("depositAccount", session.getAttribute("depositAccount"));
			params.put("balance", newBalance);
			params.put("transferredAccount", session.getAttribute("transferredAccount"));
			params.put("deltaAmount", deltaAmount);
			
			
			// 데이터 저장 로직 호출
		    int res = bankMapper.transferMoneyOk(params);
	        
			if (res > 0) {
		         model.addAttribute("msg", "성공적으로 송금되었습니다.");
		         model.addAttribute("url", "bankMain.do");
		    } else {
		         model.addAttribute("msg", "송금 실패했습니다.");
		         model.addAttribute("url", "bankMain.do");
		    }

		    return "message";
		}
		
		
	
	
}
