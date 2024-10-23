package com.project.javabank.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.javabank.dto.DepositDTO;
import com.project.javabank.dto.ProductDTO;

@Service
public class BankMapper {

	@Autowired
	private SqlSession sqlSession;
	
	public String generateAccountNumber() {
	    StringBuilder accountNumber = new StringBuilder();

	    // 16자리 숫자를 랜덤으로 생성 (4자리씩)
	    Random random = new Random();
	    for (int i = 0; i < 4; i++) {
	        int segment = random.nextInt(9000) + 1000;  // 1000~9999 사이의 4자리 숫자 생성
	        accountNumber.append(segment);
	        if (i < 3) {
	            accountNumber.append("-");  // 마지막 그룹 뒤에는 '-'를 붙이지 않음
	        }
	    }
	    //System.out.println("계좌번호 : "+ random);

	    return accountNumber.toString();
	}
	
	public boolean checkIfUserHasAccount(String userId) {
		int accountCount = sqlSession.selectOne("getAccountCountByUserId",userId);
		//
		//System.out.println("계좌갯수 : "+accountCount);
		return accountCount>0;
	}

	//계좌생성 & 계좌생성하면 바로 계좌에 0원 입금 트랜젝션
	@Transactional  // 트랜잭션 적용
    public int createAccountWithTransaction(Map<String, Object> params) {
        // 계좌 생성
        String accountNumber = generateAccountNumber();
        params.put("accountNumber", accountNumber);  // 생성한 계좌번호를 파라미터에 추가

        // 1. 계좌 정보 저장
        int result = sqlSession.insert("saveAccount", params);
        //System.out.println("계좌 생성 결과: " + result);

        // 2. 계좌 생성 후 초기 잔액을 0으로 설정한 거래 내역 추가
        if (result > 0) {
            Map<String, Object> transactionParams = Map.of(
                "accountNumber", accountNumber,
                "deltaAmount", 0,
                "balance", 0,
                "type", "개설",
                "memo", "계좌 개설"
            );
            int transactionResult = sqlSession.insert("saveInitialTransaction", transactionParams);
            //System.out.println("거래 내역 생성 결과: " + transactionResult);

            if (transactionResult <= 0) {
                throw new RuntimeException("거래 내역 추가에 실패했습니다.");  // 트랜잭션 롤백을 위해 예외 발생
            }
        }

        return result;
    }
	
	public List<DepositDTO> getAccountsByUserId(String loginId){
		return sqlSession.selectList("getAccountsByUserId",loginId);
	}
	
	public List<ProductDTO> getDepositsByUserId(String loginId){
		return sqlSession.selectList("getDepositsByUserId",loginId);
	}
	
	public List<ProductDTO> getSavingsByUserId(String loginId){
		return sqlSession.selectList("getSavingsByUserId",loginId);
	}
	
	public int transferMoneyOk(Map<String, Object> params) {
		//System.out.println("송금데이터 : " + params);
		return sqlSession.insert("transferMoneyOk",params);
	}
	
	public int getTodayTransferTotalAmount(String userId) {
	    Integer result = sqlSession.selectOne("getTodayTransferTotalAmount", userId);
	    
	    // null일 경우 기본값 0 반환
	    return (result != null) ? result : 0;
	}
	public String mainAccount(String userId) {
		return sqlSession.selectOne("mainAccount",userId);
	}
	
	@Transactional
	public int createDepositWithTransaction(Map<String, Object> params) {
		// 1. 계좌 정보 저장
        int result = sqlSession.insert("saveDeposit", params);
       // System.out.println("계좌 생성 결과: " + result);

        // 2. 계좌 생성 후 초기 잔액을 0으로 설정한 거래 내역 추가
        if (result > 0) {
            Map<String, Object> transactionParams = Map.of(
                "productAccount",params.get("productAccount"),
                "deltaAmount", 0,
                "balance", params.get("monthlyPayment"),
                "type", "개설",
                "memo", "예금 계좌 개설"
            );
            int transactionResult = sqlSession.insert("saveInitialTransactionDeposit", transactionParams);
           // System.out.println("거래 내역 생성 결과: " + transactionResult);

            if (transactionResult <= 0) {
                throw new RuntimeException("거래 내역 추가에 실패했습니다.");  // 트랜잭션 롤백을 위해 예외 발생
            }
        }

        return result;
    }
	
	//적금계좌생성 트랜젝션
	@Transactional
	public int createSavingWithTransaction(Map<String, Object> params) {
		// 1. 계좌 정보 저장
        int result = sqlSession.insert("saveSaving", params);
        System.out.println("계좌 생성 결과: " + result);

        // 2. 계좌 생성 후 초기 잔액을 0으로 설정한 거래 내역 추가
        if (result > 0) {
            Map<String, Object> transactionParams = Map.of(
                "productAccount",params.get("productAccount"),
                "deltaAmount", 0,
                "balance", 0,
                "type", "개설",
                "memo", "적금 계좌 개설"
            );
            int transactionResult = sqlSession.insert("saveInitialTransactionSaving", transactionParams);
            System.out.println("거래 내역 생성 결과: " + transactionResult);

            if (transactionResult <= 0) {
                throw new RuntimeException("거래 내역 추가에 실패했습니다.");  // 트랜잭션 롤백을 위해 예외 발생
            }
        }

        return result;
    }

	public List<DepositDTO> getAccountByAccountNumber(String depositAccount){
		return sqlSession.selectList("getAccountByAccountNumber", depositAccount);
	}
	
	public List<DepositDTO> getDepositByAccountNumber(String productAccount){
		return sqlSession.selectList("getDepositByAccountNumber", productAccount);
	}

	
	public int isValidAccount(String transferredAccount){
		return sqlSession.selectOne("isValidAccount", transferredAccount);
	}
	
	public String getAccountName(String transferredAccount){
		return sqlSession.selectOne("getAccountName", transferredAccount);
	}
	
	public List<DepositDTO> getTransferList(String userId){
		return sqlSession.selectList("getTransferList",userId);
	}
	
	//이자
	public void addInterestToAllAccounts() {
		 
		//예,적금 계좌 이자
        List<ProductDTO> productAccounts = sqlSession.selectList("getAllSavingsAccounts");

        for (ProductDTO account : productAccounts) {
            // 계좌의 잔액과 이자율 가져오기
        	 BigDecimal balance = BigDecimal.valueOf(account.getBalance()); // double -> BigDecimal
        	 BigDecimal interestRate = BigDecimal.valueOf(account.getInterestRate()).divide(new BigDecimal("100"));

             // 월 이율 계산 (연이율을 12로 나눔)
            BigDecimal monthlyInterestRate = interestRate.divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP);
            
            // 월 이자 계산 (잔액 * 월 이율)
            BigDecimal interest = balance.multiply(monthlyInterestRate).setScale(2, RoundingMode.HALF_UP);
            
            // 이자를 잔액에 더함
            BigDecimal updatedBalance = balance.add(interest);
            

         // 이자 정보를 거래 내역 테이블에 기록하기 위한 매개변수 설정
            Map<String, Object> params = new HashMap<>();
            params.put("productAccount", account.getProductAccount());  // 계좌번호
            params.put("updateDate", new java.util.Date());  // 현재 날짜를 변동일자로 사용
            params.put("type", "이자입금");  // 거래 유형: 이자입금
            params.put("memo", "월 이자 지급");  // 적요
            params.put("deltaAmount", interest);  // 변동 금액 (이자)
            params.put("balance", updatedBalance);  // 이자 적용 후 잔액

         // 개별적으로 insert 쿼리 실행
            sqlSession.insert("insertInterestTransaction", params);
            System.out.println("예,적금이자가 추가되었습니다");
        }
        
        //입출금계좌 이자
        List<DepositDTO> depositAccounts = sqlSession.selectList("getAllAccounts");

        for (DepositDTO dAccount : depositAccounts) {
            // 계좌의 잔액과 이자율 가져오기
        	 BigDecimal balance = BigDecimal.valueOf(dAccount.getBalance()); // double -> BigDecimal
        	 BigDecimal interestRate = BigDecimal.valueOf(dAccount.getInterestRate()).divide(new BigDecimal("100"));

             // 월 이율 계산 (연이율을 12로 나눔)
            BigDecimal monthlyInterestRate = interestRate.divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP);
            
            // 월 이자 계산 (잔액 * 월 이율)
            BigDecimal interest = balance.multiply(monthlyInterestRate).setScale(2, RoundingMode.HALF_UP);
            
            // 이자를 잔액에 더함
            BigDecimal updatedBalance = balance.add(interest);
            

         // 이자 정보를 거래 내역 테이블에 기록하기 위한 매개변수 설정
            Map<String, Object> params = new HashMap<>();
            params.put("depositAccounts", dAccount.getDepositAccount());  // 계좌번호
            params.put("updateDate", new java.util.Date());  // 현재 날짜를 변동일자로 사용
            params.put("type", "이자입금");  // 거래 유형: 이자입금
            params.put("memo", "월 이자 지급");  // 적요
            params.put("deltaAmount", interest);  // 변동 금액 (이자)
            params.put("balance", updatedBalance);  // 이자 적용 후 잔액
            params.put("transferredName", "javabank");  // 이자 적용 후 잔액

         // 개별적으로 insert 쿼리 실행
            sqlSession.insert("insertInterest", params);
            System.out.println("입출금이자가 추가되었습니다");
        }
        
    }
	
	//적금 자동이체
	@Transactional
	public void checkMonthlySaving() {
		//1. 오늘 날짜에 맞는 등록된 적금계좌 정보 들고오기
		List<ProductDTO> transfers = sqlSession.selectList("getTodayTransfers");
		
		if (transfers == null || transfers.isEmpty()) {
	        System.out.println("오늘 자동이체할 계좌가 없습니다.");
	        return;
	    }
		
		//2. 각 계좌에 대해 자동이체
        for (ProductDTO product : transfers) {
        	 DepositDTO mainAccount = sqlSession.selectOne("getMainAccount", product.getUserId());

        	    if (mainAccount != null) {
        	        // 제품의 월납입금액과 계좌 잔액 및 계좌 번호 사용
        	    	String transferredAccount = product.getProductAccount();
        	        double amount = product.getMonthlyPayment();
        	        double balance = mainAccount.getBalance();
        	        double Sbalance = product.getBalance();
        	        String mainDepositAccount = mainAccount.getDepositAccount();
        	        
        	        if(balance < amount) {
                		System.out.println("잔액이 부족하여 이체가 불가능합니다: 사용자 " + product.getUserId());
                		continue;
                	}
        	        
        	        BigDecimal balanceBD = BigDecimal.valueOf(balance);
        	        BigDecimal amountBD = BigDecimal.valueOf(amount);
        	        BigDecimal SbalanceBD = BigDecimal.valueOf(Sbalance);

        	        // BigDecimal에서 subtract() 메서드를 사용해 계산
        	        BigDecimal newBalance = balanceBD.subtract(amountBD);
        	        BigDecimal newSBalance = SbalanceBD.add(amountBD);

        	        
        	        Map<String, Object> params = new HashMap<>();
               	 params.put("depositAccount", mainDepositAccount);
               	 params.put("transferredAccount", transferredAccount);
               	 params.put("transferredName", "적금자동이체");
               	 params.put("deltaAmount", new BigDecimal(amount));
               	 //params.put("updateDate", new java.util.Date());
               	 params.put("type", "자동이체 출금");
               	 //params.put("amount", amount);
               	 params.put("balance", newBalance);
               	System.out.println("Params: " + params);
               	
               	//주거래 입출금 계좌에서 출금
               	int withdrawResult = sqlSession.insert("withdrawFromMainAccount",params);
                   if (withdrawResult == 0) {
                       throw new RuntimeException("출금 처리에 실패했습니다: 사용자 " + product.getUserId());
                   }
                   
               	Map<String, Object> Sparams = new HashMap<>();
               	Sparams.put("productAccount", transferredAccount);
               	//Sparams.put("updateDate", new java.util.Date());
               	Sparams.put("type", "자동이체");
               	Sparams.put("memo", "매달 정기적금 자동이체");
               	Sparams.put("deltaAmount", new BigDecimal(amount));
               	Sparams.put("balance", newSBalance);
               	System.out.println("Sparams: " + Sparams);
                   
                   
                   int depositResult = sqlSession.insert("depositToProductAccount", Sparams);
                   if (depositResult == 0) {
                       throw new RuntimeException("입금 처리에 실패했습니다: 사용자 " + product.getUserId());
                   }
               

        	        // 추가 로직을 여기에 작성
        	    } else {
        	        // mainAccount가 없을 경우 처리 로직
        	        System.out.println("Main account not found for user: " + product.getUserId());
        	    }
        }
        	
        	
        	 
        System.out.println("오늘 날짜에 해당하는 적금 자동이체를 실행하였씁니다.");
	}

	
	//내계좌
	public List<DepositDTO> getMyAccountsByUserId(String loginId){
		return sqlSession.selectList("MyAccountsByUserId",loginId);
	}
	
	public List<ProductDTO> getMyDepositsByUserId(String loginId){
		return sqlSession.selectList("getMyDepositsByUserId",loginId);
	}
	
	public List<ProductDTO> getMySavingsByUserId(String loginId){
		return sqlSession.selectList("getMySavingsByUserId",loginId);
	}
	
	public double getBalanceByAccount(String recipientAccount){
		return sqlSession.selectOne("getBalanceByAccount",recipientAccount);
	}
	
	public int insertRecipientBalance(Map<String, Object> depositParams){
		System.out.println("depositParams:"+depositParams);
		return sqlSession.insert("insertRecipientBalance",depositParams);
	}
	
}
