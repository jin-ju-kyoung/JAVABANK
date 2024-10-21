package com.project.javabank.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
		    
		    
		    //예금 계좌 정보 조회 
		    List<ProductDTO> depositList = bankMapper.getDepositsByUserId(loginId);
			
		    if (depositList.isEmpty()) {
		        model.addAttribute("hasDeposit", false); // 계좌가 없으면 false 설정
		    } else {
		        model.addAttribute("hasDeposit", true); // 계좌가 있으면 true 설정
		        model.addAttribute("depositList", depositList); // 계좌 목록을 모델에 추가
		    }
		  System.out.println(depositList);
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
	         model.addAttribute("url", "bankMain.do");
	    } else {
	         model.addAttribute("msg", "계좌 생성에 실패했습니다.");
	         model.addAttribute("url", "bankMain.do");
	    }

	    return "message";  // 결과를 표시할 메시지 페이지로 이동
	}

		
		//입출금계좌리스트 페이지 이동
		@RequestMapping("/accountList.do")
		public String accountList(Model model, @AuthenticationPrincipal UserDetails user, @RequestParam("depositAccount") String depositAccount,
									@RequestParam("category") String category,
									@RequestParam("balance") String balance) {
			model.addAttribute("loginId", user.getUsername());
			
			List<DepositDTO> account = bankMapper.getAccountByAccountNumber(depositAccount);
			model.addAttribute("accountList",account);
			model.addAttribute("category",category);
			model.addAttribute("balance",balance);
			model.addAttribute("depositAccount",depositAccount);
			
			return"account_list";
		}
		
		//입출금계좌리스트 페이지 이동
		@RequestMapping("/depositList.do")
		public String depositList(Model model, @AuthenticationPrincipal UserDetails user, @RequestParam("productAccount") String productAccount,
									@RequestParam("category") String category,
									@RequestParam("balance") String balance) {
			model.addAttribute("loginId", user.getUsername());
			
			List<DepositDTO> deposit = bankMapper.getDepositByAccountNumber(productAccount);
			model.addAttribute("depositList",deposit);
			model.addAttribute("category",category);
			model.addAttribute("balance",balance);
			model.addAttribute("productAccount",productAccount);
			
			return"deposit_list";
		}
		
		@RequestMapping("/savingList.do")
		public String savingList(Model model, @AuthenticationPrincipal UserDetails user, @RequestParam("productAccount") String productAccount,
							@RequestParam("category") String category,
							@RequestParam("balance") String balance) {
				model.addAttribute("loginId", user.getUsername());
				
				List<DepositDTO> deposit = bankMapper.getDepositByAccountNumber(productAccount);
				model.addAttribute("depositList",deposit);
				model.addAttribute("category",category);
				model.addAttribute("balance",balance);
				model.addAttribute("productAccount",productAccount);
			return "saving_list";
		}
		
	
	//정기적금계좌개설
	@RequestMapping("/addInstallmentSaving.do")
	public String addInstallmentSaving(Model model, Authentication authentication) {
		return"add_installment_saving";
	}
	
	// 적금계좌개설
	@RequestMapping("/addInstallmentSavingOk.do")
	public String addInstallmentSavingOk(Model model, 
			                  @AuthenticationPrincipal UserDetails user,
			                  @RequestParam("productPw") String productPw, 
			                  @RequestParam("monthlyPayment") String monthlyPayment,
			                  @RequestParam("expiryDate") String expiryDate,
			                  @RequestParam("interestRate") BigDecimal interestRate,
			                  @RequestParam("autoTransferDate") BigDecimal autoTransferDate) {
				
				// String으로 받은 만기일자를 LocalDate로 변환
		        LocalDate expiryLocalDate = LocalDate.parse(expiryDate);

			    // 계좌 번호 랜덤 생성
			    String productAccount = bankMapper.generateAccountNumber();

			    // 사용자의 주계좌 조회
			    String depositAccount = bankMapper.mainAccount(user.getUsername());

			    // 계좌 정보 Map에 저장
			    Map<String, Object> params = new HashMap<>();
			    params.put("productPw", productPw);              
			    params.put("productAccount", productAccount);
			    params.put("monthlyPayment", monthlyPayment);        
			    params.put("userId", user.getUsername());        
			    params.put("expiryDate", expiryLocalDate);     
			    params.put("interestRate", interestRate);          
			    params.put("depositAccount", depositAccount);          
			    params.put("autoTransferDate", autoTransferDate);          
			    params.put("category", "적금");

			    // 데이터 저장 로직 호출
			    int res = bankMapper.createSavingWithTransaction(params);


			    if (res > 0) {
			         model.addAttribute("msg", "계좌가 성공적으로 생성되었습니다.");
			         model.addAttribute("url", "bankMain.do");
			    } else {
			         model.addAttribute("msg", "계좌 생성에 실패했습니다.");
			         model.addAttribute("url", "bankMain.do");
			    }

			    return "message";  // 결과를 표시할 메시지 페이지로 이동
			}
	
	
	
	//정기예금계좌개설
	@RequestMapping("/addDeposit.do")
	public String addDeposit(Model model, @AuthenticationPrincipal UserDetails user) {
		model.addAttribute("loginId", user.getUsername());
		return"add_deposit";
	}
	
	// 예금계좌개설
		@RequestMapping("/addDepositOk.do")
		public String addDepositOk(Model model, 
		                           @AuthenticationPrincipal UserDetails user,
		                           @RequestParam("productPw") String productPw, 
		                           @RequestParam("monthlyPayment") String monthlyPayment,
		                           @RequestParam("expiryDate") String expiryDate,
		                           @RequestParam("interestRate") BigDecimal interestRate) {
			
			// String으로 받은 만기일자를 LocalDate로 변환
	        LocalDate expiryLocalDate = LocalDate.parse(expiryDate);

		    // 계좌 번호 랜덤 생성
		    String productAccount = bankMapper.generateAccountNumber();

		    // 사용자의 계좌 수 조회
		    String depositAccount = bankMapper.mainAccount(user.getUsername());

		    // 계좌 정보 Map에 저장
		    Map<String, Object> params = new HashMap<>();
		    params.put("productPw", productPw);              // 비밀번호
		    params.put("productAccount", productAccount);
		    params.put("monthlyPayment", monthlyPayment);        // 이체 한도 (숫자 값)
		    params.put("userId", user.getUsername());        // 사용자 아이디
		    params.put("expiryDate", expiryDate);     // 계좌 번호
		    params.put("interestRate", interestRate);          // 주계좌 여부
		    params.put("depositAccount", depositAccount);          // 주계좌 여부
		    params.put("category", "예금");

		    // 데이터 저장 로직 호출
		    int res = bankMapper.createDepositWithTransaction(params);

		    // 계좌 생성 후 "add_account" 페이지로 이동
//		    model.addAttribute("accountNumber", accountNumber);
//		    model.addAttribute("mainAccount", mainAccount);

		    if (res > 0) {
		         model.addAttribute("msg", "계좌가 성공적으로 생성되었습니다.");
		         model.addAttribute("url", "bankMain.do");
		    } else {
		         model.addAttribute("msg", "계좌 생성에 실패했습니다.");
		         model.addAttribute("url", "bankMain.do");
		    }

		    return "message";  // 결과를 표시할 메시지 페이지로 이동
		}
		
		
	//송금 1단계 계좌 입력페이지
	@RequestMapping("/transfer.do")
	public String transfer(Model model, HttpSession session,
					@RequestParam("category") String category,
				    @RequestParam("depositAccount") String depositAccount,
				    @RequestParam("balance") String balance,
				    //@RequestParam("deltaAmount") String deltaAmount,
				    @AuthenticationPrincipal UserDetails user) {
		
		session.setAttribute("depositAccount", depositAccount);
        session.setAttribute("balance", balance);
        session.setAttribute("category", category);
        
        List<DepositDTO> transferList = bankMapper.getTransferList(user.getUsername());
     // 리스트의 내용을 출력하는 디버깅 코드
//        if (transferList != null && !transferList.isEmpty()) {
//            System.out.println("Transfer List: ");
//            for (DepositDTO account : transferList) {
//                System.out.println("계좌번호: " + account.getDepositAccount());
//                System.out.println("이체 이름: " + account.getTransferredName());
//                System.out.println("이체 계좌: " + account.getTransferredAccount());
//                System.out.println("카테고리: " + account.getCategory());
//                System.out.println("등록 날짜: " + account.getRegDate());
//                System.out.println("이자율: " + account.getInterestRate());
//                System.out.println("이체 한도: " + account.getTransactionLimit());
//                System.out.println("주계좌 여부: " + account.getMainAccount());
//                System.out.println("------------------------------");
//            }
//        } else {
//            System.out.println("이체 내역이 없습니다.");
//        }

        model.addAttribute("transferList",transferList);
        model.addAttribute("depositAccount",depositAccount);
        model.addAttribute("category",category);
		
		
		return "transfer";
	}
	
	//입출금 송금 2단계 금액입력
	@RequestMapping("/transferMoney.do")
	public String transferMoney(@RequestParam("bankName") String bankName,
	        @RequestParam("transferredAccount") String transferredAccount,
	        HttpSession session, Model model) {
		session.setAttribute("bankName", bankName);
        session.setAttribute("transferredAccount", transferredAccount);
        
        Integer accountCheck = bankMapper.isValidAccount(transferredAccount);

        if (accountCheck == null || accountCheck <= 0) {
            model.addAttribute("error", "자바뱅크에 존재하는 계좌만 송금 가능합니다. 해당 계좌는 존재하지 않습니다.");
            return "transfer";
        }
        String accountName = bankMapper.getAccountName(transferredAccount);
        session.setAttribute("accountName", accountName);
        //System.out.println(accountName);
        
		return "transfer_money";
	}
	
	//입출금 송금 3단계 송금정보 디비로 전송
		@RequestMapping("/transferMoneyOk.do")
		public String transferMoneyOk(@RequestParam("deltaAmount") String deltaAmount,
		        HttpSession session, Model model, @AuthenticationPrincipal UserDetails user) {
			
			String userId = user.getUsername();
			String balanceStr = (String) session.getAttribute("balance");
			// balance와 deltaAmount를 숫자로 변환
		    double balance = Double.parseDouble(balanceStr); // balance는 세션에 저장된 원래 잔액
		    double delta = Double.parseDouble(deltaAmount);  // deltaAmount는 이체할 금액
		    
		 // 오늘의 총 이체 금액을 DB에서 조회 (예시: bankService.getTodayTransferTotalAmount)
		    int todayTransferTotal = bankMapper.getTodayTransferTotalAmount(userId);
		    
		 // 이체 한도 확인 (10,000,000원)
		    int transferLimit = 10000000;
		    
		    // 1. 이체 한도 확인
		    if (todayTransferTotal + delta > transferLimit) {
		        model.addAttribute("msg", "이체 한도를 초과했습니다. 오늘 남은 이체 가능 금액: " + (transferLimit - todayTransferTotal) + "원" );
		        model.addAttribute("url", "/bankMain.do");
		        return "message";
		    }
		    
		 // 2. 잔액 확인
		    if (balance < delta) {
		        model.addAttribute("msg", "잔액이 부족합니다. 현재 잔액: " + balance + "원");
		        model.addAttribute("url", "/bankMain.do");
		        return "message";
		    }
		    
		    // balance에서 deltaAmount를 차감
		    double newBalance = balance - delta;
			
			Map<String, Object> params = new HashMap<>();
			params.put("bankName", session.getAttribute("bankName"));
			params.put("depositAccount", session.getAttribute("depositAccount"));
			params.put("balance", newBalance);
			params.put("transferredAccount", session.getAttribute("transferredAccount"));
			params.put("accountName", session.getAttribute("accountName"));
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
		
		//매달 이자 
//		@Scheduled(cron = "0 0 0 1 * ?") //매달 1일 자정에 실행
//	    public void addMonthlyInterest() {
//		        // 이자 추가 로직
//		        System.out.println("매달 계좌에 이자를 추가합니다.");
//
//		        bankMapper.addInterestToAllAccounts();
//		        
//		    }
		@Scheduled(cron = "0 0 0 1 * ?", zone = "Asia/Seoul")  // 원하는 타임존에 맞게 설정
		public void addMonthlyInterest() {
		    TimeZone timeZone = TimeZone.getDefault();
		    System.out.println("현재 타임존: " + timeZone.getID());
		    System.out.println("매달 1일 자정에 이자를 추가합니다.");
		    bankMapper.addInterestToAllAccounts();
		}
		 
	
	
}
