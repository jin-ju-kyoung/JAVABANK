package com.project.javabank.mapper;

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
		System.out.println("계좌갯수 : "+accountCount);
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
        System.out.println("계좌 생성 결과: " + result);

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
            System.out.println("거래 내역 생성 결과: " + transactionResult);

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
		
		return sqlSession.selectOne("getTodayTransferTotalAmount",userId);
	}
	
	
	
	
}
